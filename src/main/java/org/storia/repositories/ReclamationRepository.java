package org.storia.repositories;

import org.storia.entities.EReclamationStatus;
import org.storia.entities.Reclamation;
import org.storia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Reclamation
 */
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    /**
     * Trouver toutes les réclamations d'un utilisateur
     */
    List<Reclamation> findByUserOrderByCreatedAtDesc(User user);

    /**
     * Trouver toutes les réclamations par statut
     */
    List<Reclamation> findByStatusOrderByCreatedAtDesc(EReclamationStatus status);

    /**
     * Trouver toutes les réclamations triées par date
     */
    List<Reclamation> findAllByOrderByCreatedAtDesc();

    /**
     * Compter les réclamations par statut
     */
    Long countByStatus(EReclamationStatus status);
}
