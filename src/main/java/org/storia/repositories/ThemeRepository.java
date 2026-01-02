package org.storia.repositories;

import org.storia.entities.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Theme
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("SELECT t FROM Theme t LEFT JOIN FETCH t.questions WHERE t.isActive = true")
    List<Theme> findByIsActiveTrueWithQuestions();

    List<Theme> findByIsActiveTrue();
}
