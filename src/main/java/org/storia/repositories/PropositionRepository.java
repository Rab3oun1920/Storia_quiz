package org.storia.repositories;

import org.storia.entities.EPropositionStatus;
import org.storia.entities.EPropositionType;
import org.storia.entities.Proposition;
import org.storia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Proposition
 */
@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Long> {

    /**
     * Trouver toutes les propositions d'un utilisateur
     */
    List<Proposition> findByUserOrderByCreatedAtDesc(User user);

    /**
     * Trouver toutes les propositions par type
     */
    List<Proposition> findByTypeOrderByCreatedAtDesc(EPropositionType type);

    /**
     * Trouver toutes les propositions par statut
     */
    List<Proposition> findByStatusOrderByCreatedAtDesc(EPropositionStatus status);

    /**
     * Trouver toutes les propositions par type et statut
     */
    List<Proposition> findByTypeAndStatusOrderByCreatedAtDesc(EPropositionType type, EPropositionStatus status);

    /**
     * Trouver toutes les propositions triées par date
     */
    List<Proposition> findAllByOrderByCreatedAtDesc();

    /**
     * Compter les propositions par statut
     */
    Long countByStatus(EPropositionStatus status);

    /**
     * Compter les propositions par type
     */
    Long countByType(EPropositionType type);
}
