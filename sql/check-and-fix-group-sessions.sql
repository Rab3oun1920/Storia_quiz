-- Script pour vérifier et corriger la table group_quiz_sessions
-- Ce script supprime la colonne 'created_by' si elle existe

USE storia_db;

-- Vérifier la structure actuelle de la table
SHOW COLUMNS FROM group_quiz_sessions;

-- Supprimer la colonne 'created_by' si elle existe
SET @dbname = DATABASE();
SET @tablename = 'group_quiz_sessions';
SET @columnname = 'created_by';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (column_name = @columnname)
  ) > 0,
  CONCAT('ALTER TABLE ', @tablename, ' DROP COLUMN ', @columnname, ';'),
  'SELECT 1;'
));

PREPARE alterIfExists FROM @preparedStatement;
EXECUTE alterIfExists;
DEALLOCATE PREPARE alterIfExists;

-- Vérifier à nouveau la structure après modification
SHOW COLUMNS FROM group_quiz_sessions;
