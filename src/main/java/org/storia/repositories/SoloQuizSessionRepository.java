package org.storia.repositories;

import org.storia.entities.SoloQuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité SoloQuizSession
 */
@Repository
public interface SoloQuizSessionRepository extends JpaRepository<SoloQuizSession, Long> {

    List<SoloQuizSession> findByUserIdOrderByCompletedAtDesc(Long userId);
}
