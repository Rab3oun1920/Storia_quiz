-- ============================================
-- STORIA - FICHIER MAÎTRE COMPLET
-- Ce fichier source tous les fichiers SQL dans le bon ordre
-- ============================================
--
-- UTILISATION:
-- mysql -u root -p < sql/storia-complete.sql
-- ou depuis MySQL:
-- SOURCE C:/xampp/htdocs/storia_project/sql/storia-complete.sql;
--
-- ============================================

-- 1. Création de la structure de la base de données
SOURCE C:/xampp/htdocs/storia_project/sql/storia-structure.sql;

-- 2. Insertion des données initiales (rôles, utilisateurs, thèmes, questions Historia)
SOURCE C:/xampp/htdocs/storia_project/sql/storia-data.sql;

-- 3. Réponses pour le thème Historia (questions 1-50)
SOURCE C:/xampp/htdocs/storia_project/sql/storia-answers-historia.sql;

-- 4. Questions pour le thème Géographie (questions 51-100)
SOURCE C:/xampp/htdocs/storia_project/sql/storia-questions-geo.sql;

-- 5. Réponses pour le thème Géographie
SOURCE C:/xampp/htdocs/storia_project/sql/storia-answers-geo.sql;

-- 6. Questions pour le thème Sport (questions 101-150)
SOURCE C:/xampp/htdocs/storia_project/sql/storia-questions-sport.sql;

-- 7. Réponses pour le thème Sport
SOURCE C:/xampp/htdocs/storia_project/sql/storia-answers-sport.sql;

-- 8. Questions pour le thème Scientifique (questions 151-200)
SOURCE C:/xampp/htdocs/storia_project/sql/storia-questions-science.sql;

-- 9. Réponses pour le thème Scientifique
SOURCE C:/xampp/htdocs/storia_project/sql/storia-answers-science.sql;

-- 10. Questions pour le thème Politique Tunisie (questions 201-250)
SOURCE C:/xampp/htdocs/storia_project/sql/storia-questions-politique.sql;

-- 11. Réponses pour le thème Politique Tunisie
SOURCE C:/xampp/htdocs/storia_project/sql/storia-answers-politique.sql;

-- ============================================
-- FIN DE L'INSTALLATION
-- ============================================
SELECT 'Base de données Storia installée avec succès!' AS Message;
SELECT COUNT(*) AS 'Nombre total de questions' FROM questions;
SELECT COUNT(*) AS 'Nombre total de réponses' FROM answers;
SELECT theme_id, name, COUNT(q.id) AS 'Nombre de questions'
FROM themes t
LEFT JOIN questions q ON t.id = q.theme_id
GROUP BY theme_id, name;
