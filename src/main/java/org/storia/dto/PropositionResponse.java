package org.storia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropositionResponse {

    private Long id;
    private Long userId;
    private String username;
    private String type;  // THEME ou QUESTION

    // Pour les thèmes
    private String themeTitle;
    private String themeDescription;
    private String themeIcon;

    // Pour les questions
    private String questionText;
    private Long themeId;
    private String themeName;
    private String difficulty;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    // Gestion
    private String status;
    private String adminComment;
    private String reviewedByUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime reviewedAt;
}
