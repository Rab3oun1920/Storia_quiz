package org.storia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * STORIA - Plateforme de Quiz Interactive
 *
 * Application Spring Boot principale pour la plateforme Storia.
 *
 * Fonctionnalités:
 * - Authentification JWT
 * - Mode Solo: 10 questions, 10 secondes par question
 * - Mode Groupe: 2-4 groupes, rotation des équipes
 * - Classements globaux et par thème
 * - Panel d'administration
 *
 * @author Storia Team
 * @version 1.0.0
 * @since 2024-12
 */
@SpringBootApplication
public class StoriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoriaApplication.class, args);

        System.out.println("\n" +
                "================================================================================\n" +
                "                    STORIA - PLATEFORME DE QUIZ DÉMARRÉE                      \n" +
                "================================================================================\n" +
                "  🎯 Application: Storia Quiz Platform\n" +
                "  🌐 URL: http://localhost:8086\n" +
                "  📚 API Docs: http://localhost:8086/api\n" +
                "================================================================================\n" +
                "  ✅ Prêt à recevoir des requêtes!\n" +
                "================================================================================\n");
    }

}
