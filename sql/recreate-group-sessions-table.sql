-- Recreate group_quiz_sessions table with correct structure
-- WARNING: This will delete all existing group quiz sessions data!

-- Drop dependent tables first
DROP TABLE IF EXISTS group_participants;
DROP TABLE IF EXISTS group_quiz_sessions;

-- Recreate group_quiz_sessions table
CREATE TABLE group_quiz_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_name VARCHAR(200) NOT NULL,
    session_code VARCHAR(50) NOT NULL UNIQUE,
    theme_id BIGINT NOT NULL,
    number_of_groups INT NOT NULL,
    total_questions INT NOT NULL,
    time_per_question INT DEFAULT 5,
    status VARCHAR(20) DEFAULT 'WAITING',
    winner_group_id BIGINT,
    created_by_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_session_code (session_code),
    INDEX idx_created_by (created_by_id)
) ENGINE=InnoDB;

-- Recreate group_participants table
CREATE TABLE group_participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    group_name VARCHAR(100) NOT NULL,
    group_order INT NOT NULL,
    score INT DEFAULT 0,
    correct_answers INT DEFAULT 0,
    total_questions INT DEFAULT 10,
    percentage DOUBLE DEFAULT 0.0,
    FOREIGN KEY (session_id) REFERENCES group_quiz_sessions(id) ON DELETE CASCADE,
    INDEX idx_session_group (session_id, group_order)
) ENGINE=InnoDB;
