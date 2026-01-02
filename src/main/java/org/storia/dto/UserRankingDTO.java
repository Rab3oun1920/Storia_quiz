package org.storia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRankingDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String country;
    private Integer totalScore;
    private Integer totalQuizzes;
    private Double averageScore;
    private Integer totalCorrectAnswers;
    private Integer totalQuestionsAnswered;
}
