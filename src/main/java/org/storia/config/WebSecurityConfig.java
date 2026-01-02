package org.storia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.storia.security.jwt.AuthEntryPointJwt;
import org.storia.security.jwt.AuthTokenFilter;
import org.storia.security.services.UserDetailsServiceImpl;

import java.util.Arrays;

/**
 * Configuration de la sécurité Spring Security avec JWT
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * Bean du filtre d'authentification JWT
     * @return AuthTokenFilter
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Provider d'authentification DAO
     * @return DaoAuthenticationProvider configuré
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * AuthenticationManager pour gérer l'authentification
     * @param authConfig Configuration d'authentification
     * @return AuthenticationManager
     * @throws Exception En cas d'erreur
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Encodeur de mot de passe BCrypt
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Désactiver complètement la sécurité pour les ressources statiques
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/", "/index.html", "/login.html", "/register.html")
                .requestMatchers("/dashboard.html", "/quiz-solo.html", "/play-solo.html", "/result-solo.html")
                .requestMatchers("/quiz-groupe.html", "/create-group.html", "/play-groupe.html", "/result-groupe.html")
                .requestMatchers("/rankings.html", "/profile.html", "/admin.html")
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**")
                .requestMatchers("/favicon.ico", "/*.png", "/*.jpg", "/*.ico");
    }

    /**
     * Chaîne de filtres de sécurité
     * @param http HttpSecurity
     * @return SecurityFilterChain configurée
     * @throws Exception En cas d'erreur
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("http://localhost:8086", "http://localhost:3000"));
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                auth
                    // Public API endpoints
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/test/**").permitAll()
                    // Protected API endpoints
                    .requestMatchers("/api/**").authenticated()
                    // Everything else is permitted (static resources handled by webSecurityCustomizer)
                    .anyRequest().permitAll()
            );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
