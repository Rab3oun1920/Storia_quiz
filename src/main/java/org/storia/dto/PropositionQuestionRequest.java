package org.storia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropositionQuestionRequest {

    @NotBlank(message = "La question ne peut pas être vide")
    private String questionText;

    @NotNull(message = "Le thème est requis")
    private Long themeId;

    @NotBlank(message = "La difficulté est requise")
    private String difficulty;  // EASY, MEDIUM, HARD

    @NotBlank(message = "La réponse correcte ne peut pas être vide")
    @Size(max = 500, message = "La réponse ne peut pas dépasser 500 caractères")
    private String correctAnswer;

    @NotBlank(message = "La première mauvaise réponse ne peut pas être vide")
    @Size(max = 500, message = "La réponse ne peut pas dépasser 500 caractères")
    private String wrongAnswer1;

    @NotBlank(message = "La deuxième mauvaise réponse ne peut pas être vide")
    @Size(max = 500, message = "La réponse ne peut pas dépasser 500 caractères")
    private String wrongAnswer2;

    @NotBlank(message = "La troisième mauvaise réponse ne peut pas être vide")
    @Size(max = 500, message = "La réponse ne peut pas dépasser 500 caractères")
    private String wrongAnswer3;
}
