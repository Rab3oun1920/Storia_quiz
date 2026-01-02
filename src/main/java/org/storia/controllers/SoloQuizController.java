package org.storia.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.storia.dto.QuizResultRequest;
import org.storia.entities.Theme;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.SoloQuizService;
import org.storia.services.ThemeService;

import java.util.List;
import java.util.Map;

/**
 * Controller de gestion des Quiz Solo
 * Endpoints pour démarrer, jouer et finaliser un quiz solo
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/solo")
@PreAuthorize("hasRole('USER')")
public class SoloQuizController {

    @Autowired
    private SoloQuizService soloQuizService;

    @Autowired
    private ThemeService themeService;

    /**
     * Récupère les thèmes disponibles pour le mode solo
     * @return Liste des thèmes actifs
     */
    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> getAvailableThemes() {
        return ResponseEntity.ok(themeService.getAllActiveThemes());
    }

    /**
     * Démarre un quiz solo pour un thème donné
     * @param themeId L'identifiant du thème
     * @return Les questions du quiz
     */
    @GetMapping("/{themeId}/start")
    public ResponseEntity<Map<String, Object>> startQuiz(@PathVariable Long themeId) {
        Map<String, Object> quizData = soloQuizService.startSoloQuiz(themeId);
        return ResponseEntity.ok(quizData);
    }

    /**
     * Vérifie si une réponse est correcte
     * @param request Contient l'ID de la réponse
     * @return Résultat (correct/incorrect) et points
     */
    @PostMapping("/check-answer")
    public ResponseEntity<Map<String, Object>> checkAnswer(@RequestBody Map<String, Long> request) {
        Long answerId = request.get("answerId");
        Map<String, Object> result = soloQuizService.checkAnswer(answerId);
        return ResponseEntity.ok(result);
    }

    /**
     * Finalise un quiz solo et enregistre les résultats
     * @param request Les résultats du quiz
     * @return Statistiques finales et classement
     */
    @PostMapping("/complete")
    public ResponseEntity<Map<String, Object>> completeQuiz(@Valid @RequestBody QuizResultRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        Map<String, Object> result = soloQuizService.completeSoloQuiz(userId, request);
        return ResponseEntity.ok(result);
    }
}
