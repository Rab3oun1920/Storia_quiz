package org.storia.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultRequest {

    @NotNull
    private Long themeId;

    @NotNull
    private Integer score;

    @NotNull
    private Integer correctAnswers;

    @NotNull
    private Integer totalQuestions;

    @NotNull
    private Integer totalTime;
}
