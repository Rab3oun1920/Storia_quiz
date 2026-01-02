package org.storia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité GroupParticipant - Représente un groupe participant dans une session
 *
 * Table: group_participants
 * Chaque groupe a un nom, un ordre de passage et son score
 */
@Entity
@Table(name = "group_participants",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"session_id", "group_name"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    @NotNull
    private GroupQuizSession session;

    @NotBlank
    @Size(max = 100)
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_order", nullable = false)
    private Integer groupOrder;

    @Column(name = "score", nullable = false)
    private Integer score = 0;

    @Column(name = "correct_answers", nullable = false)
    private Integer correctAnswers = 0;

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions = 10;

    @Column(name = "percentage", nullable = false)
    private Double percentage = 0.0;

    @Column(name = "rank")
    private Integer rank;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        // Calculer le pourcentage
        if (totalQuestions != null && totalQuestions > 0 && correctAnswers != null) {
            percentage = (correctAnswers * 100.0) / totalQuestions;
        }
    }
}
