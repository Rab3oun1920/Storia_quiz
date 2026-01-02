package org.storia.services;

import org.storia.entities.Theme;
import org.storia.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des Thèmes
 * Gère les opérations CRUD sur les thèmes de quiz
 */
@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    /**
     * Récupère tous les thèmes actifs
     * @return Liste des thèmes actifs
     */
    public List<Theme> getAllActiveThemes() {
        List<Theme> themes = themeRepository.findByIsActiveTrueWithQuestions();
        // Calculer le nombre de questions pour chaque thème
        themes.forEach(theme -> {
            theme.setQuestionCount(theme.getQuestions().size());
        });
        return themes;
    }

    /**
     * Récupère un thème par son identifiant
     * @param id L'identifiant du thème
     * @return Optional contenant le thème si trouvé
     */
    public Optional<Theme> getThemeById(Long id) {
        return themeRepository.findById(id);
    }

    /**
     * Crée un nouveau thème
     * @param theme Le thème à créer
     * @return Le thème créé et sauvegardé
     */
    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    /**
     * Met à jour un thème existant
     * @param id L'identifiant du thème à mettre à jour
     * @param themeDetails Les nouvelles données du thème
     * @return Le thème mis à jour
     * @throws RuntimeException si le thème n'est pas trouvé
     */
    public Theme updateTheme(Long id, Theme themeDetails) {
        Theme theme = themeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Theme not found with id: " + id));

        theme.setName(themeDetails.getName());
        theme.setIcon(themeDetails.getIcon());
        theme.setDescription(themeDetails.getDescription());
        theme.setIsActive(themeDetails.getIsActive());

        return themeRepository.save(theme);
    }

    /**
     * Supprime un thème
     * @param id L'identifiant du thème à supprimer
     * @throws RuntimeException si le thème n'est pas trouvé
     */
    public void deleteTheme(Long id) {
        if (!themeRepository.existsById(id)) {
            throw new RuntimeException("Theme not found with id: " + id);
        }
        themeRepository.deleteById(id);
    }
}
