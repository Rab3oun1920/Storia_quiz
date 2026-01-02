-- ============================================
-- STORIA - STRUCTURE DE LA BASE DE DONNÉES
-- ============================================

DROP DATABASE IF EXISTS storia;
CREATE DATABASE storia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE storia;

-- Table: users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    country VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB;

-- Table: roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Table: user_roles (Many-to-Many)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Table: refresh_tokens
CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_token (token)
) ENGINE=InnoDB;

-- Table: themes
CREATE TABLE themes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    icon VARCHAR(50),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB;

-- Table: questions
CREATE TABLE questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    theme_id BIGINT NOT NULL,
    question_text TEXT NOT NULL,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
    points INT NOT NULL,
    is_solo_question BOOLEAN DEFAULT TRUE,
    is_group_question BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE,
    INDEX idx_theme_difficulty (theme_id, difficulty),
    INDEX idx_solo (theme_id, is_solo_question),
    INDEX idx_group (theme_id, is_group_question)
) ENGINE=InnoDB;

-- Table: answers
CREATE TABLE answers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    answer_text TEXT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    INDEX idx_question (question_id)
) ENGINE=InnoDB;

-- Table: solo_quiz_sessions
CREATE TABLE solo_quiz_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    score INT DEFAULT 0,
    correct_answers INT DEFAULT 0,
    total_questions INT DEFAULT 10,
    total_time INT,
    percentage DOUBLE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE,
    INDEX idx_user_theme (user_id, theme_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB;

-- Table: user_global_scores
CREATE TABLE user_global_scores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_score INT DEFAULT 0,
    total_quizzes INT DEFAULT 0,
    total_correct_answers INT DEFAULT 0,
    total_questions_answered INT DEFAULT 0,
    average_score DOUBLE DEFAULT 0,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_total_score (total_score DESC)
) ENGINE=InnoDB;

-- Table: user_theme_scores
CREATE TABLE user_theme_scores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    total_score INT DEFAULT 0,
    quizzes_played INT DEFAULT 0,
    best_score INT DEFAULT 0,
    total_correct_answers INT DEFAULT 0,
    average_score DOUBLE DEFAULT 0,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_theme (user_id, theme_id),
    INDEX idx_theme_score (theme_id, total_score DESC)
) ENGINE=InnoDB;

-- Table: group_quiz_sessions
CREATE TABLE group_quiz_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_name VARCHAR(100) NOT NULL,
    session_code VARCHAR(50) NOT NULL UNIQUE,
    theme_id BIGINT NOT NULL,
    number_of_groups INT NOT NULL,
    total_questions INT NOT NULL,
    time_per_question INT DEFAULT 5,
    status ENUM('WAITING', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'WAITING',
    winner_group_id BIGINT,
    created_by_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_session_code (session_code),
    INDEX idx_created_by (created_by_id)
) ENGINE=InnoDB;

-- Table: group_participants
CREATE TABLE group_participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    group_name VARCHAR(100) NOT NULL,
    group_order INT NOT NULL,
    score INT DEFAULT 0,
    correct_answers INT DEFAULT 0,
    total_questions INT DEFAULT 10,
    percentage DOUBLE DEFAULT 0,
    rank INT,
    FOREIGN KEY (session_id) REFERENCES group_quiz_sessions(id) ON DELETE CASCADE,
    INDEX idx_session_order (session_id, group_order)
) ENGINE=InnoDB;

-- Table: group_quiz_answers
CREATE TABLE group_quiz_answers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    group_participant_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    answer_id BIGINT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    time_spent INT,
    answered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES group_quiz_sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (group_participant_id) REFERENCES group_participants(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (answer_id) REFERENCES answers(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Ajouter la contrainte de clé étrangère pour winner_group_id après création de group_participants
ALTER TABLE group_quiz_sessions
ADD FOREIGN KEY (winner_group_id) REFERENCES group_participants(id) ON DELETE SET NULL;
