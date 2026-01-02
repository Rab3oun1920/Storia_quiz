package org.storia.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.storia.dto.*;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.PropositionService;

import java.util.List;
import java.util.Map;

/**
 * Controller de gestion des propositions
 * Endpoints pour les utilisateurs et les administrateurs
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PropositionController {

    @Autowired
    private PropositionService propositionService;

    /**
     * ENDPOINTS UTILISATEUR
     */

    /**
     * Créer une proposition de thème
     */
    @PostMapping("/user/propositions/theme")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createThemeProposition(@Valid @RequestBody PropositionThemeRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            PropositionResponse response = propositionService.createThemeProposition(userDetails.getId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Créer une proposition de question
     */
    @PostMapping("/user/propositions/question")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createQuestionProposition(@Valid @RequestBody PropositionQuestionRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            PropositionResponse response = propositionService.createQuestionProposition(userDetails.getId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Récupérer toutes les propositions de l'utilisateur connecté
     */
    @GetMapping("/user/propositions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserPropositions() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            List<PropositionResponse> propositions = propositionService.getUserPropositions(userDetails.getId());
            return ResponseEntity.ok(propositions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * ENDPOINTS ADMINISTRATEUR
     */

    /**
     * Récupérer toutes les propositions (admin)
     */
    @GetMapping("/admin/propositions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllPropositions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {
        try {
            List<PropositionResponse> propositions;

            if (type != null && !type.isEmpty()) {
                propositions = propositionService.getPropositionsByType(type);
            } else if (status != null && !status.isEmpty()) {
                propositions = propositionService.getPropositionsByStatus(status);
            } else {
                propositions = propositionService.getAllPropositions();
            }

            return ResponseEntity.ok(propositions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Approuver ou rejeter une proposition (admin)
     */
    @PutMapping("/admin/propositions/{id}/review")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> reviewProposition(
            @PathVariable Long id,
            @Valid @RequestBody AdminReviewRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            PropositionResponse response = propositionService.reviewProposition(
                id, userDetails.getId(), request
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtenir les statistiques des propositions (admin)
     */
    @GetMapping("/admin/propositions/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPropositionStats() {
        try {
            Map<String, Object> stats = propositionService.getPropositionStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
