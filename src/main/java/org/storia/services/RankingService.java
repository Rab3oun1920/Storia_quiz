package org.storia.services;

import org.storia.dto.UserRankingDTO;
import org.storia.entities.UserGlobalScore;
import org.storia.entities.UserThemeScore;
import org.storia.repositories.UserGlobalScoreRepository;
import org.storia.repositories.UserThemeScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service de gestion des Classements
 * Gère les classements globaux et par thème
 */
@Service
@Transactional
public class RankingService {

    @Autowired
    private UserGlobalScoreRepository userGlobalScoreRepository;

    @Autowired
    private UserThemeScoreRepository userThemeScoreRepository;

    /**
     * Récupère le classement global paginé
     * @param page Numéro de la page (commence à 0)
     * @param size Nombre d'éléments par page
     * @return Page contenant les scores globaux triés par score décroissant
     */
    public Page<UserRankingDTO> getGlobalRankings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserGlobalScore> scores = userGlobalScoreRepository.findAllByOrderByTotalScoreDesc(pageable);
        return scores.map(this::convertToDTO);
    }

    /**
     * Convertit UserGlobalScore en DTO
     */
    private UserRankingDTO convertToDTO(UserGlobalScore score) {
        return new UserRankingDTO(
            score.getId(),
            score.getUser().getUsername(),
            score.getUser().getFirstName(),
            score.getUser().getLastName(),
            score.getUser().getCountry(),
            score.getTotalScore(),
            score.getTotalQuizzes(),
            score.getAverageScore(),
            score.getTotalCorrectAnswers(),
            score.getTotalQuestionsAnswered()
        );
    }

    /**
     * Récupère le classement pour un thème spécifique
     * @param themeId L'identifiant du thème
     * @param page Numéro de la page (commence à 0)
     * @param size Nombre d'éléments par page
     * @return Page contenant les scores du thème triés par score décroissant
     */
    public Page<UserThemeScore> getThemeRankings(Long themeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userThemeScoreRepository.findByThemeIdOrderByTotalScoreDesc(themeId, pageable);
    }

    /**
     * Récupère les statistiques complètes d'un utilisateur
     * @param userId L'identifiant de l'utilisateur
     * @return Map contenant le score global, les scores par thème et le rang global
     */
    public Map<String, Object> getUserStats(Long userId) {
        UserGlobalScore globalScore = userGlobalScoreRepository.findByUserId(userId).orElse(null);
        List<UserThemeScore> themeScores = userThemeScoreRepository.findByUserId(userId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("globalScore", globalScore);
        stats.put("themeScores", themeScores);
        stats.put("globalRank", calculateGlobalRank(userId));

        return stats;
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
}
