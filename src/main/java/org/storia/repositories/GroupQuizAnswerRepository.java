package org.storia.repositories;

import org.storia.entities.GroupQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité GroupQuizAnswer
 */
@Repository
public interface GroupQuizAnswerRepository extends JpaRepository<GroupQuizAnswer, Long> {

    List<GroupQuizAnswer> findBySessionId(Long sessionId);

    List<GroupQuizAnswer> findByGroupParticipantId(Long groupParticipantId);
}
