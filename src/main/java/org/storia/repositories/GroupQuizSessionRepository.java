package org.storia.repositories;

import org.storia.entities.GroupQuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité GroupQuizSession
 */
@Repository
public interface GroupQuizSessionRepository extends JpaRepository<GroupQuizSession, Long> {

    List<GroupQuizSession> findByCreatedByIdOrderByCreatedAtDesc(Long userId);

    boolean existsBySessionNameAndCreatedById(String sessionName, Long userId);
}
