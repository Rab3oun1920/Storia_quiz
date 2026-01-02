package org.storia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité UserThemeScore - Représente le score d'un utilisateur pour un thème spécifique
 *
 * Table: user_theme_scores
 * Une ligne par utilisateur et par thème
 */
@Entity
@Table(name = "user_theme_scores",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "theme_id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserThemeScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    @NotNull
    private Theme theme;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore = 0;

    @Column(name = "quizzes_played", nullable = false)
    private Integer quizzesPlayed = 0;

    @Column(name = "best_score", nullable = false)
    private Integer bestScore = 0;

    @Column(name = "average_score", nullable = false)
    private Double averageScore = 0.0;

    @Column(name = "total_correct_answers", nullable = false)
    private Integer totalCorrectAnswers = 0;

    @PreUpdate
    protected void onUpdate() {
        // Calculer la moyenne
        if (quizzesPlayed != null && quizzesPlayed > 0 && totalScore != null) {
            averageScore = (double) totalScore / quizzesPlayed;
        }
    }
}
