package org.storia.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.storia.dto.AdminResponseRequest;
import org.storia.dto.ReclamationRequest;
import org.storia.dto.ReclamationResponse;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.ReclamationService;

import java.util.List;
import java.util.Map;

/**
 * Controller de gestion des réclamations
 * Endpoints pour les utilisateurs et les administrateurs
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    /**
     * ENDPOINTS UTILISATEUR
     */

    /**
     * Créer une nouvelle réclamation
     */
    @PostMapping("/user/reclamations")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createReclamation(@Valid @RequestBody ReclamationRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            ReclamationResponse response = reclamationService.createReclamation(userDetails.getId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Récupérer toutes les réclamations de l'utilisateur connecté
     */
    @GetMapping("/user/reclamations")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserReclamations() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            List<ReclamationResponse> reclamations = reclamationService.getUserReclamations(userDetails.getId());
            return ResponseEntity.ok(reclamations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * ENDPOINTS ADMINISTRATEUR
     */

    /**
     * Récupérer toutes les réclamations (admin)
     */
    @GetMapping("/admin/reclamations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllReclamations(@RequestParam(required = false) String status) {
        try {
            List<ReclamationResponse> reclamations;

            if (status != null && !status.isEmpty()) {
                reclamations = reclamationService.getReclamationsByStatus(status);
            } else {
                reclamations = reclamationService.getAllReclamations();
            }

            return ResponseEntity.ok(reclamations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Récupérer une réclamation spécifique (admin)
     */
    @GetMapping("/admin/reclamations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReclamationById(@PathVariable Long id) {
        try {
            ReclamationResponse reclamation = reclamationService.getReclamationById(id);
            return ResponseEntity.ok(reclamation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Répondre à une réclamation (admin)
     */
    @PutMapping("/admin/reclamations/{id}/respond")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> respondToReclamation(
            @PathVariable Long id,
            @Valid @RequestBody AdminResponseRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

            ReclamationResponse response = reclamationService.respondToReclamation(
                id, userDetails.getId(), request
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Mettre à jour le statut d'une réclamation (admin)
     */
    @PutMapping("/admin/reclamations/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        try {
            String status = payload.get("status");
            ReclamationResponse response = reclamationService.updateStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtenir les statistiques des réclamations (admin)
     */
    @GetMapping("/admin/reclamations/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReclamationStats() {
        try {
            Map<String, Object> stats = reclamationService.getReclamationStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
