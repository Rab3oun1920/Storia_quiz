package org.storia.repositories;

import org.storia.entities.UserGlobalScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité UserGlobalScore
 */
@Repository
public interface UserGlobalScoreRepository extends JpaRepository<UserGlobalScore, Long> {

    Optional<UserGlobalScore> findByUserId(Long userId);

    List<UserGlobalScore> findTop50ByOrderByTotalScoreDesc();

    Page<UserGlobalScore> findAllByOrderByTotalScoreDesc(Pageable pageable);
}
