package org.storia.repositories;

import org.storia.entities.GroupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité GroupParticipant
 */
@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {

    List<GroupParticipant> findBySessionId(Long sessionId);

    List<GroupParticipant> findBySessionIdOrderByGroupOrderAsc(Long sessionId);
}
