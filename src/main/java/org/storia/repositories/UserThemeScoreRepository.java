package org.storia.repositories;

import org.storia.entities.UserThemeScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité UserThemeScore
 */
@Repository
public interface UserThemeScoreRepository extends JpaRepository<UserThemeScore, Long> {

    Optional<UserThemeScore> findByUserIdAndThemeId(Long userId, Long themeId);

    List<UserThemeScore> findTop50ByThemeIdOrderByTotalScoreDesc(Long themeId);

    Page<UserThemeScore> findByThemeIdOrderByTotalScoreDesc(Long themeId, Pageable pageable);

    List<UserThemeScore> findByUserId(Long userId);
}
