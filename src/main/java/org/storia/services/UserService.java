package org.storia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storia.dto.UpdateProfileRequest;
import org.storia.entities.SoloQuizSession;
import org.storia.entities.User;
import org.storia.entities.UserGlobalScore;
import org.storia.entities.UserThemeScore;
import org.storia.repositories.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGlobalScoreRepository userGlobalScoreRepository;

    @Autowired
    private UserThemeScoreRepository userThemeScoreRepository;

    @Autowired
    private SoloQuizSessionRepository soloQuizSessionRepository;

    /**
     * Met à jour le profil utilisateur
     */
    public User updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getCountry() != null) {
            user.setCountry(request.getCountry());
        }

        return userRepository.save(user);
    }

    /**
     * Récupère les statistiques globales de l'utilisateur
     */
    public Map<String, Object> getUserStats(Long userId) {
        UserGlobalScore globalScore = userGlobalScoreRepository.findByUserId(userId).orElse(null);

        Map<String, Object> stats = new HashMap<>();
        if (globalScore != null) {
            stats.put("totalQuizzes", globalScore.getTotalQuizzes());
            stats.put("totalScore", globalScore.getTotalScore());
            stats.put("averageScore", globalScore.getAverageScore());
            stats.put("totalCorrectAnswers", globalScore.getTotalCorrectAnswers());
            stats.put("totalQuestionsAnswered", globalScore.getTotalQuestionsAnswered());
            stats.put("globalRank", calculateGlobalRank(userId));
        } else {
            stats.put("totalQuizzes", 0);
            stats.put("totalScore", 0);
            stats.put("averageScore", 0.0);
            stats.put("totalCorrectAnswers", 0);
            stats.put("totalQuestionsAnswered", 0);
            stats.put("globalRank", 0);
        }
        stats.put("totalTime", 0); // TODO: calculer le temps total

        return stats;
    }

    /**
     * Récupère les statistiques pour un thème spécifique
     */
    public Map<String, Object> getThemeStats(Long userId, Long themeId) {
        UserThemeScore themeScore = userThemeScoreRepository.findByUserIdAndThemeId(userId, themeId)
            .orElse(null);

        Map<String, Object> stats = new HashMap<>();
        if (themeScore != null) {
            stats.put("quizCount", themeScore.getQuizzesPlayed());
            stats.put("totalScore", themeScore.getTotalScore());
            stats.put("bestScore", themeScore.getBestScore());
            stats.put("averageScore", themeScore.getAverageScore());
            stats.put("themeRank", calculateThemeRank(userId, themeId));
        } else {
            stats.put("quizCount", 0);
            stats.put("totalScore", 0);
            stats.put("bestScore", 0);
            stats.put("averageScore", 0.0);
            stats.put("themeRank", 0);
        }

        return stats;
    }

    /**
     * Récupère les quiz récents de l'utilisateur
     */
    public List<Map<String, Object>> getRecentQuizzes(Long userId, int limit) {
        List<SoloQuizSession> sessions = soloQuizSessionRepository
            .findByUserIdOrderByCompletedAtDesc(userId);

        return sessions.stream().limit(limit).map(session -> {
            Map<String, Object> quiz = new HashMap<>();
            quiz.put("id", session.getId());
            quiz.put("themeName", session.getTheme().getName());
            quiz.put("themeIcon", session.getTheme().getIcon());
            quiz.put("score", session.getScore());
            quiz.put("correctAnswers", session.getCorrectAnswers());
            quiz.put("totalQuestions", session.getTotalQuestions());
            quiz.put("percentage", session.getPercentage());
            quiz.put("totalTime", session.getTotalTime());
            quiz.put("completedAt", session.getCompletedAt());
            return quiz;
        }).collect(Collectors.toList());
    }

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

    private int calculateThemeRank(Long userId, Long themeId) {
        UserThemeScore userScore = userThemeScoreRepository.findByUserIdAndThemeId(userId, themeId)
            .orElse(null);
        if (userScore == null) return 0;

        List<UserThemeScore> allScores = userThemeScoreRepository
            .findTop50ByThemeIdOrderByTotalScoreDesc(themeId);
        for (int i = 0; i < allScores.size(); i++) {
            if (allScores.get(i).getId().equals(userScore.getId())) {
                return i + 1;
            }
        }
        return allScores.size() + 1;
    }
}
