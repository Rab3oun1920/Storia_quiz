-- Données de test pour le système de réclamations
-- À exécuter APRÈS avoir créé la table reclamations

-- Note: Assurez-vous que les user_id existent dans votre table users
-- Ces exemples utilisent les IDs 1, 2, 3 (modifiez selon vos besoins)

-- Réclamation 1 : En attente (non traitée)
INSERT INTO reclamations (user_id, subject, description, status, created_at, updated_at)
VALUES (
    1,
    'Problème de chargement du quiz',
    'Bonjour, j''ai rencontré un problème lors du chargement du quiz solo sur le thème Histoire. La page se fige après avoir cliqué sur "Commencer". Pourriez-vous regarder ce problème ? Merci.',
    'EN_ATTENTE',
    NOW() - INTERVAL 2 DAY,
    NOW() - INTERVAL 2 DAY
);

-- Réclamation 2 : En cours (en traitement)
INSERT INTO reclamations (user_id, subject, description, status, created_at, updated_at)
VALUES (
    2,
    'Erreur de calcul des points',
    'Je pense qu''il y a une erreur dans le calcul de mes points. J''ai répondu correctement à 8 questions sur 10 en mode difficile, mais mon score affiché ne correspond pas. Pouvez-vous vérifier ?',
    'EN_COURS',
    NOW() - INTERVAL 1 DAY,
    NOW() - INTERVAL 1 DAY
);

-- Réclamation 3 : Résolue (avec réponse admin)
-- Note: Remplacez responded_by_id par l'ID d'un admin existant
INSERT INTO reclamations (
    user_id,
    subject,
    description,
    status,
    admin_response,
    responded_by_id,
    created_at,
    updated_at,
    responded_at
)
VALUES (
    1,
    'Question incorrecte sur le thème Géographie',
    'La question "Quelle est la capitale de l''Australie ?" a comme réponse correcte "Sydney", mais la vraie réponse est "Canberra". Merci de corriger.',
    'RESOLU',
    'Merci beaucoup pour votre signalement ! Vous avez tout à fait raison. Nous avons corrigé la question et la bonne réponse est maintenant "Canberra". Nous apprécions votre vigilance qui nous aide à améliorer la qualité de notre contenu.',
    1,  -- Remplacez par l'ID d'un admin
    NOW() - INTERVAL 5 DAY,
    NOW() - INTERVAL 3 DAY,
    NOW() - INTERVAL 3 DAY
);

-- Réclamation 4 : En attente (récente)
INSERT INTO reclamations (user_id, subject, description, status, created_at, updated_at)
VALUES (
    3,
    'Impossible de rejoindre une session de groupe',
    'J''essaie de rejoindre une session de groupe avec le code ABC123 mais j''obtiens toujours un message d''erreur "Session non trouvée". Le code est pourtant correct.',
    'EN_ATTENTE',
    NOW() - INTERVAL 3 HOUR,
    NOW() - INTERVAL 3 HOUR
);

-- Réclamation 5 : Fermée
INSERT INTO reclamations (
    user_id,
    subject,
    description,
    status,
    admin_response,
    responded_by_id,
    created_at,
    updated_at,
    responded_at
)
VALUES (
    2,
    'Suggestion d''amélioration',
    'Ce n''est pas vraiment un problème, mais je suggère d''ajouter plus de questions sur le thème Sport. Il n''y en a pas assez à mon goût. Merci !',
    'FERME',
    'Merci pour votre suggestion ! Nous prenons note de votre retour. Nous travaillons activement à enrichir tous nos thèmes avec de nouvelles questions. Restez à l''écoute pour les prochaines mises à jour !',
    1,  -- Remplacez par l'ID d'un admin
    NOW() - INTERVAL 7 DAY,
    NOW() - INTERVAL 6 DAY,
    NOW() - INTERVAL 6 DAY
);

-- Réclamation 6 : En cours (traitement en cours)
INSERT INTO reclamations (
    user_id,
    subject,
    description,
    status,
    admin_response,
    responded_by_id,
    created_at,
    updated_at,
    responded_at
)
VALUES (
    3,
    'Bug sur mobile',
    'L''application ne fonctionne pas correctement sur mon iPhone. Les boutons de réponse sont coupés et je ne peux pas sélectionner la dernière option.',
    'EN_COURS',
    'Bonjour, merci pour votre signalement. Nous avons identifié le problème et notre équipe technique travaille actuellement sur une correction. Nous vous tiendrons informé dès que le bug sera résolu.',
    1,  -- Remplacez par l'ID d'un admin
    NOW() - INTERVAL 2 DAY,
    NOW() - INTERVAL 1 DAY,
    NOW() - INTERVAL 1 DAY
);

-- Réclamation 7 : En attente (très récente)
INSERT INTO reclamations (user_id, subject, description, status, created_at, updated_at)
VALUES (
    1,
    'Temps de réponse trop court',
    'Les 10 secondes pour répondre aux questions en mode solo sont trop courtes. Serait-il possible d''augmenter ce délai à 15 ou 20 secondes ?',
    'EN_ATTENTE',
    NOW() - INTERVAL 30 MINUTE,
    NOW() - INTERVAL 30 MINUTE
);

-- Réclamation 8 : Résolue
INSERT INTO reclamations (
    user_id,
    subject,
    description,
    status,
    admin_response,
    responded_by_id,
    created_at,
    updated_at,
    responded_at
)
VALUES (
    2,
    'Problème de connexion',
    'Je n''arrive pas à me connecter. Mon mot de passe ne fonctionne plus. J''ai essayé plusieurs fois mais ça ne marche pas.',
    'RESOLU',
    'Nous avons réinitialisé votre mot de passe. Vous devriez avoir reçu un email avec les instructions. Si vous rencontrez toujours des difficultés, n''hésitez pas à nous contacter à nouveau.',
    1,  -- Remplacez par l'ID d'un admin
    NOW() - INTERVAL 10 DAY,
    NOW() - INTERVAL 9 DAY,
    NOW() - INTERVAL 9 DAY
);

-- Vérification des données insérées
SELECT
    id,
    user_id,
    subject,
    status,
    created_at,
    CASE
        WHEN admin_response IS NOT NULL THEN 'Oui'
        ELSE 'Non'
    END as 'Réponse Admin'
FROM reclamations
ORDER BY created_at DESC;

-- Statistiques
SELECT
    status,
    COUNT(*) as nombre
FROM reclamations
GROUP BY status;
