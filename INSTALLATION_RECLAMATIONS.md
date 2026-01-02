# Installation du Système de Réclamations

## Étapes d'installation

### 1. Créer la table dans MySQL

Ouvrez MySQL et exécutez :

```bash
# Connectez-vous à MySQL
mysql -u root -p

# Sélectionnez la base de données
USE storia;

# Exécutez le script
SOURCE C:/xampp/htdocs/storia_project/sql/reclamations-table.sql;

# Ou copiez-collez directement :
CREATE TABLE IF NOT EXISTS reclamations (
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
    FOREIGN KEY (responded_by_id) REFERENCES users(id) ON DELETE SET NULL,

    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 2. Vérifier que la table est créée

```sql
DESCRIBE reclamations;
```

Vous devriez voir la structure de la table.

### 3. Redémarrer l'application Spring Boot

Si l'application est déjà en cours d'exécution, redémarrez-la :

```bash
# Arrêtez l'application (Ctrl+C)
# Puis relancez
./mvnw spring-boot:run
```

Ou sous Windows :

```bash
mvnw.cmd spring-boot:run
```

### 4. Tester le système

#### A. Test côté utilisateur

1. Connectez-vous en tant qu'utilisateur normal
2. Accédez à `http://localhost:8086/reclamations.html`
3. Cliquez sur "Nouvelle Réclamation"
4. Remplissez le formulaire :
   - Sujet : "Test de réclamation"
   - Description : "Ceci est un test du système de réclamation"
5. Cliquez sur "Envoyer"
6. Vérifiez que la réclamation apparaît dans la liste avec le statut "En Attente"

#### B. Test côté administrateur

1. Connectez-vous en tant qu'administrateur
2. Accédez à `http://localhost:8086/admin.html`
3. Cliquez sur l'onglet "📮 Réclamations"
4. Vérifiez que vous voyez la réclamation créée précédemment
5. Vérifiez les statistiques (Total, En attente, etc.)
6. Cliquez sur "Répondre" pour la réclamation
7. Remplissez la réponse :
   - Réponse : "Merci pour votre retour, nous traitons votre demande"
   - Statut : "En cours"
8. Cliquez sur "Envoyer la réponse"
9. Vérifiez que le statut a changé

#### C. Vérifier la réponse côté utilisateur

1. Revenez à la page utilisateur `/reclamations.html`
2. Actualisez la page
3. Vérifiez que vous voyez la réponse de l'administrateur
4. Vérifiez que le statut est passé à "En cours"

### 5. Test du filtrage (Admin)

1. Dans l'onglet Réclamations de l'admin
2. Utilisez le menu déroulant pour filtrer :
   - Tous les statuts
   - En attente
   - En cours
   - Résolu
   - Fermé
3. Vérifiez que le filtrage fonctionne

### 6. Test des statistiques

1. Créez plusieurs réclamations avec différents statuts
2. Vérifiez que les statistiques en haut se mettent à jour automatiquement

## Vérification des endpoints API

### Utilisateur

Tester avec curl ou Postman :

```bash
# Créer une réclamation (remplacez YOUR_JWT_TOKEN)
curl -X POST http://localhost:8086/api/user/reclamations \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "subject": "Problème avec le quiz",
    "description": "Le quiz ne se charge pas correctement"
  }'

# Voir ses réclamations
curl -X GET http://localhost:8086/api/user/reclamations \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Administrateur

```bash
# Voir toutes les réclamations (ADMIN uniquement)
curl -X GET http://localhost:8086/api/admin/reclamations \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN"

# Statistiques
curl -X GET http://localhost:8086/api/admin/reclamations/stats \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN"

# Répondre à une réclamation
curl -X PUT http://localhost:8086/api/admin/reclamations/1/respond \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "response": "Nous avons résolu votre problème",
    "status": "RESOLU"
  }'
```

## Dépannage

### Erreur : Table reclamations doesn't exist

- Vérifiez que vous avez bien créé la table dans la base de données `storia`
- Exécutez le script SQL fourni

### Erreur 403 Forbidden

- Vérifiez que vous êtes bien connecté
- Vérifiez que votre token JWT est valide
- Pour les endpoints admin, vérifiez que vous avez le rôle ADMIN

### Les réclamations ne s'affichent pas

- Vérifiez la console du navigateur (F12) pour les erreurs
- Vérifiez que l'API retourne bien des données
- Vérifiez que le serveur Spring Boot est bien démarré

### Erreur lors de la soumission

- Vérifiez que tous les champs sont remplis
- Vérifiez la longueur du sujet (max 200 caractères)
- Vérifiez les logs du serveur

## Structure des fichiers

```
storia_project/
├── sql/
│   └── reclamations-table.sql
├── src/main/java/org/storia/
│   ├── entities/
│   │   ├── Reclamation.java
│   │   └── EReclamationStatus.java
│   ├── repositories/
│   │   └── ReclamationRepository.java
│   ├── services/
│   │   └── ReclamationService.java
│   ├── controllers/
│   │   └── ReclamationController.java
│   └── dto/
│       ├── ReclamationRequest.java
│       ├── ReclamationResponse.java
│       └── AdminResponseRequest.java
└── src/main/resources/static/
    ├── reclamations.html
    ├── admin.html (modifié)
    └── js/
        ├── reclamations.js
        └── admin.js (modifié)
```

## Prochaines étapes

Une fois le système testé et fonctionnel, vous pouvez :

1. Ajouter des notifications par email pour les nouvelles réclamations
2. Ajouter un système de priorité (Basse, Moyenne, Haute, Urgente)
3. Ajouter des pièces jointes (images, fichiers)
4. Ajouter un historique complet des échanges
5. Ajouter des catégories de réclamations (Technique, Contenu, Autre)

## Support

Pour toute question, consultez :
- `RECLAMATIONS_README.md` - Documentation complète
- `API_DOCUMENTATION.md` - Documentation API
- Logs du serveur Spring Boot
