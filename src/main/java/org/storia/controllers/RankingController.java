package org.storia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.storia.dto.UserRankingDTO;
import org.storia.entities.UserThemeScore;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.RankingService;

import java.util.Map;

/**
 * Controller de gestion des Classements
 * Endpoints pour consulter les classements et statistiques
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rankings")
@PreAuthorize("hasRole('USER')")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    /**
     * Récupère le classement global paginé
     * @param page Numéro de la page (par défaut 0)
     * @param size Nombre d'éléments par page (par défaut 50)
     * @return Page de classement global
     */
    @GetMapping("/global")
    public ResponseEntity<Page<UserRankingDTO>> getGlobalRankings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(rankingService.getGlobalRankings(page, size));
    }

    /**
     * Récupère le classement pour un thème spécifique
     * @param themeId L'identifiant du thème
     * @param page Numéro de la page (par défaut 0)
     * @param size Nombre d'éléments par page (par défaut 50)
     * @return Page de classement pour le thème
     */
    @GetMapping("/theme/{themeId}")
    public ResponseEntity<Page<UserThemeScore>> getThemeRankings(
            @PathVariable Long themeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(rankingService.getThemeRankings(themeId, page, size));
    }

    /**
     * Récupère les statistiques de l'utilisateur connecté
     * @return Statistiques complètes de l'utilisateur
     */
    @GetMapping("/user/stats")
    public ResponseEntity<Map<String, Object>> getUserStats() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        return ResponseEntity.ok(rankingService.getUserStats(userId));
    }
}
