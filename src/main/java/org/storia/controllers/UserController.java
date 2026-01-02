package org.storia.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.storia.dto.UpdateProfileRequest;
import org.storia.entities.User;
import org.storia.repositories.UserRepository;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.UserService;

import java.util.Map;

/**
 * Controller de gestion des utilisateurs
 * Endpoints pour le profil utilisateur
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère le profil de l'utilisateur connecté
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    /**
     * Met à jour le profil de l'utilisateur
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

        User updatedUser = userService.updateProfile(userDetails.getId(), request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Récupère les statistiques de l'utilisateur
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

        Map<String, Object> stats = userService.getUserStats(userDetails.getId());
        return ResponseEntity.ok(stats);
    }

    /**
     * Récupère les statistiques par thème
     */
    @GetMapping("/theme-stats/{themeId}")
    public ResponseEntity<Map<String, Object>> getThemeStats(@PathVariable Long themeId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

        Map<String, Object> stats = userService.getThemeStats(userDetails.getId(), themeId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Récupère les quiz récents de l'utilisateur
     */
    @GetMapping("/recent-quizzes")
    public ResponseEntity<?> getRecentQuizzes(@RequestParam(defaultValue = "10") int limit) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.getRecentQuizzes(userDetails.getId(), limit));
    }
}
