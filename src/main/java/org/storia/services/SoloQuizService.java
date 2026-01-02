package org.storia.services;

import org.storia.dto.QuizResultRequest;
import org.storia.entities.*;
import org.storia.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service de gestion des Quiz en Mode Solo
 * Gère le démarrage, la vérification des réponses et la finalisation des quiz solo
 */
@Service
public class SoloQuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SoloQuizSessionRepository soloQuizSessionRepository;

    @Autowired
    private UserGlobalScoreRepository userGlobalScoreRepository;

    @Autowired
    private UserThemeScoreRepository userThemeScoreRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Démarre un quiz solo pour un thème donné
     * Sélectionne 10 questions : 3 faciles, 4 moyennes, 3 difficiles
     * @param themeId L'identifiant du thème
     * @return Map contenant les questions et métadonnées du quiz
     */
    public Map<String, Object> startSoloQuiz(Long themeId) {
        // 1. Récupère le thème et ses questions
        Theme theme = themeRepository.findById(themeId)
            .orElseThrow(() -> new RuntimeException("Theme not found with id: " + themeId));

        List<Question> allQuestions = questionRepository.findByThemeIdAndIsSoloQuestionTrue(themeId);

        if (allQuestions.isEmpty()) {
            throw new RuntimeException("No questions found for theme id: " + themeId);
        }

        // 2. Filtre par difficulté
        List<Question> easyQuestions = allQuestions.stream()
            .filter(q -> q.getDifficulty() == Question.Difficulty.EASY)
            .collect(Collectors.toList());
        List<Question> mediumQuestions = allQuestions.stream()
            .filter(q -> q.getDifficulty() == Question.Difficulty.MEDIUM)
            .collect(Collectors.toList());
        List<Question> hardQuestions = allQuestions.stream()
            .filter(q -> q.getDifficulty() == Question.Difficulty.HARD)
            .collect(Collectors.toList());

        // 3. Mélange et sélectionne
        Collections.shuffle(easyQuestions);
        Collections.shuffle(mediumQuestions);
        Collections.shuffle(hardQuestions);

        List<Question> selectedQuestions = new ArrayList<>();
        selectedQuestions.addAll(easyQuestions.subList(0, Math.min(3, easyQuestions.size())));
        selectedQuestions.addAll(mediumQuestions.subList(0, Math.min(4, mediumQuestions.size())));
        selectedQuestions.addAll(hardQuestions.subList(0, Math.min(3, hardQuestions.size())));

        // 4. Mélange l'ordre final
        Collections.shuffle(selectedQuestions);

        // 5. Prépare le résultat
        List<Map<String, Object>> questionsData = new ArrayList<>();
        for (Question question : selectedQuestions) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("id", question.getId());
            questionData.put("questionText", question.getQuestionText());
            questionData.put("difficulty", question.getDifficulty().name());
            questionData.put("points", question.getPoints());

            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            Collections.shuffle(answers); // Mélange les réponses

            List<Map<String, Object>> answersData = answers.stream().map(answer -> {
                Map<String, Object> answerData = new HashMap<>();
                answerData.put("id", answer.getId());
                answerData.put("answerText", answer.getAnswerText());
                // NE PAS envoyer isCorrect au frontend !
                return answerData;
            }).collect(Collectors.toList());

            questionData.put("answers", answersData);
            questionsData.add(questionData);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("themeName", theme.getName());
        result.put("questions", questionsData);
        result.put("totalQuestions", selectedQuestions.size());
        result.put("timePerQuestion", 10); // 10 secondes par question

        return result;
    }

    /**
     * Vérifie si une réponse est correcte
     * @param answerId L'identifiant de la réponse
     * @return Map contenant le résultat (correct/incorrect) et les points
     */
    @Transactional
    public Map<String, Object> checkAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
            .orElseThrow(() -> new RuntimeException("Answer not found with id: " + answerId));

        Map<String, Object> result = new HashMap<>();
        result.put("correct", answer.getIsCorrect());
        result.put("points", answer.getIsCorrect() ? answer.getQuestion().getPoints() : 0);

        return result;
    }

    /**
     * Finalise un quiz solo et met à jour les scores
     * @param userId L'identifiant de l'utilisateur
     * @param request Les résultats du quiz
     * @return Map contenant les statistiques finales
     */
    @Transactional
    public Map<String, Object> completeSoloQuiz(Long userId, QuizResultRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Theme theme = themeRepository.findById(request.getThemeId())
            .orElseThrow(() -> new RuntimeException("Theme not found with id: " + request.getThemeId()));

        // 1. Sauvegarder la session
        SoloQuizSession session = new SoloQuizSession();
        session.setUser(user);
        session.setTheme(theme);
        session.setScore(request.getScore());
        session.setCorrectAnswers(request.getCorrectAnswers());
        session.setTotalQuestions(request.getTotalQuestions());
        session.setTotalTime(request.getTotalTime());
        session.setPercentage((request.getCorrectAnswers() * 100.0) / request.getTotalQuestions());
        soloQuizSessionRepository.save(session);

        // 2. Mettre à jour UserGlobalScore
        UserGlobalScore globalScore = userGlobalScoreRepository.findByUserId(userId)
            .orElse(new UserGlobalScore());
        if (globalScore.getId() == null) {
            globalScore.setUser(user);
            globalScore.setTotalScore(0);
            globalScore.setTotalQuizzes(0);
            globalScore.setTotalCorrectAnswers(0);
            globalScore.setTotalQuestionsAnswered(0);
        }

        globalScore.setTotalScore(globalScore.getTotalScore() + request.getScore());
        globalScore.setTotalQuizzes(globalScore.getTotalQuizzes() + 1);
        globalScore.setTotalCorrectAnswers(globalScore.getTotalCorrectAnswers() + request.getCorrectAnswers());
        globalScore.setTotalQuestionsAnswered(globalScore.getTotalQuestionsAnswered() + request.getTotalQuestions());
        globalScore.setAverageScore((double) globalScore.getTotalScore() / globalScore.getTotalQuizzes());
        userGlobalScoreRepository.save(globalScore);

        // 3. Mettre à jour UserThemeScore
        UserThemeScore themeScore = userThemeScoreRepository.findByUserIdAndThemeId(userId, request.getThemeId())
            .orElse(new UserThemeScore());
        if (themeScore.getId() == null) {
            themeScore.setUser(user);
            themeScore.setTheme(theme);
            themeScore.setTotalScore(0);
            themeScore.setQuizzesPlayed(0);
            themeScore.setBestScore(0);
            themeScore.setTotalCorrectAnswers(0);
        }

        themeScore.setTotalScore(themeScore.getTotalScore() + request.getScore());
        themeScore.setQuizzesPlayed(themeScore.getQuizzesPlayed() + 1);
        themeScore.setTotalCorrectAnswers(themeScore.getTotalCorrectAnswers() + request.getCorrectAnswers());
        if (request.getScore() > themeScore.getBestScore()) {
            themeScore.setBestScore(request.getScore());
        }
        themeScore.setAverageScore((double) themeScore.getTotalScore() / themeScore.getQuizzesPlayed());
        userThemeScoreRepository.save(themeScore);

        // 4. Préparer la réponse
        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", session.getId());
        result.put("score", session.getScore());
        result.put("percentage", session.getPercentage());
        result.put("globalScore", globalScore.getTotalScore());
        result.put("globalRank", calculateGlobalRank(userId));
        result.put("themeRank", calculateThemeRank(userId, request.getThemeId()));

        return result;
    }

    /**
     * Calcule le rang global d'un utilisateur
     * @param userId L'identifiant de l'utilisateur
     * @return Le rang global (position dans le classement)
     */
    private int calculateGlobalRank(Long userId) {
        UserGlobalScore userScore = userGlobalScoreRepository.findByUserId(userId).orElse(null);
        if (userScore == null) return 0;

        List<UserGlobalScore> allScores = userGlobalScoreRepository.findTop50ByOrderByTotalScoreDesc();
        for (int i = 0; i < allScores.size(); i++) {
            if (allScores.get(i).getId().equals(userScore.getId())) {
                return i + 1;
            }
        }
        return allScores.size() + 1;
    }

    /**
     * Calcule le rang d'un utilisateur pour un thème spécifique
     * @param userId L'identifiant de l'utilisateur
     * @param themeId L'identifiant du thème
     * @return Le rang pour ce thème (position dans le classement)
     */
    private int calculateThemeRank(Long userId, Long themeId) {
        UserThemeScore userScore = userThemeScoreRepository.findByUserIdAndThemeId(userId, themeId).orElse(null);
        if (userScore == null) return 0;

        List<UserThemeScore> allScores = userThemeScoreRepository.findTop50ByThemeIdOrderByTotalScoreDesc(themeId);
        for (int i = 0; i < allScores.size(); i++) {
            if (allScores.get(i).getId().equals(userScore.getId())) {
                return i + 1;
            }
        }
        return allScores.size() + 1;
    }
}
