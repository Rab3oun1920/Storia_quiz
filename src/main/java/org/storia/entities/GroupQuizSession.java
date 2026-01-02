package org.storia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité GroupQuizSession - Représente une session de quiz en groupe
 *
 * Table: group_quiz_sessions
 * Format: 2-4 groupes, 20/30/40 questions selon le nombre de groupes
 */
@Entity
@Table(name = "group_quiz_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "session_name", nullable = false)
    private String sessionName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "session_code", nullable = false, unique = true)
    private String sessionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    @NotNull
    private Theme theme;

    @Column(name = "number_of_groups", nullable = false)
    private Integer numberOfGroups;

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;

    @Column(name = "time_per_question", nullable = false)
    private Integer timePerQuestion = 5; // 5 secondes par question

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SessionStatus status = SessionStatus.WAITING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    @NotNull
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_group_id")
    private GroupParticipant winnerGroup;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupParticipant> participants = new ArrayList<>();

    public enum SessionStatus {
        WAITING,
        IN_PROGRESS,
        COMPLETED
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = SessionStatus.WAITING;
        }
        if (timePerQuestion == null) {
            timePerQuestion = 5;
        }
        // Calculer le nombre de questions selon le nombre de groupes
        if (totalQuestions == null && numberOfGroups != null) {
            totalQuestions = switch (numberOfGroups) {
                case 2 -> 20;
                case 3 -> 30;
                case 4 -> 40;
                default -> 20;
            };
        }
    }
}
