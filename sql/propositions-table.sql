-- Table pour les propositions des utilisateurs (nouveaux thèmes ou questions)
CREATE TABLE IF NOT EXISTS propositions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,  -- THEME ou QUESTION

    -- Pour les thèmes
    theme_title VARCHAR(100),
    theme_description TEXT,
    theme_icon VARCHAR(50),

    -- Pour les questions
    question_text TEXT,
    theme_id BIGINT,  -- Thème suggéré pour la question
    difficulty VARCHAR(20),  -- EASY, MEDIUM, HARD
    correct_answer VARCHAR(500),
    wrong_answer_1 VARCHAR(500),
    wrong_answer_2 VARCHAR(500),
    wrong_answer_3 VARCHAR(500),

    -- Gestion
    status VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',  -- EN_ATTENTE, APPROUVE, REJETE
    admin_comment TEXT,
    reviewed_by_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE SET NULL,
    FOREIGN KEY (reviewed_by_id) REFERENCES users(id) ON DELETE SET NULL,

    INDEX idx_user_id (user_id),
    INDEX idx_type (type),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Commentaires pour documentation
ALTER TABLE propositions
    COMMENT = 'Table stockant les propositions de thèmes et questions des utilisateurs';
