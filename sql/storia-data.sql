-- ============================================
-- STORIA - DONNÉES INITIALES
-- ============================================

USE storia;

-- ============================================
-- ROLES
-- ============================================
INSERT INTO roles (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- ============================================
-- UTILISATEURS DE TEST
-- Tous les mots de passe: password123
-- Hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2
-- ============================================
INSERT INTO users (id, username, email, password, first_name, last_name, country, created_at) VALUES
(1, 'admin', 'admin@storia.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2', 'Admin', 'Storia', 'Tunisia', NOW()),
(2, 'alice', 'alice@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2', 'Alice', 'Martin', 'France', NOW()),
(3, 'bob', 'bob@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2', 'Bob', 'Dupont', 'Tunisia', NOW()),
(4, 'charlie', 'charlie@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2', 'Charlie', 'Ben Ali', 'Tunisia', NOW());

-- User Roles
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), (1, 2),  -- admin has both USER and ADMIN roles
(2, 1),
(3, 1),
(4, 1);

-- ============================================
-- THÈMES
-- ============================================
INSERT INTO themes (id, name, icon, description, is_active) VALUES
(1, 'Historia', '📜', 'Questions sur l''histoire mondiale et tunisienne', TRUE),
(2, 'Géographie', '🌍', 'Découvrez le monde et ses merveilles', TRUE),
(3, 'Sport', '⚽', 'Testez vos connaissances sportives', TRUE),
(4, 'Scientifique', '🔬', 'Sciences et découvertes', TRUE),
(5, 'Politique Tunisie', '🇹🇳', 'Politique et histoire contemporaine de la Tunisie', TRUE);

-- ============================================
-- QUESTIONS - THEME 1: HISTORIA (50 questions)
-- Distribution: 17 EASY, 17 MEDIUM, 16 HARD
-- ============================================

-- EASY Questions (1-17)
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(1, 'En quelle année a eu lieu la Révolution française ?', 'EASY', 10, TRUE, TRUE),
(1, 'Qui était le premier président de la République tunisienne ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quel empereur romain a construit un mur en Grande-Bretagne ?', 'EASY', 10, TRUE, TRUE),
(1, 'En quelle année la Tunisie a-t-elle obtenu son indépendance ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quel pays a découvert l''Amérique en 1492 ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quelle civilisation a construit les pyramides en Égypte ?', 'EASY', 10, TRUE, TRUE),
(1, 'Qui était le chef de l''armée française pendant la Révolution ?', 'EASY', 10, TRUE, TRUE),
(1, 'En quelle année la Première Guerre mondiale a-t-elle commencé ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quelle ville était la capitale de l''Empire romain ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quel événement a marqué le début de la Seconde Guerre mondiale ?', 'EASY', 10, TRUE, TRUE),
(1, 'Qui a découvert l''Amérique ?', 'EASY', 10, TRUE, TRUE),
(1, 'En quelle année l''homme a-t-il marché sur la Lune ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quel empire a construit le Colisée ?', 'EASY', 10, TRUE, TRUE),
(1, 'Qui était Hannibal ?', 'EASY', 10, TRUE, TRUE),
(1, 'En quelle année le mur de Berlin est-il tombé ?', 'EASY', 10, TRUE, TRUE),
(1, 'Quel pays colonisait la Tunisie avant l''indépendance ?', 'EASY', 10, TRUE, TRUE),
(1, 'Qui était Jules César ?', 'EASY', 10, TRUE, TRUE);

-- MEDIUM Questions (18-34)
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(1, 'Quel traité a mis fin à la Première Guerre mondiale ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Qui était le leader du mouvement de libération tunisien ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'En quelle année l''Empire ottoman a-t-il pris fin ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quelle bataille a marqué la fin de Napoléon ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quel pharaon a construit la Grande Pyramide ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quelle guerre a opposé le Nord et le Sud des États-Unis ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Qui a unifié l''Allemagne en 1871 ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quel événement a déclenché la Révolution tunisienne de 2011 ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quelle dynastie a régné en Tunisie avant le protectorat français ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Qui était le roi de France pendant la Révolution française ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'En quelle année la Tunisie est-elle devenue une république ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quelle bataille navale a été gagnée par les Britanniques en 1805 ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quel empereur romain a converti l''Empire au christianisme ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Qui a écrit "Le Manifeste du Parti communiste" ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quelle conférence a redessiné l''Europe après la Seconde Guerre mondiale ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quel président américain a aboli l''esclavage ?', 'MEDIUM', 20, TRUE, TRUE),
(1, 'Quelle révolution industrielle a commencé en Grande-Bretagne au 18e siècle ?', 'MEDIUM', 20, TRUE, TRUE);

-- HARD Questions (35-50)
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(1, 'Quel traité a établi les frontières de la Tunisie moderne en 1881 ?', 'HARD', 30, TRUE, TRUE),
(1, 'Qui était le bey de Tunis lors de l''établissement du protectorat français ?', 'HARD', 30, TRUE, TRUE),
(1, 'En quelle année la bataille de Zama a-t-elle eu lieu ?', 'HARD', 30, TRUE, TRUE),
(1, 'Quel concile a défini le dogme de la Trinité en 325 ?', 'HARD', 30, TRUE, TRUE),
(1, 'Qui a mené la révolte des esclaves à Rome en 73 av. J.-C. ?', 'HARD', 30, TRUE, TRUE),
(1, 'Quelle dynastie musulmane a régné sur l''Andalousie ?', 'HARD', 30, TRUE, TRUE),
(1, 'En quelle année le califat abbasside a-t-il été établi ?', 'HARD', 30, TRUE, TRUE),
(1, 'Qui était le chef carthaginois lors de la Première Guerre punique ?', 'HARD', 30, TRUE, TRUE),
(1, 'Quelle bataille a marqué la fin de la République romaine ?', 'HARD', 30, TRUE, TRUE),
(1, 'Qui a codifié le droit romain au 6e siècle ?', 'HARD', 30, TRUE, TRUE),
(1, 'Quel traité a divisé l''Empire carolingien en 843 ?', 'HARD', 30, TRUE, TRUE),
(1, 'En quelle année la prise de Constantinople par les Ottomans a-t-elle eu lieu ?', 'HARD', 30, TRUE, TRUE),
(1, 'Qui était le sultan ottoman lors de la bataille de Lépante ?', 'HARD', 30, TRUE, TRUE),
(1, 'Quelle dynastie a régné sur Carthage avant sa destruction ?', 'HARD', 30, TRUE, TRUE),
(1, 'En quelle année le royaume de Numidie a-t-il été établi ?', 'HARD', 30, TRUE, TRUE),
(1, 'Qui était le premier calife omeyyade ?', 'HARD', 30, TRUE, TRUE);
