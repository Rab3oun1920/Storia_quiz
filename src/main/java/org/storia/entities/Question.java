package org.storia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Question - Représente une question de quiz
 *
 * Table: questions
 * Difficulté: EASY (10 points), MEDIUM (20 points), HARD (30 points)
 */
@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    @NotNull
    private Theme theme;

    @NotBlank
    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false, length = 10)
    @NotNull
    private Difficulty difficulty;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "is_solo_question", nullable = false)
    private Boolean isSoloQuestion = true;

    @Column(name = "is_group_question", nullable = false)
    private Boolean isGroupQuestion = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Answer> answers = new ArrayList<>();

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isSoloQuestion == null) {
            isSoloQuestion = true;
        }
        if (isGroupQuestion == null) {
            isGroupQuestion = true;
        }
        // Assigner les points selon la difficulté
        if (points == null && difficulty != null) {
            points = switch (difficulty) {
                case EASY -> 10;
                case MEDIUM -> 20;
                case HARD -> 30;
            };
        }
    }
}
