-- CORRECTION IMMÉDIATE - Supprimer la colonne created_by
USE storia;

ALTER TABLE group_quiz_sessions DROP COLUMN created_by;

-- Vérifier que c'est corrigé
SHOW COLUMNS FROM group_quiz_sessions;
