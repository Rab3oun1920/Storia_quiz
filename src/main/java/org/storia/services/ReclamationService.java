package org.storia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storia.dto.AdminResponseRequest;
import org.storia.dto.ReclamationRequest;
import org.storia.dto.ReclamationResponse;
import org.storia.entities.EReclamationStatus;
import org.storia.entities.Reclamation;
import org.storia.entities.User;
import org.storia.repositories.ReclamationRepository;
import org.storia.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Créer une nouvelle réclamation
     */
    public ReclamationResponse createReclamation(Long userId, ReclamationRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Reclamation reclamation = new Reclamation();
        reclamation.setUser(user);
        reclamation.setSubject(request.getSubject());
        reclamation.setDescription(request.getDescription());
        reclamation.setStatus(EReclamationStatus.EN_ATTENTE);

        Reclamation savedReclamation = reclamationRepository.save(reclamation);
        return convertToResponse(savedReclamation);
    }

    /**
     * Récupérer toutes les réclamations d'un utilisateur
     */
    public List<ReclamationResponse> getUserReclamations(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Reclamation> reclamations = reclamationRepository.findByUserOrderByCreatedAtDesc(user);
        return reclamations.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Récupérer toutes les réclamations (pour admin)
     */
    public List<ReclamationResponse> getAllReclamations() {
        List<Reclamation> reclamations = reclamationRepository.findAllByOrderByCreatedAtDesc();
        return reclamations.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Récupérer les réclamations par statut
     */
    public List<ReclamationResponse> getReclamationsByStatus(String status) {
        EReclamationStatus eStatus = EReclamationStatus.valueOf(status);
        List<Reclamation> reclamations = reclamationRepository.findByStatusOrderByCreatedAtDesc(eStatus);
        return reclamations.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Répondre à une réclamation (admin)
     */
    public ReclamationResponse respondToReclamation(Long reclamationId, Long adminId, AdminResponseRequest request) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
            .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));

        User admin = userRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        reclamation.setAdminResponse(request.getResponse());
        reclamation.setRespondedBy(admin);
        reclamation.setRespondedAt(LocalDateTime.now());

        // Définir le statut
        EReclamationStatus status = EReclamationStatus.valueOf(request.getStatus());
        reclamation.setStatus(status);

        Reclamation savedReclamation = reclamationRepository.save(reclamation);
        return convertToResponse(savedReclamation);
    }

    /**
     * Mettre à jour le statut d'une réclamation
     */
    public ReclamationResponse updateStatus(Long reclamationId, String status) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
            .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));

        EReclamationStatus eStatus = EReclamationStatus.valueOf(status);
        reclamation.setStatus(eStatus);

        Reclamation savedReclamation = reclamationRepository.save(reclamation);
        return convertToResponse(savedReclamation);
    }

    /**
     * Récupérer une réclamation par ID
     */
    public ReclamationResponse getReclamationById(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        return convertToResponse(reclamation);
    }

    /**
     * Obtenir les statistiques des réclamations
     */
    public java.util.Map<String, Object> getReclamationStats() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();

        stats.put("total", reclamationRepository.count());
        stats.put("enAttente", reclamationRepository.countByStatus(EReclamationStatus.EN_ATTENTE));
        stats.put("enCours", reclamationRepository.countByStatus(EReclamationStatus.EN_COURS));
        stats.put("resolu", reclamationRepository.countByStatus(EReclamationStatus.RESOLU));
        stats.put("ferme", reclamationRepository.countByStatus(EReclamationStatus.FERME));

        return stats;
    }

    /**
     * Convertir une Reclamation en ReclamationResponse
     */
    private ReclamationResponse convertToResponse(Reclamation reclamation) {
        ReclamationResponse response = new ReclamationResponse();
        response.setId(reclamation.getId());
        response.setUserId(reclamation.getUser().getId());
        response.setUsername(reclamation.getUser().getUsername());
        response.setSubject(reclamation.getSubject());
        response.setDescription(reclamation.getDescription());
        response.setStatus(reclamation.getStatus().name());
        response.setAdminResponse(reclamation.getAdminResponse());

        if (reclamation.getRespondedBy() != null) {
            response.setRespondedByUsername(reclamation.getRespondedBy().getUsername());
        }

        response.setCreatedAt(reclamation.getCreatedAt());
        response.setUpdatedAt(reclamation.getUpdatedAt());
        response.setRespondedAt(reclamation.getRespondedAt());

        return response;
    }
}
