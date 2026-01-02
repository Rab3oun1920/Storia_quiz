# Base de données Storia - Documentation SQL

## Structure des fichiers

### Fichiers principaux

1. **storia-structure.sql** - Crée la structure complète de la base de données (tables, contraintes, index)
2. **storia-data.sql** - Données initiales (rôles, utilisateurs, thèmes, questions Historia)
3. **storia-complete.sql** - Fichier maître qui source tous les autres fichiers

### Fichiers de questions et réponses par thème

#### Thème 1: Historia (Questions 1-50)
- **storia-answers-historia.sql** - 200 réponses (4 par question)

#### Thème 2: Géographie (Questions 51-100)
- **storia-questions-geo.sql** - 50 questions (17 EASY, 17 MEDIUM, 16 HARD)
- **storia-answers-geo.sql** - 200 réponses (4 par question)

#### Thème 3: Sport (Questions 101-150)
- **storia-questions-sport.sql** - 50 questions (17 EASY, 17 MEDIUM, 16 HARD)
- **storia-answers-sport.sql** - 200 réponses (4 par question)

#### Thème 4: Scientifique (Questions 151-200)
- **storia-questions-science.sql** - 50 questions (17 EASY, 17 MEDIUM, 16 HARD)
- **storia-answers-science.sql** - 200 réponses (4 par question)

#### Thème 5: Politique Tunisie (Questions 201-250)
- **storia-questions-politique.sql** - 50 questions (17 EASY, 17 MEDIUM, 16 HARD)
- **storia-answers-politique.sql** - 200 réponses (4 par question)

## Installation

### Méthode 1: Fichier complet (recommandée)

```bash
mysql -u root -p < sql/storia-complete.sql
```

Ou depuis MySQL:

```sql
SOURCE C:/xampp/htdocs/storia_project/sql/storia-complete.sql;
```

### Méthode 2: Installation manuelle

```bash
mysql -u root -p < sql/storia-structure.sql
mysql -u root -p < sql/storia-data.sql
mysql -u root -p < sql/storia-answers-historia.sql
mysql -u root -p < sql/storia-questions-geo.sql
mysql -u root -p < sql/storia-answers-geo.sql
mysql -u root -p < sql/storia-questions-sport.sql
mysql -u root -p < sql/storia-answers-sport.sql
mysql -u root -p < sql/storia-questions-science.sql
mysql -u root -p < sql/storia-answers-science.sql
mysql -u root -p < sql/storia-questions-politique.sql
mysql -u root -p < sql/storia-answers-politique.sql
```

## Statistiques

### Total
- **250 questions** (50 par thème)
- **1000 réponses** (4 par question, 1 seule correcte)

### Distribution par difficulté (par thème)
- **EASY**: 17 questions (10 points chacune)
- **MEDIUM**: 17 questions (20 points chacune)
- **HARD**: 16 questions (30 points chacune)

### Thèmes
1. **Historia** - Questions sur l'histoire mondiale et tunisienne
2. **Géographie** - Découvrir le monde et ses merveilles
3. **Sport** - Connaissances sportives
4. **Scientifique** - Sciences et découvertes
5. **Politique Tunisie** - Politique et histoire contemporaine tunisienne

## Utilisateurs de test

Tous les utilisateurs ont le même mot de passe: `password123`

- **admin@storia.com** - Administrateur (ROLE_USER + ROLE_ADMIN)
- **alice@example.com** - Utilisateur normal
- **bob@example.com** - Utilisateur normal
- **charlie@example.com** - Utilisateur normal

## Vérification de l'installation

```sql
USE storia;

-- Vérifier le nombre de questions
SELECT COUNT(*) AS 'Total questions' FROM questions;

-- Vérifier le nombre de réponses
SELECT COUNT(*) AS 'Total réponses' FROM answers;

-- Vérifier la distribution par thème
SELECT t.name, COUNT(q.id) AS 'Nombre de questions'
FROM themes t
LEFT JOIN questions q ON t.id = q.theme_id
GROUP BY t.name;

-- Vérifier la distribution par difficulté
SELECT difficulty, COUNT(*) AS 'Nombre'
FROM questions
GROUP BY difficulty;
```

## Notes importantes

- Chaque question a exactement 4 réponses
- Une seule réponse est correcte par question (is_correct = TRUE)
- Les IDs des questions sont séquentiels (1-250)
- Toutes les questions sont disponibles pour les modes solo et groupe
- Les points sont attribués selon la difficulté: EASY=10, MEDIUM=20, HARD=30
