-- Script pour ajouter le rôle ADMIN à un utilisateur existant
-- Remplacez 'votre_username' par le nom d'utilisateur souhaité

-- 1. Vérifier que les rôles existent
SELECT * FROM roles;

-- 2. Trouver l'ID de l'utilisateur
SELECT id, username, email FROM users WHERE username = 'votre_username';

-- 3. Ajouter le rôle ADMIN à l'utilisateur (remplacez USER_ID par l'ID trouvé à l'étape 2)
INSERT INTO user_roles (user_id, role_id)
SELECT USER_ID, id FROM roles WHERE name = 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM user_roles
    WHERE user_id = USER_ID AND role_id = (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
);

-- 4. Vérifier que le rôle a été ajouté
SELECT u.id, u.username, u.email, r.name as role
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE u.username = 'votre_username';

-- EXEMPLE : Pour ajouter le rôle ADMIN à l'utilisateur avec l'ID 1
-- INSERT INTO user_roles (user_id, role_id)
-- SELECT 1, id FROM roles WHERE name = 'ROLE_ADMIN'
-- WHERE NOT EXISTS (
--     SELECT 1 FROM user_roles
--     WHERE user_id = 1 AND role_id = (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
-- );
