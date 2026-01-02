-- ============================================
-- STORIA - QUESTIONS SPORT (Theme 3)
-- 50 questions: 17 EASY, 17 MEDIUM, 16 HARD
-- ============================================

USE storia;

-- ============================================
-- QUESTIONS EASY (17 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(3, 'Combien de joueurs composent une équipe de football ?', 'EASY', 10, TRUE, TRUE),
(3, 'Dans quel sport utilise-t-on une raquette et une balle jaune ?', 'EASY', 10, TRUE, TRUE),
(3, 'Quelle est la couleur du maillot de l''équipe nationale tunisienne de football ?', 'EASY', 10, TRUE, TRUE),
(3, 'Combien de sets faut-il gagner pour remporter un match de tennis masculin en Grand Chelem ?', 'EASY', 10, TRUE, TRUE),
(3, 'Dans quel sport peut-on marquer un "panier à trois points" ?', 'EASY', 10, TRUE, TRUE),
(3, 'Tous les combien d''années ont lieu les Jeux Olympiques d''été ?', 'EASY', 10, TRUE, TRUE),
(3, 'Quel sport pratique-t-on sur un tatami ?', 'EASY', 10, TRUE, TRUE),
(3, 'Combien de joueurs y a-t-il sur le terrain dans une équipe de handball ?', 'EASY', 10, TRUE, TRUE),
(3, 'Dans quel sport utilise-t-on un ballon ovale ?', 'EASY', 10, TRUE, TRUE),
(3, 'Quelle est la distance d''un marathon ?', 'EASY', 10, TRUE, TRUE),
(3, 'Quel pays a remporté la Coupe du Monde de football 2018 ?', 'EASY', 10, TRUE, TRUE),
(3, 'Dans quel sport peut-on faire un "ace" ?', 'EASY', 10, TRUE, TRUE),
(3, 'Combien de temps dure un match de football réglementaire ?', 'EASY', 10, TRUE, TRUE),
(3, 'Dans quel sport utilise-t-on des skis ?', 'EASY', 10, TRUE, TRUE),
(3, 'Quelle nageuse tunisienne a remporté une médaille d''or aux JO ?', 'EASY', 10, TRUE, TRUE),
(3, 'Combien de points vaut un essai au rugby ?', 'EASY', 10, TRUE, TRUE),
(3, 'Dans quel sport utilise-t-on une table et des pagaies ?', 'EASY', 10, TRUE, TRUE);

-- ============================================
-- QUESTIONS MEDIUM (17 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(3, 'Quel club tunisien a remporté la Ligue des Champions africaine en 2019 ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Qui est le meilleur buteur de l''histoire de la Coupe du Monde ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'En quelle année la Tunisie a-t-elle participé pour la première fois à une Coupe du Monde ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quel athlète détient le record du monde du 100 mètres ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Dans quel pays se trouve le stade de Wembley ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quel joueur de tennis a remporté le plus de titres du Grand Chelem ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quelle équipe tunisienne de football est surnommée "Les Aigles de Carthage" ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Combien de joueurs composent une équipe de volleyball sur le terrain ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quel pays a accueilli les Jeux Olympiques de 2016 ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Dans quelle ville se trouve le stade Santiago Bernabéu ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quel boxeur tunisien a remporté une médaille d''or aux JO de Rio 2016 ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Combien de Grand Chelem existe-t-il dans le tennis ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quelle est la nationalité de Cristiano Ronaldo ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Dans quel sport utilise-t-on un palet appelé "puck" ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quel nageur a remporté le plus de médailles d''or olympiques ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'Quelle équipe a remporté le plus de Coupes d''Europe de football ?', 'MEDIUM', 20, TRUE, TRUE),
(3, 'En quelle année la Tunisie a-t-elle remporté la Coupe d''Afrique des Nations ?', 'MEDIUM', 20, TRUE, TRUE);

-- ============================================
-- QUESTIONS HARD (16 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(3, 'Quel joueur tunisien a marqué le premier but de la Tunisie en Coupe du Monde ?', 'HARD', 30, TRUE, TRUE),
(3, 'En quelle année le Stade de Radès a-t-il été inauguré ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quel cycliste a remporté le plus de Tours de France ?', 'HARD', 30, TRUE, TRUE),
(3, 'Qui est le meilleur buteur de l''histoire de l''équipe nationale tunisienne ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quel athlète tunisien a remporté une médaille d''or au 3000m steeple aux JO de 2008 ?', 'HARD', 30, TRUE, TRUE),
(3, 'Combien de fois l''Espérance Sportive de Tunis a-t-elle remporté la Ligue des Champions africaine ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quel pays a accueilli la première Coupe du Monde de football en 1930 ?', 'HARD', 30, TRUE, TRUE),
(3, 'Qui est le seul joueur tunisien à avoir remporté la Ligue des Champions européenne ?', 'HARD', 30, TRUE, TRUE),
(3, 'En quelle année a été fondée l''Espérance Sportive de Tunis ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quel joueur détient le record de buts marqués en une seule saison de Liga espagnole ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quelle nageuse tunisienne a établi un record du monde en petit bassin ?', 'HARD', 30, TRUE, TRUE),
(3, 'Combien de titres mondiaux de Formule 1 a remportés Michael Schumacher ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quel est le surnom du Club Africain de Tunis ?', 'HARD', 30, TRUE, TRUE),
(3, 'En quelle année la Tunisie a-t-elle accueilli la Coupe d''Afrique des Nations ?', 'HARD', 30, TRUE, TRUE),
(3, 'Quel joueur a marqué le but le plus rapide de l''histoire de la Coupe du Monde ?', 'HARD', 30, TRUE, TRUE),
(3, 'Combien de fois le Real Madrid a-t-il remporté la Ligue des Champions consécutivement entre 2016 et 2018 ?', 'HARD', 30, TRUE, TRUE);
