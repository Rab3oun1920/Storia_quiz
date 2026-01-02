package org.storia.repositories;

import org.storia.entities.Question;
import org.storia.entities.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Question
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByThemeIdAndIsSoloQuestionTrue(Long themeId);

    List<Question> findByThemeIdAndIsGroupQuestionTrue(Long themeId);

    List<Question> findByDifficulty(Question.Difficulty difficulty);

    List<Question> findByThemeAndDifficulty(Theme theme, Question.Difficulty difficulty);

    @Query(value = "SELECT * FROM questions WHERE theme_id = :themeId AND is_solo_question = true ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomSoloQuestionsByThemeId(@Param("themeId") Long themeId, @Param("limit") int limit);

    @Query(value = "SELECT * FROM questions WHERE theme_id = :themeId AND is_group_question = true ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomGroupQuestionsByThemeId(@Param("themeId") Long themeId, @Param("limit") int limit);
}
