package org.storia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité SoloQuizSession - Représente une session de quiz solo
 *
 * Table: solo_quiz_sessions
 * Format: 10 questions, 10 secondes par question, score max 200 points
 */
@Entity
@Table(name = "solo_quiz_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoloQuizSession {

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

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "correct_answers", nullable = false)
    private Integer correctAnswers;

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions = 10;

    @Column(name = "total_time", nullable = false)
    private Integer totalTime; // en secondes

    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        if (completedAt == null) {
            completedAt = LocalDateTime.now();
        }
        if (totalQuestions == null) {
            totalQuestions = 10;
        }
        // Calculer le pourcentage
        if (percentage == null && correctAnswers != null && totalQuestions != null) {
            percentage = (correctAnswers * 100.0) / totalQuestions;
        }
    }
}
