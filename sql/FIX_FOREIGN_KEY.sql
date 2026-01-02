-- CORRECTION COMPLÈTE - Supprimer la contrainte puis la colonne
USE storia;

-- Étape 1: Supprimer la contrainte de clé étrangère
ALTER TABLE group_quiz_sessions DROP FOREIGN KEY FKhyrxivrgbhvq0dydtdp5bk7ai;

-- Étape 2: Supprimer l'index associé si nécessaire
ALTER TABLE group_quiz_sessions DROP INDEX FKhyrxivrgbhvq0dydtdp5bk7ai;

-- Étape 3: Supprimer la colonne created_by
ALTER TABLE group_quiz_sessions DROP COLUMN created_by;

-- Étape 4: Vérifier que tout est correct
SHOW COLUMNS FROM group_quiz_sessions;
SHOW CREATE TABLE group_quiz_sessions;
