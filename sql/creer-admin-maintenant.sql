-- ============================================
-- CRÉATION DU COMPTE ADMIN
-- ============================================
-- Username: admin
-- Password: password123
-- Email: admin@storia.com
-- ============================================

USE storia;

-- 1. Vérifier si les rôles existent
SELECT 'Vérification des rôles...' as '';
SELECT * FROM roles;

-- 2. Créer les rôles s'ils n'existent pas
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- 3. Supprimer l'utilisateur admin s'il existe déjà (pour recommencer proprement)
DELETE FROM user_roles WHERE user_id = 1;
DELETE FROM users WHERE username = 'admin';

-- 4. Créer l'utilisateur admin
-- Hash BCrypt pour "password123": $2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2
INSERT INTO users (id, username, email, password, first_name, last_name, country, created_at) VALUES
(1, 'admin', 'admin@storia.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeKL.Q4BCXF9VLaF3YkFREH1fZMGqT5u2', 'Admin', 'Storia', 'Tunisia', NOW());

-- 5. Ajouter les rôles USER et ADMIN à l'utilisateur
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),  -- ROLE_USER
(1, 2);  -- ROLE_ADMIN

-- 6. Vérifier que tout est OK
SELECT 'Vérification du compte créé...' as '';
SELECT u.id, u.username, u.email, u.first_name, u.last_name, GROUP_CONCAT(r.name) as roles
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
WHERE u.username = 'admin'
GROUP BY u.id, u.username, u.email, u.first_name, u.last_name;

-- ============================================
-- TERMINÉ !
-- Vous pouvez maintenant vous connecter avec:
-- Username: admin
-- Password: password123
-- ============================================
