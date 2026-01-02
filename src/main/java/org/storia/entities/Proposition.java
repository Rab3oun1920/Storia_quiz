package org.storia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité Proposition - Représente une proposition de thème ou question
 *
 * Table: propositions
 */
@Entity
@Table(name = "propositions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Proposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private EPropositionType type;

    // Champs pour les thèmes
    @Size(max = 100)
    @Column(name = "theme_title")
    private String themeTitle;

    @Column(name = "theme_description", columnDefinition = "TEXT")
    private String themeDescription;

    @Size(max = 50)
    @Column(name = "theme_icon")
    private String themeIcon;

    // Champs pour les questions
    @Column(name = "question_text", columnDefinition = "TEXT")
    private String questionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 20)
    private Question.Difficulty difficulty;

    @Size(max = 500)
    @Column(name = "correct_answer")
    private String correctAnswer;

    @Size(max = 500)
    @Column(name = "wrong_answer_1")
    private String wrongAnswer1;

    @Size(max = 500)
    @Column(name = "wrong_answer_2")
    private String wrongAnswer2;

    @Size(max = 500)
    @Column(name = "wrong_answer_3")
    private String wrongAnswer3;

    // Gestion
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EPropositionStatus status = EPropositionStatus.EN_ATTENTE;

    @Column(name = "admin_comment", columnDefinition = "TEXT")
    private String adminComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_id")
    private User reviewedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = EPropositionStatus.EN_ATTENTE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
