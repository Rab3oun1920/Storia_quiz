# Système de Réclamations - STORIA

## Vue d'ensemble

Le système de réclamations permet aux utilisateurs de signaler des problèmes et de recevoir des réponses des administrateurs.

## Fonctionnalités

### Pour les Utilisateurs

1. **Accès** : `/reclamations.html`
2. **Créer une réclamation** :
   - Cliquer sur "Nouvelle Réclamation"
   - Remplir le sujet (max 200 caractères)
   - Décrire le problème en détail
   - Soumettre

3. **Consulter ses réclamations** :
   - Voir toutes ses réclamations avec leur statut
   - Lire les réponses des administrateurs
   - Suivre l'évolution (En attente → En cours → Résolu/Fermé)

### Pour les Administrateurs

1. **Accès** : Onglet "📮 Réclamations" dans `/admin.html`

2. **Tableau de bord** :
   - Statistiques en temps réel (Total, En attente, En cours, Résolues)
   - Filtrage par statut
   - Liste complète des réclamations

3. **Répondre à une réclamation** :
   - Cliquer sur "Répondre" ou "Voir/Modifier"
   - Lire les détails de la réclamation
   - Rédiger une réponse
   - Changer le statut (En cours, Résolu, Fermé)
   - Envoyer

## Statuts des réclamations

- **⏳ EN_ATTENTE** : Nouvelle réclamation non traitée
- **🔄 EN_COURS** : Réclamation en cours de traitement
- **✅ RESOLU** : Problème résolu avec réponse
- **🔒 FERME** : Réclamation fermée sans suite

## API Endpoints

### Utilisateur (ROLE_USER)

- `POST /api/user/reclamations` - Créer une réclamation
- `GET /api/user/reclamations` - Voir ses réclamations

### Administrateur (ROLE_ADMIN)

- `GET /api/admin/reclamations` - Voir toutes les réclamations
- `GET /api/admin/reclamations?status=EN_ATTENTE` - Filtrer par statut
- `GET /api/admin/reclamations/{id}` - Détails d'une réclamation
- `PUT /api/admin/reclamations/{id}/respond` - Répondre à une réclamation
- `PUT /api/admin/reclamations/{id}/status` - Changer le statut
- `GET /api/admin/reclamations/stats` - Statistiques

## Structure de la base de données

```sql
CREATE TABLE reclamations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
    admin_response TEXT,
    responded_by_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    responded_at TIMESTAMP NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (responded_by_id) REFERENCES users(id) ON DELETE SET NULL
);
```

## Installation

1. **Créer la table dans MySQL** :
   ```bash
   mysql -u root storia < sql/reclamations-table.sql
   ```

2. **Redémarrer l'application Spring Boot** :
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Accéder aux pages** :
   - Utilisateur : `http://localhost:8086/reclamations.html`
   - Admin : `http://localhost:8086/admin.html` → Onglet Réclamations

## Fichiers créés/modifiés

### Backend (Java)

- ✅ `entities/Reclamation.java` - Entité JPA
- ✅ `entities/EReclamationStatus.java` - Enum des statuts
- ✅ `repositories/ReclamationRepository.java` - Repository
- ✅ `services/ReclamationService.java` - Service métier
- ✅ `controllers/ReclamationController.java` - REST Controller
- ✅ `dto/ReclamationRequest.java` - DTO requête
- ✅ `dto/ReclamationResponse.java` - DTO réponse
- ✅ `dto/AdminResponseRequest.java` - DTO réponse admin

### Frontend

- ✅ `static/reclamations.html` - Page utilisateur
- ✅ `static/js/reclamations.js` - Logique utilisateur
- ✅ `static/admin.html` - Onglet ajouté + modale
- ✅ `static/js/admin.js` - Fonctions admin ajoutées

### SQL

- ✅ `sql/reclamations-table.sql` - Script de création

## Sécurité

- Authentification requise (JWT)
- Utilisateurs : peuvent voir uniquement leurs réclamations
- Administrateurs : peuvent voir et répondre à toutes les réclamations
- Protection CSRF désactivée (API stateless)
- Validation des données (Jakarta Validation)

## Notes importantes

1. Assurez-vous que la table `reclamations` est créée dans la base de données
2. Les utilisateurs doivent être connectés pour accéder aux réclamations
3. Seuls les ADMIN peuvent répondre aux réclamations
4. Les réponses sont visibles immédiatement par les utilisateurs
5. Les statuts peuvent être modifiés même après avoir répondu

## Support

Pour toute question ou problème, consultez la documentation API complète dans `API_DOCUMENTATION.md`.
