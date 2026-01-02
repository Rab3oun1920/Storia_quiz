# STORIA - Documentation API

## Configuration de base

**Base URL:** `http://localhost:8086/api`

**Port du serveur:** 8086

---

## Table des matières
1. [Authentification](#authentification)
2. [Thèmes](#thèmes)
3. [Quiz Solo](#quiz-solo)
4. [Utilisateur](#utilisateur)
5. [Classements](#classements)
6. [Administration](#administration)
7. [Configuration Postman](#configuration-postman)

---

## Authentification

### 1. Inscription (Sign Up)
**POST** `/auth/signup`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "country": "France"
}
```

**Réponse:**
```json
{
  "message": "User registered successfully!"
}
```

---

### 2. Connexion (Sign In)
**POST** `/auth/signin`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "username": "testuser",
  "password": "password123"
}
```

**Réponse:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "id": 1,
  "username": "testuser",
  "email": "test@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "country": "France",
  "roles": ["ROLE_USER"]
}
```

**IMPORTANT:** Copiez le token JWT pour les requêtes suivantes!

---

### 3. Rafraîchir le token
**POST** `/auth/refreshtoken`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "refreshToken": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

**Réponse:**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

---

### 4. Déconnexion
**POST** `/auth/signout`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "message": "Log out successful!"
}
```

---

## Thèmes

### 1. Récupérer tous les thèmes actifs
**GET** `/themes`

**Headers:**
```
Content-Type: application/json
```

**Réponse:**
```json
[
  {
    "id": 1,
    "name": "Histoire",
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "name": "Géographie",
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00"
  }
]
```

---

### 2. Récupérer un thème par ID
**GET** `/themes/{id}`

**Exemple:** `/themes/1`

**Headers:**
```
Content-Type: application/json
```

**Réponse:**
```json
{
  "id": 1,
  "name": "Histoire",
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00"
}
```

---

## Quiz Solo

### 1. Récupérer les thèmes disponibles
**GET** `/solo/themes`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
[
  {
    "id": 1,
    "name": "Histoire",
    "isActive": true
  }
]
```

---

### 2. Démarrer un quiz
**GET** `/solo/{themeId}/start`

**Exemple:** `/solo/1/start`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "sessionId": 123,
  "theme": {
    "id": 1,
    "name": "Histoire"
  },
  "questions": [
    {
      "id": 1,
      "questionText": "Quelle est la capitale de la France?",
      "difficulty": "FACILE",
      "points": 10,
      "answers": [
        {
          "id": 1,
          "answerText": "Paris",
          "answerOrder": 1
        },
        {
          "id": 2,
          "answerText": "Lyon",
          "answerOrder": 2
        },
        {
          "id": 3,
          "answerText": "Marseille",
          "answerOrder": 3
        },
        {
          "id": 4,
          "answerText": "Bordeaux",
          "answerOrder": 4
        }
      ]
    }
  ]
}
```

---

### 3. Vérifier une réponse
**POST** `/solo/check-answer`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "answerId": 1
}
```

**Réponse:**
```json
{
  "correct": true,
  "points": 10,
  "correctAnswer": "Paris"
}
```

---

### 4. Finaliser un quiz
**POST** `/solo/complete`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "themeId": 1,
  "score": 80,
  "correctAnswers": 8,
  "totalQuestions": 10,
  "timeSpent": 120
}
```

**Réponse:**
```json
{
  "message": "Quiz completed successfully",
  "totalScore": 80,
  "correctAnswers": 8,
  "rank": 15,
  "globalScore": 450
}
```

---

## Utilisateur

### 1. Récupérer le profil
**GET** `/user/profile`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "id": 1,
  "username": "testuser",
  "email": "test@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "country": "France",
  "roles": [
    {
      "id": 1,
      "name": "ROLE_USER"
    }
  ],
  "createdAt": "2024-01-01T10:00:00"
}
```

---

### 2. Mettre à jour le profil
**PUT** `/user/profile`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "country": "Belgique",
  "email": "newemail@example.com"
}
```

**Réponse:**
```json
{
  "id": 1,
  "username": "testuser",
  "email": "newemail@example.com",
  "firstName": "Jane",
  "lastName": "Smith",
  "country": "Belgique"
}
```

---

### 3. Récupérer les statistiques utilisateur
**GET** `/user/stats`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "totalQuizzes": 25,
  "totalScore": 1250,
  "averageScore": 50,
  "bestScore": 100,
  "rank": 15
}
```

---

### 4. Récupérer les statistiques par thème
**GET** `/user/theme-stats/{themeId}`

**Exemple:** `/user/theme-stats/1`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "themeId": 1,
  "themeName": "Histoire",
  "quizCount": 5,
  "totalScore": 400,
  "averageScore": 80,
  "bestScore": 100
}
```

---

### 5. Récupérer les quiz récents
**GET** `/user/recent-quizzes?limit=10`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
[
  {
    "id": 123,
    "theme": "Histoire",
    "score": 80,
    "completedAt": "2024-01-15T14:30:00"
  }
]
```

---

## Classements

### 1. Classement global
**GET** `/rankings/global?page=0&size=50`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "content": [
    {
      "rank": 1,
      "username": "player1",
      "totalScore": 5000,
      "quizCount": 100
    },
    {
      "rank": 2,
      "username": "player2",
      "totalScore": 4500,
      "quizCount": 95
    }
  ],
  "totalElements": 150,
  "totalPages": 3,
  "currentPage": 0
}
```

---

### 2. Classement par thème
**GET** `/rankings/theme/{themeId}?page=0&size=50`

**Exemple:** `/rankings/theme/1?page=0&size=50`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "content": [
    {
      "rank": 1,
      "username": "player1",
      "score": 1200,
      "quizCount": 30
    }
  ],
  "totalElements": 50,
  "totalPages": 1,
  "currentPage": 0
}
```

---

### 3. Statistiques de l'utilisateur
**GET** `/rankings/user/stats`

**Headers:**
```
Authorization: Bearer {votre-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "totalScore": 2500,
  "rank": 15,
  "totalQuizzes": 50,
  "themeStats": [
    {
      "themeName": "Histoire",
      "score": 800,
      "rank": 10
    }
  ]
}
```

---

## Administration

**IMPORTANT:** Toutes les requêtes admin nécessitent un token JWT avec le rôle ROLE_ADMIN

### 1. Statistiques globales
**GET** `/admin/statistics`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "totalUsers": 1000,
  "totalQuizzesSolo": 5000,
  "totalQuizzesGroup": 500,
  "totalQuestions": 500,
  "activeThemes": 10,
  "averageScoreSolo": 65.5,
  "mostPlayedTheme": "Histoire",
  "registrationsToday": 15,
  "quizzesToday": 120
}
```

---

### 2. Gestion des Thèmes

#### Récupérer tous les thèmes (avec nombre de questions)
**GET** `/admin/themes`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
[
  {
    "id": 1,
    "name": "Histoire",
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00",
    "questionCount": 45
  }
]
```

---

#### Créer un thème
**POST** `/admin/themes`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Sciences"
}
```

**Réponse:**
```json
{
  "id": 3,
  "name": "Sciences",
  "isActive": true,
  "createdAt": "2024-01-15T10:00:00"
}
```

---

#### Modifier un thème
**PUT** `/admin/themes/{id}`

**Exemple:** `/admin/themes/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Histoire Moderne"
}
```

**Réponse:**
```json
{
  "id": 1,
  "name": "Histoire Moderne",
  "isActive": true
}
```

---

#### Supprimer un thème
**DELETE** `/admin/themes/{id}`

**Exemple:** `/admin/themes/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "message": "Theme deleted successfully"
}
```

---

### 3. Gestion des Questions

#### Récupérer toutes les questions (avec pagination et filtres)
**GET** `/admin/questions?page=0&size=50&themeId=1&difficulty=FACILE`

**Paramètres optionnels:**
- `page`: Numéro de page (défaut: 0)
- `size`: Taille de la page (défaut: 50)
- `themeId`: Filtrer par thème
- `difficulty`: Filtrer par difficulté (FACILE, MOYEN, DIFFICILE)

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "content": [
    {
      "id": 1,
      "questionText": "Quelle est la capitale de la France?",
      "difficulty": "FACILE",
      "points": 10,
      "theme": {
        "id": 1,
        "name": "Histoire"
      }
    }
  ],
  "totalElements": 100,
  "totalPages": 2,
  "currentPage": 0
}
```

---

#### Récupérer une question par ID
**GET** `/admin/questions/{id}`

**Exemple:** `/admin/questions/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "question": {
    "id": 1,
    "questionText": "Quelle est la capitale de la France?",
    "difficulty": "FACILE",
    "points": 10
  },
  "answers": [
    {
      "id": 1,
      "answerText": "Paris",
      "isCorrect": true,
      "answerOrder": 1
    },
    {
      "id": 2,
      "answerText": "Lyon",
      "isCorrect": false,
      "answerOrder": 2
    },
    {
      "id": 3,
      "answerText": "Marseille",
      "isCorrect": false,
      "answerOrder": 3
    },
    {
      "id": 4,
      "answerText": "Bordeaux",
      "isCorrect": false,
      "answerOrder": 4
    }
  ]
}
```

---

#### Créer une question
**POST** `/admin/questions`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "themeId": 1,
  "questionText": "Qui a découvert l'Amérique?",
  "difficulty": "FACILE",
  "answers": [
    {
      "answerText": "Christophe Colomb",
      "isCorrect": true
    },
    {
      "answerText": "Vasco de Gama",
      "isCorrect": false
    },
    {
      "answerText": "Marco Polo",
      "isCorrect": false
    },
    {
      "answerText": "Magellan",
      "isCorrect": false
    }
  ]
}
```

**Réponse:**
```json
{
  "id": 50,
  "questionText": "Qui a découvert l'Amérique?",
  "difficulty": "FACILE",
  "points": 10,
  "theme": {
    "id": 1,
    "name": "Histoire"
  }
}
```

---

#### Modifier une question
**PUT** `/admin/questions/{id}`

**Exemple:** `/admin/questions/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "questionText": "Quelle est la capitale de la France aujourd'hui?",
  "difficulty": "MOYEN",
  "themeId": 1,
  "answers": [
    {
      "answerText": "Paris",
      "isCorrect": true
    },
    {
      "answerText": "Lyon",
      "isCorrect": false
    },
    {
      "answerText": "Marseille",
      "isCorrect": false
    },
    {
      "answerText": "Bordeaux",
      "isCorrect": false
    }
  ]
}
```

**Réponse:**
```json
{
  "id": 1,
  "questionText": "Quelle est la capitale de la France aujourd'hui?",
  "difficulty": "MOYEN",
  "points": 20
}
```

---

#### Supprimer une question
**DELETE** `/admin/questions/{id}`

**Exemple:** `/admin/questions/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "message": "Question deleted successfully"
}
```

---

#### Importer des questions via CSV
**POST** `/admin/questions/import`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: multipart/form-data
```

**Body:**
- Form-data key: `file`
- Value: Fichier CSV

**Format CSV:**
```
ThemeId,QuestionText,Difficulty,Answer1,Answer2,Answer3,Answer4,CorrectAnswerIndex
1,"Question ici?",FACILE,"Réponse 1","Réponse 2","Réponse 3","Réponse 4",1
```

**Réponse:**
```json
{
  "imported": 25,
  "errors": 2,
  "message": "Imported 25 questions successfully with 2 errors"
}
```

---

### 4. Gestion des Utilisateurs

#### Récupérer tous les utilisateurs (avec scores et quiz)
**GET** `/admin/users?page=0&size=50`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "testuser",
      "email": "test@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "country": "France",
      "roles": [
        {
          "id": 1,
          "name": "ROLE_USER"
        }
      ],
      "totalScore": 1250,
      "quizCount": 25,
      "createdAt": "2024-01-01T10:00:00"
    }
  ],
  "totalElements": 1000,
  "totalPages": 20,
  "currentPage": 0
}
```

---

#### Récupérer les détails d'un utilisateur
**GET** `/admin/users/{id}`

**Exemple:** `/admin/users/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "country": "France"
  },
  "globalScore": {
    "totalScore": 1250
  },
  "recentSessions": [
    {
      "id": 123,
      "theme": "Histoire",
      "score": 80,
      "completedAt": "2024-01-15T14:30:00"
    }
  ],
  "totalQuizzes": 25,
  "averageSuccessRate": 75.5
}
```

---

#### Créer un utilisateur
**POST** `/admin/users`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123",
  "firstName": "Alice",
  "lastName": "Wonder",
  "country": "Canada",
  "role": "ROLE_USER"
}
```

**Réponse:**
```json
{
  "message": "User created successfully!"
}
```

---

#### Modifier un utilisateur
**PUT** `/admin/users/{id}`

**Exemple:** `/admin/users/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Body:**
```json
{
  "username": "updateduser",
  "email": "updated@example.com",
  "password": "newpassword123",
  "firstName": "Jane",
  "lastName": "Doe",
  "country": "Belgique",
  "role": "ROLE_ADMIN"
}
```

**Note:** Le champ `password` est optionnel. Si non fourni, le mot de passe actuel est conservé.

**Réponse:**
```json
{
  "message": "User updated successfully!"
}
```

---

#### Supprimer un utilisateur
**DELETE** `/admin/users/{id}`

**Exemple:** `/admin/users/1`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Réponse:**
```json
{
  "message": "User deleted successfully"
}
```

---

#### Réparer les rôles utilisateurs
**POST** `/admin/fix-user-roles`

**Headers:**
```
Authorization: Bearer {admin-token-jwt}
Content-Type: application/json
```

**Description:** Ajoute automatiquement ROLE_USER à tous les utilisateurs qui ne l'ont pas.

**Réponse:**
```json
{
  "totalUsers": 1000,
  "fixedUsers": 15,
  "message": "Fixed 15 users without ROLE_USER"
}
```

---

## Configuration Postman

### Étape 1: Créer une nouvelle Collection
1. Ouvrez Postman
2. Cliquez sur "New" > "Collection"
3. Nommez-la "STORIA API"

---

### Étape 2: Configurer les Variables d'environnement
1. Cliquez sur "Environments" dans la barre latérale
2. Cliquez sur "+" pour créer un nouvel environnement
3. Nommez-le "STORIA Local"
4. Ajoutez ces variables:

| Variable | Initial Value | Current Value |
|----------|--------------|---------------|
| base_url | http://localhost:8086/api | http://localhost:8086/api |
| token | | |
| refresh_token | | |

---

### Étape 3: Tester l'inscription et la connexion

#### 1. Créer une requête d'inscription:
- Méthode: **POST**
- URL: `{{base_url}}/auth/signup`
- Headers:
  - `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "username": "postmantest",
  "email": "postman@test.com",
  "password": "test123",
  "firstName": "Postman",
  "lastName": "Test",
  "country": "France"
}
```
- Cliquez sur **Send**

---

#### 2. Créer une requête de connexion:
- Méthode: **POST**
- URL: `{{base_url}}/auth/signin`
- Headers:
  - `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "username": "postmantest",
  "password": "test123"
}
```
- Dans l'onglet **Tests**, ajoutez ce script pour sauvegarder automatiquement le token:
```javascript
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.token);
pm.environment.set("refresh_token", jsonData.refreshToken);
```
- Cliquez sur **Send**
- Le token est maintenant sauvegardé dans vos variables d'environnement!

---

### Étape 4: Utiliser le token pour les requêtes authentifiées

Pour toutes les requêtes qui nécessitent une authentification:

1. Dans l'onglet **Authorization**:
   - Type: **Bearer Token**
   - Token: `{{token}}`

OU

2. Dans l'onglet **Headers**, ajoutez:
   - Key: `Authorization`
   - Value: `Bearer {{token}}`

---

### Étape 5: Tester une requête utilisateur

Exemple: Récupérer le profil

- Méthode: **GET**
- URL: `{{base_url}}/user/profile`
- Authorization: **Bearer Token** avec `{{token}}`
- Cliquez sur **Send**

---

### Étape 6: Créer un compte admin pour tester les endpoints admin

1. Créez d'abord un utilisateur normal via `/auth/signup`
2. Connectez-vous à votre base de données
3. Exécutez ce SQL pour ajouter le rôle admin:
```sql
-- Trouvez l'ID de votre utilisateur
SELECT * FROM users WHERE username = 'postmantest';

-- Supposons que l'ID est 5
INSERT INTO user_roles (user_id, role_id)
VALUES (5, 2);  -- 2 = ROLE_ADMIN
```
4. Reconnectez-vous avec cet utilisateur dans Postman
5. Le nouveau token aura les permissions admin

---

### Étape 7: Exemples de tests complets

#### Test 1: Flux complet d'un quiz
1. **POST** `/auth/signin` - Se connecter
2. **GET** `/solo/themes` - Voir les thèmes disponibles
3. **GET** `/solo/1/start` - Démarrer un quiz sur le thème 1
4. **POST** `/solo/check-answer` - Vérifier chaque réponse
5. **POST** `/solo/complete` - Finaliser le quiz
6. **GET** `/rankings/global` - Voir le classement

---

#### Test 2: Gestion admin d'un thème
1. **POST** `/auth/signin` - Se connecter en admin
2. **GET** `/admin/themes` - Voir tous les thèmes
3. **POST** `/admin/themes` - Créer un nouveau thème
4. **PUT** `/admin/themes/3` - Modifier le thème
5. **GET** `/admin/themes` - Vérifier les changements
6. **DELETE** `/admin/themes/3` - Supprimer le thème

---

### Conseils Postman

1. **Organisation:** Organisez vos requêtes en dossiers:
   - Authentification
   - Thèmes
   - Quiz
   - Utilisateur
   - Classements
   - Admin

2. **Tests automatiques:** Ajoutez des scripts de test pour vérifier les réponses:
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has token", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.token).to.exist;
});
```

3. **Pre-request Scripts:** Pour rafraîchir automatiquement le token si expiré:
```javascript
const tokenExpiry = pm.environment.get("token_expiry");
const currentTime = new Date().getTime();

if (currentTime > tokenExpiry) {
    // Rafraîchir le token
    pm.sendRequest({
        url: pm.environment.get("base_url") + "/auth/refreshtoken",
        method: 'POST',
        header: {
            'Content-Type': 'application/json',
        },
        body: {
            mode: 'raw',
            raw: JSON.stringify({
                refreshToken: pm.environment.get("refresh_token")
            })
        }
    }, function (err, res) {
        var jsonData = res.json();
        pm.environment.set("token", jsonData.accessToken);
    });
}
```

4. **Export/Import:** Exportez votre collection pour la partager avec votre équipe

---

## Codes d'erreur HTTP

| Code | Signification |
|------|---------------|
| 200 | Succès |
| 201 | Créé avec succès |
| 400 | Requête invalide (vérifiez le format JSON) |
| 401 | Non authentifié (token manquant ou invalide) |
| 403 | Accès refusé (permissions insuffisantes) |
| 404 | Ressource non trouvée |
| 500 | Erreur serveur |

---

## Support

Pour toute question ou problème:
- Vérifiez que le serveur tourne sur le port 8086
- Vérifiez que votre token JWT est valide et non expiré
- Consultez les logs du serveur pour plus de détails sur les erreurs
