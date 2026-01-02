package org.storia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité GroupQuizAnswer - Représente une réponse donnée par un groupe
 *
 * Table: group_quiz_answers
 * Trace toutes les réponses données par chaque groupe pendant la session
 */
@Entity
@Table(name = "group_quiz_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    @NotNull
    private GroupQuizSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_participant_id", nullable = false)
    @NotNull
    private GroupParticipant groupParticipant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @NotNull
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    @NotNull
    private Answer answer;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "time_spent", nullable = false)
    private Integer timeSpent; // en secondes

    @Column(name = "answered_at", nullable = false)
    private LocalDateTime answeredAt;

    @PrePersist
    protected void onCreate() {
        if (answeredAt == null) {
            answeredAt = LocalDateTime.now();
        }
    }
}
