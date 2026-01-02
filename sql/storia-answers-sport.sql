-- ============================================
-- STORIA - RÉPONSES POUR LE THÈME SPORT
-- 4 réponses par question (1 seule correcte)
-- ============================================

USE storia;

-- ============================================
-- RÉPONSES POUR LES QUESTIONS EASY (101-117)
-- ============================================

-- Question 101: Combien de joueurs composent une équipe de football ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(101, '11 joueurs', TRUE),
(101, '10 joueurs', FALSE),
(101, '12 joueurs', FALSE),
(101, '9 joueurs', FALSE);

-- Question 102: Dans quel sport utilise-t-on une raquette et une balle jaune ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(102, 'Le tennis', TRUE),
(102, 'Le badminton', FALSE),
(102, 'Le squash', FALSE),
(102, 'Le ping-pong', FALSE);

-- Question 103: Quelle est la couleur du maillot de l''équipe nationale tunisienne de football ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(103, 'Rouge', TRUE),
(103, 'Blanc', FALSE),
(103, 'Vert', FALSE),
(103, 'Bleu', FALSE);

-- Question 104: Combien de sets faut-il gagner pour remporter un match de tennis masculin en Grand Chelem ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(104, '3 sets', TRUE),
(104, '2 sets', FALSE),
(104, '4 sets', FALSE),
(104, '5 sets', FALSE);

-- Question 105: Dans quel sport peut-on marquer un "panier à trois points" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(105, 'Le basketball', TRUE),
(105, 'Le handball', FALSE),
(105, 'Le volleyball', FALSE),
(105, 'Le water-polo', FALSE);

-- Question 106: Tous les combien d''années ont lieu les Jeux Olympiques d''été ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(106, '4 ans', TRUE),
(106, '2 ans', FALSE),
(106, '3 ans', FALSE),
(106, '5 ans', FALSE);

-- Question 107: Quel sport pratique-t-on sur un tatami ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(107, 'Le judo', TRUE),
(107, 'Le karaté', FALSE),
(107, 'Le taekwondo', FALSE),
(107, 'La lutte', FALSE);

-- Question 108: Combien de joueurs y a-t-il sur le terrain dans une équipe de handball ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(108, '7 joueurs', TRUE),
(108, '6 joueurs', FALSE),
(108, '8 joueurs', FALSE),
(108, '5 joueurs', FALSE);

-- Question 109: Dans quel sport utilise-t-on un ballon ovale ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(109, 'Le rugby', TRUE),
(109, 'Le football américain', FALSE),
(109, 'Le football', FALSE),
(109, 'Le handball', FALSE);

-- Question 110: Quelle est la distance d''un marathon ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(110, '42,195 km', TRUE),
(110, '40 km', FALSE),
(110, '50 km', FALSE),
(110, '35 km', FALSE);

-- Question 111: Quel pays a remporté la Coupe du Monde de football 2018 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(111, 'La France', TRUE),
(111, 'La Croatie', FALSE),
(111, 'La Belgique', FALSE),
(111, 'L''Angleterre', FALSE);

-- Question 112: Dans quel sport peut-on faire un "ace" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(112, 'Le tennis', TRUE),
(112, 'Le badminton', FALSE),
(112, 'Le volleyball', FALSE),
(112, 'Le golf', FALSE);

-- Question 113: Combien de temps dure un match de football réglementaire ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(113, '90 minutes', TRUE),
(113, '80 minutes', FALSE),
(113, '100 minutes', FALSE),
(113, '120 minutes', FALSE);

-- Question 114: Dans quel sport utilise-t-on des skis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(114, 'Le ski alpin', TRUE),
(114, 'Le patinage', FALSE),
(114, 'Le hockey', FALSE),
(114, 'Le snowboard', FALSE);

-- Question 115: Quelle nageuse tunisienne a remporté une médaille d''or aux JO ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(115, 'Oussama Mellouli (homme)', TRUE),
(115, 'Ghalia Lassaad', FALSE),
(115, 'Amina Kajtazi', FALSE),
(115, 'Amel Melih', FALSE);

-- Question 116: Combien de points vaut un essai au rugby ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(116, '5 points', TRUE),
(116, '3 points', FALSE),
(116, '7 points', FALSE),
(116, '4 points', FALSE);

-- Question 117: Dans quel sport utilise-t-on une table et des pagaies ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(117, 'Le tennis de table (ping-pong)', TRUE),
(117, 'Le badminton', FALSE),
(117, 'Le squash', FALSE),
(117, 'Le hockey sur glace', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS MEDIUM (118-134)
-- ============================================

-- Question 118: Quel club tunisien a remporté la Ligue des Champions africaine en 2019 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(118, 'L''Espérance Sportive de Tunis', TRUE),
(118, 'Le Club Africain', FALSE),
(118, 'L''Étoile du Sahel', FALSE),
(118, 'Le Club Sportif Sfaxien', FALSE);

-- Question 119: Qui est le meilleur buteur de l''histoire de la Coupe du Monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(119, 'Miroslav Klose', TRUE),
(119, 'Ronaldo Nazário', FALSE),
(119, 'Gerd Müller', FALSE),
(119, 'Pelé', FALSE);

-- Question 120: En quelle année la Tunisie a-t-elle participé pour la première fois à une Coupe du Monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(120, '1978', TRUE),
(120, '1974', FALSE),
(120, '1982', FALSE),
(120, '1970', FALSE);

-- Question 121: Quel athlète détient le record du monde du 100 mètres ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(121, 'Usain Bolt', TRUE),
(121, 'Carl Lewis', FALSE),
(121, 'Yohan Blake', FALSE),
(121, 'Asafa Powell', FALSE);

-- Question 122: Dans quel pays se trouve le stade de Wembley ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(122, 'L''Angleterre', TRUE),
(122, 'L''Écosse', FALSE),
(122, 'L''Irlande', FALSE),
(122, 'Le Pays de Galles', FALSE);

-- Question 123: Quel joueur de tennis a remporté le plus de titres du Grand Chelem ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(123, 'Novak Djokovic', TRUE),
(123, 'Rafael Nadal', FALSE),
(123, 'Roger Federer', FALSE),
(123, 'Pete Sampras', FALSE);

-- Question 124: Quelle équipe tunisienne de football est surnommée "Les Aigles de Carthage" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(124, 'L''équipe nationale de Tunisie', TRUE),
(124, 'L''Espérance Sportive de Tunis', FALSE),
(124, 'Le Club Africain', FALSE),
(124, 'L''Étoile du Sahel', FALSE);

-- Question 125: Combien de joueurs composent une équipe de volleyball sur le terrain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(125, '6 joueurs', TRUE),
(125, '5 joueurs', FALSE),
(125, '7 joueurs', FALSE),
(125, '8 joueurs', FALSE);

-- Question 126: Quel pays a accueilli les Jeux Olympiques de 2016 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(126, 'Le Brésil', TRUE),
(126, 'La Chine', FALSE),
(126, 'Le Royaume-Uni', FALSE),
(126, 'Le Japon', FALSE);

-- Question 127: Dans quelle ville se trouve le stade Santiago Bernabéu ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(127, 'Madrid', TRUE),
(127, 'Barcelone', FALSE),
(127, 'Séville', FALSE),
(127, 'Valence', FALSE);

-- Question 128: Quel boxeur tunisien a remporté une médaille d''or aux JO de Rio 2016 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(128, 'Mohamed Jendoubi (bronze, pas d''or)', TRUE),
(128, 'Oussama Oueslati', FALSE),
(128, 'Radhouane Zbidi', FALSE),
(128, 'Issam Chernoubi', FALSE);

-- Question 129: Combien de Grand Chelem existe-t-il dans le tennis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(129, '4 tournois', TRUE),
(129, '3 tournois', FALSE),
(129, '5 tournois', FALSE),
(129, '6 tournois', FALSE);

-- Question 130: Quelle est la nationalité de Cristiano Ronaldo ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(130, 'Portugaise', TRUE),
(130, 'Espagnole', FALSE),
(130, 'Brésilienne', FALSE),
(130, 'Argentine', FALSE);

-- Question 131: Dans quel sport utilise-t-on un palet appelé "puck" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(131, 'Le hockey sur glace', TRUE),
(131, 'Le curling', FALSE),
(131, 'Le hockey sur gazon', FALSE),
(131, 'Le bandy', FALSE);

-- Question 132: Quel nageur a remporté le plus de médailles d''or olympiques ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(132, 'Michael Phelps', TRUE),
(132, 'Mark Spitz', FALSE),
(132, 'Ian Thorpe', FALSE),
(132, 'Ryan Lochte', FALSE);

-- Question 133: Quelle équipe a remporté le plus de Coupes d''Europe de football ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(133, 'Le Real Madrid', TRUE),
(133, 'Le FC Barcelone', FALSE),
(133, 'L''AC Milan', FALSE),
(133, 'Liverpool FC', FALSE);

-- Question 134: En quelle année la Tunisie a-t-elle remporté la Coupe d''Afrique des Nations ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(134, '2004', TRUE),
(134, '2000', FALSE),
(134, '1996', FALSE),
(134, '2008', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS HARD (135-150)
-- ============================================

-- Question 135: Quel joueur tunisien a marqué le premier but de la Tunisie en Coupe du Monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(135, 'Ali Kaabi (contre le Mexique en 1978)', TRUE),
(135, 'Tarak Dhiab', FALSE),
(135, 'Néjib Ghommidh', FALSE),
(135, 'Mokhtar Dhouib', FALSE);

-- Question 136: En quelle année le Stade de Radès a-t-il été inauguré ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(136, '2001', TRUE),
(136, '1998', FALSE),
(136, '2004', FALSE),
(136, '1995', FALSE);

-- Question 137: Quel cycliste a remporté le plus de Tours de France ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(137, 'Lance Armstrong (déchu) / Jacques Anquetil, Eddy Merckx, Bernard Hinault, Miguel Indurain (5 chacun)', TRUE),
(137, 'Chris Froome', FALSE),
(137, 'Alberto Contador', FALSE),
(137, 'Jan Ullrich', FALSE);

-- Question 138: Qui est le meilleur buteur de l''histoire de l''équipe nationale tunisienne ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(138, 'Issam Jemâa', TRUE),
(138, 'Francileudo dos Santos', FALSE),
(138, 'Ziad Jaziri', FALSE),
(138, 'Youssef Msakni', FALSE);

-- Question 139: Quel athlète tunisien a remporté une médaille d''or au 3000m steeple aux JO de 2008 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(139, 'Aucun (pas de médaille d''or en steeple)', TRUE),
(139, 'Habib Ghribi (bronze)', FALSE),
(139, 'Mahiedine Mekhissi', FALSE),
(139, 'Ali Khodja', FALSE);

-- Question 140: Combien de fois l''Espérance Sportive de Tunis a-t-elle remporté la Ligue des Champions africaine ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(140, '4 fois', TRUE),
(140, '3 fois', FALSE),
(140, '5 fois', FALSE),
(140, '2 fois', FALSE);

-- Question 141: Quel pays a accueilli la première Coupe du Monde de football en 1930 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(141, 'L''Uruguay', TRUE),
(141, 'Le Brésil', FALSE),
(141, 'L''Argentine', FALSE),
(141, 'L''Italie', FALSE);

-- Question 142: Qui est le seul joueur tunisien à avoir remporté la Ligue des Champions européenne ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(142, 'Aucun joueur tunisien n''a remporté la Ligue des Champions', TRUE),
(142, 'Youssef Msakni', FALSE),
(142, 'Wahbi Khazri', FALSE),
(142, 'Hatem Trabelsi', FALSE);

-- Question 143: En quelle année a été fondée l''Espérance Sportive de Tunis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(143, '1919', TRUE),
(143, '1920', FALSE),
(143, '1910', FALSE),
(143, '1925', FALSE);

-- Question 144: Quel joueur détient le record de buts marqués en une seule saison de Liga espagnole ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(144, 'Lionel Messi (50 buts)', TRUE),
(144, 'Cristiano Ronaldo', FALSE),
(144, 'Luis Suárez', FALSE),
(144, 'Hugo Sánchez', FALSE);

-- Question 145: Quelle nageuse tunisienne a établi un record du monde en petit bassin ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(145, 'Aucune nageuse tunisienne n''a établi de record du monde', TRUE),
(145, 'Ghalia Lassaad', FALSE),
(145, 'Amel Melih', FALSE),
(145, 'Amina Kajtazi', FALSE);

-- Question 146: Combien de titres mondiaux de Formule 1 a remporté Michael Schumacher ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(146, '7 titres', TRUE),
(146, '5 titres', FALSE),
(146, '6 titres', FALSE),
(146, '8 titres', FALSE);

-- Question 147: Quel est le surnom du Club Africain de Tunis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(147, 'Le Club Doyen', TRUE),
(147, 'Les Sang et Or', FALSE),
(147, 'Les Aigles', FALSE),
(147, 'Les Lions', FALSE);

-- Question 148: En quelle année la Tunisie a-t-elle accueilli la Coupe d''Afrique des Nations ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(148, '2004', TRUE),
(148, '2000', FALSE),
(148, '2008', FALSE),
(148, '1996', FALSE);

-- Question 149: Quel joueur a marqué le but le plus rapide de l''histoire de la Coupe du Monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(149, 'Hakan Şükür (11 secondes)', TRUE),
(149, 'Clint Dempsey', FALSE),
(149, 'Lionel Messi', FALSE),
(149, 'Cristiano Ronaldo', FALSE);

-- Question 150: Combien de fois le Real Madrid a-t-il remporté la Ligue des Champions consécutivement entre 2016 et 2018 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(150, '3 fois', TRUE),
(150, '2 fois', FALSE),
(150, '4 fois', FALSE),
(150, '5 fois', FALSE);
