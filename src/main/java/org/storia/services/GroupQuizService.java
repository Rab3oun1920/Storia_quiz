package org.storia.services;

import org.storia.entities.*;
import org.storia.dto.GroupSessionRequest;
import org.storia.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupQuizService {
    @Autowired
    private GroupQuizSessionRepository groupQuizSessionRepository;

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Autowired
    private GroupQuizAnswerRepository groupQuizAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    // PARTIE 1: CRÉATION
    public Map<String, Object> createGroupSession(GroupSessionRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Theme theme = themeRepository.findById(request.getThemeId()).orElseThrow();

        // Valider que le nom de session est unique pour cet utilisateur
        if (groupQuizSessionRepository.existsBySessionNameAndCreatedById(request.getSessionName(), userId)) {
            throw new RuntimeException("A session with this name already exists for this user");
        }

        // Valider 2-4 groupes
        int numberOfGroups = request.getGroups().size();
        if (numberOfGroups < 2 || numberOfGroups > 4) {
            throw new RuntimeException("Number of groups must be between 2 and 4");
        }

        // Créer session
        GroupQuizSession session = new GroupQuizSession();
        session.setSessionName(request.getSessionName());
        session.setSessionCode(generateSessionCode(theme.getName()));
        session.setTheme(theme);
        session.setNumberOfGroups(numberOfGroups);
        session.setTotalQuestions(numberOfGroups == 2 ? 20 : numberOfGroups == 3 ? 30 : 40);
        session.setTimePerQuestion(5);
        session.setStatus(GroupQuizSession.SessionStatus.WAITING);
        session.setCreatedBy(user);
        session = groupQuizSessionRepository.save(session);

        // Créer participants
        List<GroupParticipant> participants = new ArrayList<>();
        for (GroupSessionRequest.GroupRequest groupReq : request.getGroups()) {
            GroupParticipant participant = new GroupParticipant();
            participant.setSession(session);
            participant.setGroupName(groupReq.getGroupName());
            participant.setGroupOrder(groupReq.getOrder());
            participant.setScore(0);
            participant.setCorrectAnswers(0);
            participant.setTotalQuestions(10); // 10 questions par groupe
            participant.setPercentage(0.0);
            participants.add(participant);
        }
        groupParticipantRepository.saveAll(participants);

        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", session.getId());
        result.put("sessionCode", session.getSessionCode());
        result.put("numberOfGroups", numberOfGroups);
        result.put("totalQuestions", session.getTotalQuestions());

        return result;
    }

    private String generateSessionCode(String themeName) {
        String prefix = themeName.substring(0, Math.min(4, themeName.length())).toUpperCase();
        String timestamp = String.valueOf(System.currentTimeMillis() % 100000); // 5 derniers chiffres
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return prefix + "-" + timestamp + "-" + random;
    }

    public Map<String, Object> getSessionDetails(Long sessionId) {
        GroupQuizSession session = groupQuizSessionRepository.findById(sessionId).orElseThrow();
        List<GroupParticipant> participants = groupParticipantRepository
            .findBySessionIdOrderByGroupOrderAsc(sessionId);

        // Format session data
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("id", session.getId());
        sessionData.put("sessionName", session.getSessionName());
        sessionData.put("sessionCode", session.getSessionCode());
        sessionData.put("themeName", session.getTheme().getName());
        sessionData.put("numberOfGroups", session.getNumberOfGroups());
        sessionData.put("totalQuestions", session.getTotalQuestions());
        sessionData.put("timePerQuestion", session.getTimePerQuestion());
        sessionData.put("status", session.getStatus().name());
        sessionData.put("createdAt", session.getCreatedAt().toString());

        // Format participants data
        List<Map<String, Object>> participantsData = new ArrayList<>();
        for (GroupParticipant participant : participants) {
            Map<String, Object> pData = new HashMap<>();
            pData.put("id", participant.getId());
            pData.put("groupName", participant.getGroupName());
            pData.put("groupOrder", participant.getGroupOrder());
            pData.put("score", participant.getScore());
            pData.put("correctAnswers", participant.getCorrectAnswers());
            pData.put("totalQuestions", participant.getTotalQuestions());
            pData.put("percentage", participant.getPercentage());
            participantsData.add(pData);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("session", sessionData);
        result.put("participants", participantsData);

        return result;
    }

    // PARTIE 2: GAMEPLAY
    @Transactional
    public Map<String, Object> startGroupQuiz(Long sessionId) {
        GroupQuizSession session = groupQuizSessionRepository.findById(sessionId).orElseThrow();

        // Changer status
        session.setStatus(GroupQuizSession.SessionStatus.IN_PROGRESS);
        groupQuizSessionRepository.save(session);

        // Charger questions aléatoires
        List<Question> allQuestions = questionRepository
            .findRandomGroupQuestionsByThemeId(session.getTheme().getId(), session.getTotalQuestions());

        // Retourner questions pour le premier groupe (Q1-10)
        List<GroupParticipant> participants = groupParticipantRepository
            .findBySessionIdOrderByGroupOrderAsc(sessionId);

        if (participants.isEmpty()) {
            throw new RuntimeException("No participants found");
        }

        GroupParticipant firstGroup = participants.get(0);
        List<Question> firstGroupQuestions = allQuestions.subList(0, Math.min(10, allQuestions.size()));

        // Format current group without circular reference
        Map<String, Object> currentGroupData = new HashMap<>();
        currentGroupData.put("id", firstGroup.getId());
        currentGroupData.put("name", firstGroup.getGroupName());
        currentGroupData.put("order", firstGroup.getGroupOrder());
        currentGroupData.put("score", firstGroup.getScore());

        // Format all groups
        List<Map<String, Object>> groupsData = new ArrayList<>();
        for (GroupParticipant participant : participants) {
            Map<String, Object> groupData = new HashMap<>();
            groupData.put("id", participant.getId());
            groupData.put("name", participant.getGroupName());
            groupData.put("order", participant.getGroupOrder());
            groupData.put("score", participant.getScore());
            groupsData.add(groupData);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", sessionId);
        result.put("name", session.getSessionName());
        result.put("sessionId", sessionId);
        result.put("currentGroup", currentGroupData);
        result.put("groups", groupsData);
        result.put("questions", formatQuestions(firstGroupQuestions));
        result.put("totalQuestions", 10);
        result.put("timePerQuestion", session.getTimePerQuestion());

        return result;
    }

    public Map<String, Object> getQuestionsForGroup(Long sessionId, int groupOrder) {
        GroupQuizSession session = groupQuizSessionRepository.findById(sessionId).orElseThrow();

        // Charger toutes les questions
        List<Question> allQuestions = questionRepository
            .findRandomGroupQuestionsByThemeId(session.getTheme().getId(), session.getTotalQuestions());

        // Calculer range selon groupOrder
        int startIdx = (groupOrder - 1) * 10;
        int endIdx = Math.min(startIdx + 10, allQuestions.size());

        List<Question> groupQuestions = allQuestions.subList(startIdx, endIdx);

        Map<String, Object> result = new HashMap<>();
        result.put("questions", formatQuestions(groupQuestions));

        return result;
    }

    @Transactional
    public Map<String, Object> checkGroupAnswer(Long sessionId, Long groupId, Long answerId, int timeSpent) {
        Answer answer = answerRepository.findById(answerId).orElseThrow();
        GroupParticipant group = groupParticipantRepository.findById(groupId).orElseThrow();

        // Sauvegarder réponse
        GroupQuizAnswer quizAnswer = new GroupQuizAnswer();
        quizAnswer.setSession(groupQuizSessionRepository.findById(sessionId).orElseThrow());
        quizAnswer.setGroupParticipant(group);
        quizAnswer.setQuestion(answer.getQuestion());
        quizAnswer.setAnswer(answer);
        quizAnswer.setIsCorrect(answer.getIsCorrect());
        quizAnswer.setTimeSpent(timeSpent);
        groupQuizAnswerRepository.save(quizAnswer);

        Map<String, Object> result = new HashMap<>();
        result.put("correct", answer.getIsCorrect());
        result.put("points", answer.getIsCorrect() ? answer.getQuestion().getPoints() : 0);

        return result;
    }

    // PARTIE 3: RÉSULTATS
    @Transactional
    public Map<String, Object> completeGroupTurn(Long sessionId, Long groupId) {
        GroupParticipant group = groupParticipantRepository.findById(groupId).orElseThrow();

        // Calculer score du groupe
        List<GroupQuizAnswer> answers = groupQuizAnswerRepository.findByGroupParticipantId(groupId);

        int totalScore = 0;
        int correctAnswers = 0;

        for (GroupQuizAnswer answer : answers) {
            if (answer.getIsCorrect()) {
                correctAnswers++;
                totalScore += answer.getQuestion().getPoints();
            }
        }

        group.setScore(totalScore);
        group.setCorrectAnswers(correctAnswers);

        // Calculer le pourcentage avec protection contre division par zéro
        if (group.getTotalQuestions() != null && group.getTotalQuestions() > 0) {
            group.setPercentage((correctAnswers * 100.0) / group.getTotalQuestions());
        } else {
            group.setPercentage(0.0);
        }

        groupParticipantRepository.save(group);

        Map<String, Object> result = new HashMap<>();
        result.put("groupId", groupId);
        result.put("score", totalScore);
        result.put("correctAnswers", correctAnswers);
        result.put("percentage", group.getPercentage());

        return result;
    }

    public Map<String, Object> moveToNextGroup(Long sessionId, int currentGroupOrder) {
        List<GroupParticipant> participants = groupParticipantRepository
            .findBySessionIdOrderByGroupOrderAsc(sessionId);

        int nextOrder = currentGroupOrder + 1;
        GroupParticipant nextGroup = participants.stream()
            .filter(p -> p.getGroupOrder() == nextOrder)
            .findFirst()
            .orElse(null);

        if (nextGroup == null) {
            throw new RuntimeException("No more groups");
        }

        Map<String, Object> result = getQuestionsForGroup(sessionId, nextOrder);

        // Format current group without circular reference
        Map<String, Object> currentGroupData = new HashMap<>();
        currentGroupData.put("id", nextGroup.getId());
        currentGroupData.put("name", nextGroup.getGroupName());
        currentGroupData.put("order", nextGroup.getGroupOrder());
        currentGroupData.put("score", nextGroup.getScore());

        result.put("currentGroup", currentGroupData);

        return result;
    }

    @Transactional
    public Map<String, Object> completeSession(Long sessionId) {
        GroupQuizSession session = groupQuizSessionRepository.findById(sessionId).orElseThrow();
        List<GroupParticipant> participants = groupParticipantRepository.findBySessionId(sessionId);

        // Trier par score décroissant
        participants.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Assigner rangs
        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setRank(i + 1);
        }
        groupParticipantRepository.saveAll(participants);

        // Définir le vainqueur
        if (!participants.isEmpty()) {
            session.setWinnerGroup(participants.get(0));
        }
        session.setStatus(GroupQuizSession.SessionStatus.COMPLETED);
        session.setCompletedAt(LocalDateTime.now());
        groupQuizSessionRepository.save(session);

        // Format participants without circular references
        List<Map<String, Object>> rankingsData = new ArrayList<>();
        for (GroupParticipant participant : participants) {
            Map<String, Object> pData = new HashMap<>();
            pData.put("id", participant.getId());
            pData.put("name", participant.getGroupName());
            pData.put("score", participant.getScore());
            pData.put("correctAnswers", participant.getCorrectAnswers());
            pData.put("totalQuestions", participant.getTotalQuestions());
            pData.put("percentage", participant.getPercentage());
            pData.put("rank", participant.getRank());
            rankingsData.add(pData);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("sessionName", session.getSessionName());
        result.put("winner", rankingsData.isEmpty() ? null : rankingsData.get(0));
        result.put("rankings", rankingsData);

        return result;
    }

    public List<Map<String, Object>> getSessionHistory(Long userId) {
        List<GroupQuizSession> sessions = groupQuizSessionRepository.findByCreatedByIdOrderByCreatedAtDesc(userId);

        List<Map<String, Object>> result = new ArrayList<>();
        for (GroupQuizSession session : sessions) {
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("id", session.getId());
            sessionData.put("sessionName", session.getSessionName());
            sessionData.put("sessionCode", session.getSessionCode());
            sessionData.put("themeName", session.getTheme().getName());
            sessionData.put("numberOfGroups", session.getNumberOfGroups());
            sessionData.put("totalQuestions", session.getTotalQuestions());
            sessionData.put("status", session.getStatus().name());
            sessionData.put("createdAt", session.getCreatedAt().toString());
            if (session.getCompletedAt() != null) {
                sessionData.put("completedAt", session.getCompletedAt().toString());
            }
            if (session.getWinnerGroup() != null) {
                sessionData.put("winnerGroupName", session.getWinnerGroup().getGroupName());
            }
            result.add(sessionData);
        }

        return result;
    }

    public List<Map<String, Object>> getGroupRankings(Long userId) {
        // Get all completed sessions for this user
        List<GroupQuizSession> sessions = groupQuizSessionRepository.findByCreatedByIdOrderByCreatedAtDesc(userId);

        // Collect all participants from completed sessions
        List<Map<String, Object>> allParticipants = new ArrayList<>();

        for (GroupQuizSession session : sessions) {
            if (session.getStatus() == GroupQuizSession.SessionStatus.COMPLETED) {
                List<GroupParticipant> participants = groupParticipantRepository
                    .findBySessionIdOrderByGroupOrderAsc(session.getId());

                for (GroupParticipant participant : participants) {
                    Map<String, Object> rankingEntry = new HashMap<>();
                    rankingEntry.put("groupName", participant.getGroupName());
                    rankingEntry.put("sessionName", session.getSessionName());
                    rankingEntry.put("sessionCode", session.getSessionCode());
                    rankingEntry.put("score", participant.getScore());
                    rankingEntry.put("correctAnswers", participant.getCorrectAnswers());
                    rankingEntry.put("totalQuestions", participant.getTotalQuestions());
                    rankingEntry.put("percentage", participant.getPercentage());
                    rankingEntry.put("rank", participant.getRank());
                    rankingEntry.put("createdAt", session.getCreatedAt().toString());
                    allParticipants.add(rankingEntry);
                }
            }
        }

        // Sort by score descending, then by percentage
        allParticipants.sort((a, b) -> {
            int scoreCompare = Integer.compare((Integer) b.get("score"), (Integer) a.get("score"));
            if (scoreCompare != 0) return scoreCompare;
            return Double.compare((Double) b.get("percentage"), (Double) a.get("percentage"));
        });

        // Return top 10
        return allParticipants.stream()
            .limit(10)
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getSessionRankings(Long sessionId) {
        GroupQuizSession session = groupQuizSessionRepository.findById(sessionId).orElseThrow();

        if (session.getStatus() != GroupQuizSession.SessionStatus.COMPLETED) {
            throw new RuntimeException("Session not completed yet");
        }

        List<GroupParticipant> participants = groupParticipantRepository
            .findBySessionIdOrderByGroupOrderAsc(sessionId);

        // Sort by rank (already calculated in completeSession)
        participants.sort((a, b) -> Integer.compare(a.getRank(), b.getRank()));

        List<Map<String, Object>> rankings = new ArrayList<>();
        for (GroupParticipant participant : participants) {
            Map<String, Object> rankingEntry = new HashMap<>();
            rankingEntry.put("groupName", participant.getGroupName());
            rankingEntry.put("sessionName", session.getSessionName());
            rankingEntry.put("sessionCode", session.getSessionCode());
            rankingEntry.put("score", participant.getScore());
            rankingEntry.put("correctAnswers", participant.getCorrectAnswers());
            rankingEntry.put("totalQuestions", participant.getTotalQuestions());
            rankingEntry.put("percentage", participant.getPercentage());
            rankingEntry.put("rank", participant.getRank());
            rankings.add(rankingEntry);
        }

        return rankings;
    }

    @Transactional
    public void resetSession(Long sessionId, Long userId) {
        GroupQuizSession session = groupQuizSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));

        // Verify that the session belongs to this user
        if (!session.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: This session does not belong to you");
        }

        // Can only reset IN_PROGRESS sessions
        if (session.getStatus() != GroupQuizSession.SessionStatus.IN_PROGRESS) {
            throw new RuntimeException("Can only reset sessions that are in progress");
        }

        // Reset session status to WAITING
        session.setStatus(GroupQuizSession.SessionStatus.WAITING);
        session.setWinnerGroup(null);
        session.setCompletedAt(null);
        groupQuizSessionRepository.save(session);

        // Reset all participants scores
        List<GroupParticipant> participants = groupParticipantRepository.findBySessionId(sessionId);
        for (GroupParticipant participant : participants) {
            participant.setScore(0);
            participant.setCorrectAnswers(0);
            participant.setPercentage(0.0);
            participant.setRank(null);
        }
        groupParticipantRepository.saveAll(participants);

        // Delete all answers for this session
        List<GroupQuizAnswer> answers = groupQuizAnswerRepository.findBySessionId(sessionId);
        groupQuizAnswerRepository.deleteAll(answers);
    }

    private List<Map<String, Object>> formatQuestions(List<Question> questions) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Question question : questions) {
            Map<String, Object> qData = new HashMap<>();
            qData.put("id", question.getId());
            qData.put("questionText", question.getQuestionText());
            qData.put("difficulty", question.getDifficulty().name());
            qData.put("points", question.getPoints());

            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            Collections.shuffle(answers);

            List<Map<String, Object>> aData = answers.stream().map(a -> {
                Map<String, Object> answerMap = new HashMap<>();
                answerMap.put("id", a.getId());
                answerMap.put("answerText", a.getAnswerText());
                return answerMap;
            }).collect(Collectors.toList());

            qData.put("answers", aData);
            result.add(qData);
        }
        return result;
    }
}
