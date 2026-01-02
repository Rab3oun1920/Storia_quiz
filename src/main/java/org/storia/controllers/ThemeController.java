package org.storia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.storia.entities.Theme;
import org.storia.services.ThemeService;

import java.util.List;

/**
 * Controller de gestion des Thèmes
 * Endpoints pour récupérer les thèmes disponibles
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    /**
     * Récupère tous les thèmes actifs
     * @return Liste des thèmes actifs
     */
    @GetMapping("")
    public ResponseEntity<List<Theme>> getAllActiveThemes() {
        return ResponseEntity.ok(themeService.getAllActiveThemes());
    }

    /**
     * Récupère un thème par son ID
     * @param id L'identifiant du thème
     * @return Le thème trouvé ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
