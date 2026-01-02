package org.storia.repositories;

import org.storia.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository pour l'entité Answer
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);

    List<Answer> findByQuestionIdOrderByAnswerOrder(Long questionId);

    @Transactional
    @Modifying
    void deleteByQuestionId(Long questionId);
}
