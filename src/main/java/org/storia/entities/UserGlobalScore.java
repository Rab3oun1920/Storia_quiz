package org.storia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité UserGlobalScore - Représente le score global d'un utilisateur
 *
 * Table: user_global_scores
 * Agrège tous les quiz solo joués par un utilisateur
 */
@Entity
@Table(name = "user_global_scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "bestTheme"})
public class UserGlobalScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @NotNull
    private User user;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore = 0;

    @Column(name = "total_quizzes", nullable = false)
    private Integer totalQuizzes = 0;

    @Column(name = "average_score", nullable = false)
    private Double averageScore = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "best_theme_id")
    private Theme bestTheme;

    @Column(name = "total_correct_answers", nullable = false)
    private Integer totalCorrectAnswers = 0;

    @Column(name = "total_questions_answered", nullable = false)
    private Integer totalQuestionsAnswered = 0;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        // Calculer la moyenne
        if (totalQuizzes != null && totalQuizzes > 0 && totalScore != null) {
            averageScore = (double) totalScore / totalQuizzes;
        }
    }
}
