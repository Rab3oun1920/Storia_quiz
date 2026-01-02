package org.storia.services;

import org.storia.entities.RefreshToken;
import org.storia.entities.User;
import org.storia.repositories.RefreshTokenRepository;
import org.storia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service de gestion des RefreshTokens
 * Gère la création, vérification et suppression des tokens de rafraîchissement
 */
@Service
public class RefreshTokenService {

    @Value("${storia.jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Recherche un RefreshToken par sa valeur
     * @param token La valeur du token
     * @return Optional contenant le RefreshToken si trouvé
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Crée un nouveau RefreshToken pour un utilisateur
     * @param userId L'identifiant de l'utilisateur
     * @return Le RefreshToken créé et sauvegardé
     */
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Vérifie si un RefreshToken est expiré
     * @param token Le RefreshToken à vérifier
     * @return Le token si valide
     * @throws RuntimeException si le token est expiré
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    /**
     * Supprime tous les RefreshTokens d'un utilisateur
     * @param userId L'identifiant de l'utilisateur
     * @return Le nombre de tokens supprimés
     */
    @Transactional
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return refreshTokenRepository.deleteByUser(user);
    }
}
