# Système de Propositions - STORIA

## Vue d'ensemble

Le système de propositions permet aux utilisateurs de suggérer de nouveaux thèmes ou questions pour enrichir la plateforme. Les administrateurs peuvent examiner, approuver ou rejeter ces propositions.

## Fonctionnalités

### Pour les Utilisateurs

1. **Accès** : `/propositions.html`

2. **Proposer un nouveau thème** :
   - Choisir "Proposer un Thème"
   - Remplir le titre (max 100 caractères)
   - Décrire le thème
   - Ajouter une icône (optionnel)
   - Soumettre

3. **Proposer une nouvelle question** :
   - Choisir "Proposer une Question"
   - Sélectionner un thème existant
   - Poser la question
   - Choisir la difficulté (Facile, Moyenne, Difficile)
   - Fournir la bonne réponse
   - Fournir 3 mauvaises réponses
   - Soumettre

4. **Consulter ses propositions** :
   - Voir toutes ses propositions avec leur statut
   - Lire les commentaires des administrateurs
   - Suivre l'évolution (En attente → Approuvée/Rejetée)

### Pour les Administrateurs

1. **Accès** : Onglet "💡 Propositions" dans `/admin.html`

2. **Tableau de bord** :
   - Statistiques en temps réel (Total, En attente, Approuvées, Rejetées)
   - Statistiques par type (Thèmes, Questions)
   - Filtrage par type et statut
   - Liste complète des propositions

3. **Examiner une proposition** :
   - Cliquer sur "Examiner" ou "Voir"
   - Lire les détails complets
   - Pour les thèmes : voir titre, description, icône
   - Pour les questions : voir question, thème, difficulté, réponses
   - Décider : Approuver ou Rejeter
   - Ajouter un commentaire (optionnel)
   - Valider

4. **Approbation automatique** :
   - Thème approuvé → Ajouté automatiquement à la base
   - Question approuvée → Ajoutée automatiquement avec ses 4 réponses

## Statuts des propositions

- **⏳ EN_ATTENTE** : Nouvelle proposition non examinée
- **✅ APPROUVE** : Proposition acceptée et ajoutée au système
- **❌ REJETE** : Proposition refusée

## API Endpoints

### Utilisateur (ROLE_USER)

- `POST /api/user/propositions/theme` - Proposer un thème
- `POST /api/user/propositions/question` - Proposer une question
- `GET /api/user/propositions` - Voir ses propositions

### Administrateur (ROLE_ADMIN)

- `GET /api/admin/propositions` - Voir toutes les propositions
- `GET /api/admin/propositions?type=THEME` - Filtrer par type
- `GET /api/admin/propositions?status=EN_ATTENTE` - Filtrer par statut
- `PUT /api/admin/propositions/{id}/review` - Examiner une proposition
- `GET /api/admin/propositions/stats` - Statistiques

## Structure de la base de données

```sql
CREATE TABLE propositions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,  -- THEME ou QUESTION

    -- Pour les thèmes
    theme_title VARCHAR(100),
    theme_description TEXT,
    theme_icon VARCHAR(50),

    -- Pour les questions
    question_text TEXT,
    theme_id BIGINT,
    difficulty VARCHAR(20),  -- EASY, MEDIUM, HARD
    correct_answer VARCHAR(500),
    wrong_answer_1 VARCHAR(500),
    wrong_answer_2 VARCHAR(500),
    wrong_answer_3 VARCHAR(500),

    -- Gestion
    status VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
    admin_comment TEXT,
    reviewed_by_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE SET NULL,
    FOREIGN KEY (reviewed_by_id) REFERENCES users(id) ON DELETE SET NULL
);
```

## Installation

1. **Créer la table dans MySQL** :
   ```bash
   mysql -u root storia < sql/propositions-table.sql
   ```

2. **Redémarrer l'application Spring Boot** :
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Accéder aux pages** :
   - Utilisateur : `http://localhost:8086/propositions.html`
   - Admin : `http://localhost:8086/admin.html` → Onglet Propositions

## Fichiers créés/modifiés

### Backend (Java)

- ✅ `entities/Proposition.java` - Entité JPA
- ✅ `entities/EPropositionType.java` - Enum THEME/QUESTION
- ✅ `entities/EPropositionStatus.java` - Enum EN_ATTENTE/APPROUVE/REJETE
- ✅ `repositories/PropositionRepository.java` - Repository
- ✅ `services/PropositionService.java` - Service métier
- ✅ `controllers/PropositionController.java` - REST Controller
- ✅ `dto/PropositionThemeRequest.java` - DTO thème
- ✅ `dto/PropositionQuestionRequest.java` - DTO question
- ✅ `dto/PropositionResponse.java` - DTO réponse
- ✅ `dto/AdminReviewRequest.java` - DTO examen admin

### Frontend

- ✅ `static/propositions.html` - Page utilisateur
- ✅ `static/js/propositions.js` - Logique utilisateur
- ✅ `static/admin.html` - Onglet ajouté + modale
- ✅ `static/js/admin.js` - Fonctions admin ajoutées
- ✅ `static/dashboard.html` - Lien navigation

### SQL

- ✅ `sql/propositions-table.sql` - Script de création

## Workflow Complet

1. **Utilisateur** soumet une proposition → Statut : EN_ATTENTE
2. **Admin** reçoit la notification (statistiques mises à jour)
3. **Admin** examine la proposition
4. **Admin** approuve ou rejette :
   - **Si approuvé** :
     - Thème → Créé dans la table `themes` avec `is_active = true`
     - Question → Créée dans `questions` avec ses 4 réponses dans `answers`
   - **Si rejeté** : Statut changé, commentaire ajouté
5. **Utilisateur** voit la décision et le commentaire admin

## Sécurité

- Authentification requise (JWT)
- Utilisateurs : peuvent voir uniquement leurs propositions
- Administrateurs : peuvent voir et examiner toutes les propositions
- Validation des données (Jakarta Validation)
- Protection contre les injections (JPA)
- Cascade sur suppression d'utilisateur

## Règles de validation

### Thème
- Titre : obligatoire, max 100 caractères
- Description : obligatoire
- Icône : optionnel, max 50 caractères

### Question
- Question : obligatoire
- Thème : obligatoire (sélection parmi thèmes existants)
- Difficulté : obligatoire (EASY/MEDIUM/HARD)
- Bonne réponse : obligatoire, max 500 caractères
- 3 mauvaises réponses : obligatoires, max 500 caractères chacune

## Exemples d'utilisation

### Proposer un thème
```bash
POST /api/user/propositions/theme
{
  "themeTitle": "Cinéma",
  "themeDescription": "Questions sur l'histoire du cinéma, acteurs, réalisateurs et films célèbres",
  "themeIcon": "🎬"
}
```

### Proposer une question
```bash
POST /api/user/propositions/question
{
  "themeId": 1,
  "questionText": "Qui a réalisé le film Inception ?",
  "difficulty": "MEDIUM",
  "correctAnswer": "Christopher Nolan",
  "wrongAnswer1": "Steven Spielberg",
  "wrongAnswer2": "Quentin Tarantino",
  "wrongAnswer3": "Martin Scorsese"
}
```

### Examiner une proposition
```bash
PUT /api/admin/propositions/1/review
{
  "status": "APPROUVE",
  "comment": "Excellente proposition ! Nous l'avons ajoutée au système."
}
```

## Statistiques disponibles

Les admins peuvent voir :
- Nombre total de propositions
- Propositions en attente
- Propositions approuvées
- Propositions rejetées
- Propositions de thèmes
- Propositions de questions

## Notes importantes

1. Les propositions approuvées sont **automatiquement ajoutées** au système
2. Un thème approuvé devient immédiatement disponible pour les quiz
3. Une question approuvée est ajoutée avec le bon calcul de points selon la difficulté
4. Les utilisateurs sont **notifiés** via la page de leurs propositions
5. Les commentaires admin sont **visibles** par les utilisateurs

## Améliora tions futures possibles

- Notifications par email lors de l'examen
- Système de vote pour les propositions populaires
- Possibilité d'éditer une proposition rejetée
- Import en masse de propositions
- Statistiques détaillées par utilisateur
- Badges pour les contributeurs actifs

## Support

Pour toute question, consultez :
- `PROPOSITIONS_README.md` - Documentation complète (ce fichier)
- `API_DOCUMENTATION.md` - Documentation API générale
- `RECLAMATIONS_README.md` - Système de réclamations

---

**Date de création :** 2025-12-31
**Version :** 1.0
**Statut :** ✅ Production Ready
