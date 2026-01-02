-- Fix group_quiz_sessions table structure
-- Remove old 'created_by' column if it exists and ensure 'created_by_id' is correct

-- Check if created_by column exists and drop it
ALTER TABLE group_quiz_sessions DROP COLUMN IF EXISTS created_by;

-- Make sure created_by_id column exists with correct definition
-- If the column doesn't exist, this will add it
-- If it exists but is nullable, this will make it NOT NULL
ALTER TABLE group_quiz_sessions
MODIFY COLUMN created_by_id BIGINT NOT NULL;

-- Ensure the foreign key exists
-- First drop it if it exists to avoid errors
ALTER TABLE group_quiz_sessions DROP FOREIGN KEY IF EXISTS fk_group_sessions_created_by;

-- Then add it back
ALTER TABLE group_quiz_sessions
ADD CONSTRAINT fk_group_sessions_created_by
FOREIGN KEY (created_by_id) REFERENCES users(id) ON DELETE CASCADE;

-- Ensure index exists
ALTER TABLE group_quiz_sessions DROP INDEX IF EXISTS idx_created_by;
ALTER TABLE group_quiz_sessions ADD INDEX idx_created_by (created_by_id);
