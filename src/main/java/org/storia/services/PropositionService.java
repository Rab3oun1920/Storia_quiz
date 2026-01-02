package org.storia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storia.dto.*;
import org.storia.entities.*;
import org.storia.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropositionService {

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    /**
     * Créer une proposition de thème
     */
    public PropositionResponse createThemeProposition(Long userId, PropositionThemeRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Proposition proposition = new Proposition();
        proposition.setUser(user);
        proposition.setType(EPropositionType.THEME);
        proposition.setThemeTitle(request.getThemeTitle());
        proposition.setThemeDescription(request.getThemeDescription());
        proposition.setThemeIcon(request.getThemeIcon());
        proposition.setStatus(EPropositionStatus.EN_ATTENTE);

        Proposition saved = propositionRepository.save(proposition);
        return convertToResponse(saved);
    }

    /**
     * Créer une proposition de question
     */
    public PropositionResponse createQuestionProposition(Long userId, PropositionQuestionRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(request.getThemeId())
            .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        Proposition proposition = new Proposition();
        proposition.setUser(user);
        proposition.setType(EPropositionType.QUESTION);
        proposition.setQuestionText(request.getQuestionText());
        proposition.setTheme(theme);
        proposition.setDifficulty(Question.Difficulty.valueOf(request.getDifficulty()));
        proposition.setCorrectAnswer(request.getCorrectAnswer());
        proposition.setWrongAnswer1(request.getWrongAnswer1());
        proposition.setWrongAnswer2(request.getWrongAnswer2());
        proposition.setWrongAnswer3(request.getWrongAnswer3());
        proposition.setStatus(EPropositionStatus.EN_ATTENTE);

        Proposition saved = propositionRepository.save(proposition);
        return convertToResponse(saved);
    }

    /**
     * Récupérer toutes les propositions d'un utilisateur
     */
    public List<PropositionResponse> getUserPropositions(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Proposition> propositions = propositionRepository.findByUserOrderByCreatedAtDesc(user);
        return propositions.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Récupérer toutes les propositions (admin)
     */
    public List<PropositionResponse> getAllPropositions() {
        List<Proposition> propositions = propositionRepository.findAllByOrderByCreatedAtDesc();
        return propositions.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Récupérer les propositions par type
     */
    public List<PropositionResponse> getPropositionsByType(String type) {
        EPropositionType eType = EPropositionType.valueOf(type);
        List<Proposition> propositions = propositionRepository.findByTypeOrderByCreatedAtDesc(eType);
        return propositions.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Récupérer les propositions par statut
     */
    public List<PropositionResponse> getPropositionsByStatus(String status) {
        EPropositionStatus eStatus = EPropositionStatus.valueOf(status);
        List<Proposition> propositions = propositionRepository.findByStatusOrderByCreatedAtDesc(eStatus);
        return propositions.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Approuver ou rejeter une proposition (admin)
     */
    public PropositionResponse reviewProposition(Long propositionId, Long adminId, AdminReviewRequest request) {
        Proposition proposition = propositionRepository.findById(propositionId)
            .orElseThrow(() -> new RuntimeException("Proposition non trouvée"));

        User admin = userRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        EPropositionStatus status = EPropositionStatus.valueOf(request.getStatus());
        proposition.setStatus(status);
        proposition.setAdminComment(request.getComment());
        proposition.setReviewedBy(admin);
        proposition.setReviewedAt(LocalDateTime.now());

        // Si approuvé, ajouter au système
        if (status == EPropositionStatus.APPROUVE) {
            if (proposition.getType() == EPropositionType.THEME) {
                createThemeFromProposition(proposition);
            } else if (proposition.getType() == EPropositionType.QUESTION) {
                createQuestionFromProposition(proposition);
            }
        }

        Proposition saved = propositionRepository.save(proposition);
        return convertToResponse(saved);
    }

    /**
     * Créer un thème à partir d'une proposition approuvée
     */
    private void createThemeFromProposition(Proposition proposition) {
        Theme theme = new Theme();
        theme.setName(proposition.getThemeTitle());
        theme.setDescription(proposition.getThemeDescription());
        theme.setIcon(proposition.getThemeIcon());
        theme.setIsActive(true);
        themeRepository.save(theme);
    }

    /**
     * Créer une question à partir d'une proposition approuvée
     */
    private void createQuestionFromProposition(Proposition proposition) {
        Question question = new Question();
        question.setQuestionText(proposition.getQuestionText());
        question.setTheme(proposition.getTheme());
        question.setDifficulty(proposition.getDifficulty());
        question.setIsSoloQuestion(true);
        question.setIsGroupQuestion(true);

        // Calculer les points selon la difficulté
        int points = switch (proposition.getDifficulty()) {
            case EASY -> 10;
            case MEDIUM -> 20;
            case HARD -> 30;
        };
        question.setPoints(points);

        Question savedQuestion = questionRepository.save(question);

        // Créer les réponses
        Answer correctAnswer = new Answer();
        correctAnswer.setQuestion(savedQuestion);
        correctAnswer.setAnswerText(proposition.getCorrectAnswer());
        correctAnswer.setIsCorrect(true);
        answerRepository.save(correctAnswer);

        Answer wrongAnswer1 = new Answer();
        wrongAnswer1.setQuestion(savedQuestion);
        wrongAnswer1.setAnswerText(proposition.getWrongAnswer1());
        wrongAnswer1.setIsCorrect(false);
        answerRepository.save(wrongAnswer1);

        Answer wrongAnswer2 = new Answer();
        wrongAnswer2.setQuestion(savedQuestion);
        wrongAnswer2.setAnswerText(proposition.getWrongAnswer2());
        wrongAnswer2.setIsCorrect(false);
        answerRepository.save(wrongAnswer2);

        Answer wrongAnswer3 = new Answer();
        wrongAnswer3.setQuestion(savedQuestion);
        wrongAnswer3.setAnswerText(proposition.getWrongAnswer3());
        wrongAnswer3.setIsCorrect(false);
        answerRepository.save(wrongAnswer3);
    }

    /**
     * Obtenir les statistiques des propositions
     */
    public java.util.Map<String, Object> getPropositionStats() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();

        stats.put("total", propositionRepository.count());
        stats.put("enAttente", propositionRepository.countByStatus(EPropositionStatus.EN_ATTENTE));
        stats.put("approuve", propositionRepository.countByStatus(EPropositionStatus.APPROUVE));
        stats.put("rejete", propositionRepository.countByStatus(EPropositionStatus.REJETE));
        stats.put("themes", propositionRepository.countByType(EPropositionType.THEME));
        stats.put("questions", propositionRepository.countByType(EPropositionType.QUESTION));

        return stats;
    }

    /**
     * Convertir une Proposition en PropositionResponse
     */
    private PropositionResponse convertToResponse(Proposition proposition) {
        PropositionResponse response = new PropositionResponse();
        response.setId(proposition.getId());
        response.setUserId(proposition.getUser().getId());
        response.setUsername(proposition.getUser().getUsername());
        response.setType(proposition.getType().name());

        // Champs thème
        response.setThemeTitle(proposition.getThemeTitle());
        response.setThemeDescription(proposition.getThemeDescription());
        response.setThemeIcon(proposition.getThemeIcon());

        // Champs question
        response.setQuestionText(proposition.getQuestionText());
        if (proposition.getTheme() != null) {
            response.setThemeId(proposition.getTheme().getId());
            response.setThemeName(proposition.getTheme().getName());
        }
        if (proposition.getDifficulty() != null) {
            response.setDifficulty(proposition.getDifficulty().name());
        }
        response.setCorrectAnswer(proposition.getCorrectAnswer());
        response.setWrongAnswer1(proposition.getWrongAnswer1());
        response.setWrongAnswer2(proposition.getWrongAnswer2());
        response.setWrongAnswer3(proposition.getWrongAnswer3());

        // Gestion
        response.setStatus(proposition.getStatus().name());
        response.setAdminComment(proposition.getAdminComment());
        if (proposition.getReviewedBy() != null) {
            response.setReviewedByUsername(proposition.getReviewedBy().getUsername());
        }

        response.setCreatedAt(proposition.getCreatedAt());
        response.setUpdatedAt(proposition.getUpdatedAt());
        response.setReviewedAt(proposition.getReviewedAt());

        return response;
    }
}
