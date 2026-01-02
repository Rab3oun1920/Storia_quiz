package org.storia.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.storia.dto.*;
import org.storia.entities.ERole;
import org.storia.entities.RefreshToken;
import org.storia.entities.Role;
import org.storia.entities.User;
import org.storia.repositories.RoleRepository;
import org.storia.repositories.UserRepository;
import org.storia.security.jwt.JwtUtils;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.RefreshTokenService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller d'authentification
 * Gère l'inscription, la connexion et le rafraîchissement des tokens
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    /**
     * Endpoint de connexion
     * @param loginRequest Contient username/email et password
     * @return JWT et RefreshToken
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // 1. Authentifier avec AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // 2. Set authentication dans SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Générer JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Récupérer UserDetails
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // 5. Créer RefreshToken
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        // 6. Récupérer l'utilisateur complet pour obtenir firstName, lastName, country
        User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // 7. Extraire les rôles
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        // 8. Retourner JwtResponse
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
            userDetails.getUsername(), userDetails.getEmail(),
            user.getFirstName(), user.getLastName(), user.getCountry(), roles));
    }

    /**
     * Endpoint d'inscription
     * @param signUpRequest Contient username, email, password et informations profil
     * @return Message de succès ou d'erreur
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // 1. Vérifier si username existe
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // 2. Vérifier si email existe
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // 3. Créer nouveau User
        User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setCountry(signUpRequest.getCountry());

        // 4. Assigner ROLE_USER par défaut
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        // 5. Sauvegarder
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Endpoint de rafraîchissement du token
     * @param request Contient le refreshToken
     * @return Nouveau JWT
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    /**
     * Endpoint de déconnexion
     * @return Message de succès
     */
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
