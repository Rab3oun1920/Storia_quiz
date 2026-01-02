-- ============================================
-- STORIA - RÉPONSES POUR LE THÈME HISTORIA
-- 4 réponses par question (1 seule correcte)
-- ============================================

USE storia;

-- ============================================
-- RÉPONSES POUR LES QUESTIONS EASY (1-17)
-- ============================================

-- Question 1: En quelle année a eu lieu la Révolution française ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(1, '1789', TRUE),
(1, '1776', FALSE),
(1, '1799', FALSE),
(1, '1804', FALSE);

-- Question 2: Qui était le premier président de la République tunisienne ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(2, 'Habib Bourguiba', TRUE),
(2, 'Zine El Abidine Ben Ali', FALSE),
(2, 'Moncef Bey', FALSE),
(2, 'Béji Caïd Essebsi', FALSE);

-- Question 3: Quel empereur romain a construit un mur en Grande-Bretagne ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(3, 'Hadrien', TRUE),
(3, 'Auguste', FALSE),
(3, 'Néron', FALSE),
(3, 'Trajan', FALSE);

-- Question 4: En quelle année la Tunisie a-t-elle obtenu son indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(4, '1956', TRUE),
(4, '1952', FALSE),
(4, '1960', FALSE),
(4, '1945', FALSE);

-- Question 5: Quel pays a découvert l''Amérique en 1492 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(5, 'Espagne', TRUE),
(5, 'Portugal', FALSE),
(5, 'Angleterre', FALSE),
(5, 'France', FALSE);

-- Question 6: Quelle civilisation a construit les pyramides en Égypte ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(6, 'Les Égyptiens', TRUE),
(6, 'Les Grecs', FALSE),
(6, 'Les Romains', FALSE),
(6, 'Les Perses', FALSE);

-- Question 7: Qui était le chef de l''armée française pendant la Révolution ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(7, 'Napoléon Bonaparte', TRUE),
(7, 'Louis XVI', FALSE),
(7, 'Maximilien Robespierre', FALSE),
(7, 'Georges Danton', FALSE);

-- Question 8: En quelle année la Première Guerre mondiale a-t-elle commencé ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(8, '1914', TRUE),
(8, '1918', FALSE),
(8, '1912', FALSE),
(8, '1916', FALSE);

-- Question 9: Quelle ville était la capitale de l''Empire romain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(9, 'Rome', TRUE),
(9, 'Athènes', FALSE),
(9, 'Constantinople', FALSE),
(9, 'Alexandrie', FALSE);

-- Question 10: Quel événement a marqué le début de la Seconde Guerre mondiale ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(10, 'L''invasion de la Pologne', TRUE),
(10, 'L''attaque de Pearl Harbor', FALSE),
(10, 'La bataille de Stalingrad', FALSE),
(10, 'Le débarquement en Normandie', FALSE);

-- Question 11: Qui a découvert l''Amérique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(11, 'Christophe Colomb', TRUE),
(11, 'Amerigo Vespucci', FALSE),
(11, 'Ferdinand Magellan', FALSE),
(11, 'Vasco de Gama', FALSE);

-- Question 12: En quelle année l''homme a-t-il marché sur la Lune ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(12, '1969', TRUE),
(12, '1968', FALSE),
(12, '1970', FALSE),
(12, '1972', FALSE);

-- Question 13: Quel empire a construit le Colisée ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(13, 'L''Empire romain', TRUE),
(13, 'L''Empire grec', FALSE),
(13, 'L''Empire perse', FALSE),
(13, 'L''Empire byzantin', FALSE);

-- Question 14: Qui était Hannibal ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(14, 'Un général carthaginois', TRUE),
(14, 'Un empereur romain', FALSE),
(14, 'Un pharaon égyptien', FALSE),
(14, 'Un philosophe grec', FALSE);

-- Question 15: En quelle année le mur de Berlin est-il tombé ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(15, '1989', TRUE),
(15, '1987', FALSE),
(15, '1991', FALSE),
(15, '1985', FALSE);

-- Question 16: Quel pays colonisait la Tunisie avant l''indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(16, 'La France', TRUE),
(16, 'L''Italie', FALSE),
(16, 'L''Espagne', FALSE),
(16, 'L''Angleterre', FALSE);

-- Question 17: Qui était Jules César ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(17, 'Un général et dictateur romain', TRUE),
(17, 'Un empereur byzantin', FALSE),
(17, 'Un philosophe grec', FALSE),
(17, 'Un roi perse', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS MEDIUM (18-34)
-- ============================================

-- Question 18: Quel traité a mis fin à la Première Guerre mondiale ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(18, 'Le traité de Versailles', TRUE),
(18, 'Le traité de Paris', FALSE),
(18, 'Le traité de Vienne', FALSE),
(18, 'Le traité de Brest-Litovsk', FALSE);

-- Question 19: Qui était le leader du mouvement de libération tunisien ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(19, 'Habib Bourguiba', TRUE),
(19, 'Salah Ben Youssef', FALSE),
(19, 'Farhat Hached', FALSE),
(19, 'Tahar Ben Ammar', FALSE);

-- Question 20: En quelle année l''Empire ottoman a-t-il pris fin ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(20, '1922', TRUE),
(20, '1918', FALSE),
(20, '1920', FALSE),
(20, '1924', FALSE);

-- Question 21: Quelle bataille a marqué la fin de Napoléon ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(21, 'La bataille de Waterloo', TRUE),
(21, 'La bataille d''Austerlitz', FALSE),
(21, 'La bataille de Leipzig', FALSE),
(21, 'La bataille de Wagram', FALSE);

-- Question 22: Quel pharaon a construit la Grande Pyramide ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(22, 'Khéops', TRUE),
(22, 'Ramsès II', FALSE),
(22, 'Toutânkhamon', FALSE),
(22, 'Khéphren', FALSE);

-- Question 23: Quelle guerre a opposé le Nord et le Sud des États-Unis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(23, 'La guerre de Sécession', TRUE),
(23, 'La guerre d''Indépendance', FALSE),
(23, 'La guerre hispano-américaine', FALSE),
(23, 'La guerre de 1812', FALSE);

-- Question 24: Qui a unifié l''Allemagne en 1871 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(24, 'Otto von Bismarck', TRUE),
(24, 'Guillaume II', FALSE),
(24, 'Frédéric le Grand', FALSE),
(24, 'Helmuth von Moltke', FALSE);

-- Question 25: Quel événement a déclenché la Révolution tunisienne de 2011 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(25, 'L''immolation de Mohamed Bouazizi', TRUE),
(25, 'Une manifestation à Tunis', FALSE),
(25, 'Une grève générale', FALSE),
(25, 'Un coup d''État militaire', FALSE);

-- Question 26: Quelle dynastie a régné en Tunisie avant le protectorat français ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(26, 'La dynastie husseinite', TRUE),
(26, 'La dynastie hafside', FALSE),
(26, 'La dynastie almohade', FALSE),
(26, 'La dynastie aghlabide', FALSE);

-- Question 27: Qui était le roi de France pendant la Révolution française ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(27, 'Louis XVI', TRUE),
(27, 'Louis XIV', FALSE),
(27, 'Louis XV', FALSE),
(27, 'Louis XVIII', FALSE);

-- Question 28: En quelle année la Tunisie est-elle devenue une république ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(28, '1957', TRUE),
(28, '1956', FALSE),
(28, '1958', FALSE),
(28, '1959', FALSE);

-- Question 29: Quelle bataille navale a été gagnée par les Britanniques en 1805 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(29, 'La bataille de Trafalgar', TRUE),
(29, 'La bataille du Nil', FALSE),
(29, 'La bataille de Copenhague', FALSE),
(29, 'La bataille de Jutland', FALSE);

-- Question 30: Quel empereur romain a converti l''Empire au christianisme ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(30, 'Constantin Ier', TRUE),
(30, 'Théodose Ier', FALSE),
(30, 'Dioclétien', FALSE),
(30, 'Justinien Ier', FALSE);

-- Question 31: Qui a écrit "Le Manifeste du Parti communiste" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(31, 'Karl Marx et Friedrich Engels', TRUE),
(31, 'Lénine', FALSE),
(31, 'Trotsky', FALSE),
(31, 'Staline', FALSE);

-- Question 32: Quelle conférence a redessiné l''Europe après la Seconde Guerre mondiale ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(32, 'La conférence de Yalta', TRUE),
(32, 'La conférence de Versailles', FALSE),
(32, 'La conférence de Munich', FALSE),
(32, 'La conférence de Berlin', FALSE);

-- Question 33: Quel président américain a aboli l''esclavage ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(33, 'Abraham Lincoln', TRUE),
(33, 'George Washington', FALSE),
(33, 'Thomas Jefferson', FALSE),
(33, 'Andrew Jackson', FALSE);

-- Question 34: Quelle révolution industrielle a commencé en Grande-Bretagne au 18e siècle ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(34, 'La première révolution industrielle', TRUE),
(34, 'La deuxième révolution industrielle', FALSE),
(34, 'La révolution agricole', FALSE),
(34, 'La révolution technologique', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS HARD (35-50)
-- ============================================

-- Question 35: Quel traité a établi les frontières de la Tunisie moderne en 1881 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(35, 'Le traité du Bardo', TRUE),
(35, 'Le traité de la Marsa', FALSE),
(35, 'Le traité de Berlin', FALSE),
(35, 'Le traité de Paris', FALSE);

-- Question 36: Qui était le bey de Tunis lors de l''établissement du protectorat français ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(36, 'Muhammad III as-Sadiq', TRUE),
(36, 'Ahmad I ibn Mustafa', FALSE),
(36, 'Ali III ibn al-Husayn', FALSE),
(36, 'Muhammad IV al-Hadi', FALSE);

-- Question 37: En quelle année la bataille de Zama a-t-elle eu lieu ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(37, '202 av. J.-C.', TRUE),
(37, '216 av. J.-C.', FALSE),
(37, '146 av. J.-C.', FALSE),
(37, '264 av. J.-C.', FALSE);

-- Question 38: Quel concile a défini le dogme de la Trinité en 325 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(38, 'Le concile de Nicée', TRUE),
(38, 'Le concile de Chalcédoine', FALSE),
(38, 'Le concile d''Éphèse', FALSE),
(38, 'Le concile de Constantinople', FALSE);

-- Question 39: Qui a mené la révolte des esclaves à Rome en 73 av. J.-C. ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(39, 'Spartacus', TRUE),
(39, 'Crixus', FALSE),
(39, 'Crassus', FALSE),
(39, 'Pompée', FALSE);

-- Question 40: Quelle dynastie musulmane a régné sur l''Andalousie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(40, 'Les Omeyyades', TRUE),
(40, 'Les Abbassides', FALSE),
(40, 'Les Almohades', FALSE),
(40, 'Les Fatimides', FALSE);

-- Question 41: En quelle année le califat abbasside a-t-il été établi ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(41, '750', TRUE),
(41, '661', FALSE),
(41, '786', FALSE),
(41, '909', FALSE);

-- Question 42: Qui était le chef carthaginois lors de la Première Guerre punique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(42, 'Hamilcar Barca', TRUE),
(42, 'Hannibal Barca', FALSE),
(42, 'Hasdrubal Barca', FALSE),
(42, 'Magon Barca', FALSE);

-- Question 43: Quelle bataille a marqué la fin de la République romaine ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(43, 'La bataille d''Actium', TRUE),
(43, 'La bataille de Pharsale', FALSE),
(43, 'La bataille de Philippes', FALSE),
(43, 'La bataille d''Alésia', FALSE);

-- Question 44: Qui a codifié le droit romain au 6e siècle ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(44, 'Justinien Ier', TRUE),
(44, 'Théodose II', FALSE),
(44, 'Constantin Ier', FALSE),
(44, 'Dioclétien', FALSE);

-- Question 45: Quel traité a divisé l''Empire carolingien en 843 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(45, 'Le traité de Verdun', TRUE),
(45, 'Le traité de Mersen', FALSE),
(45, 'Le traité de Ribemont', FALSE),
(45, 'Le traité de Paris', FALSE);

-- Question 46: En quelle année la prise de Constantinople par les Ottomans a-t-elle eu lieu ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(46, '1453', TRUE),
(46, '1492', FALSE),
(46, '1415', FALSE),
(46, '1517', FALSE);

-- Question 47: Qui était le sultan ottoman lors de la bataille de Lépante ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(47, 'Sélim II', TRUE),
(47, 'Soliman le Magnifique', FALSE),
(47, 'Mehmed II', FALSE),
(47, 'Mourad III', FALSE);

-- Question 48: Quelle dynastie a régné sur Carthage avant sa destruction ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(48, 'La dynastie Magonide', TRUE),
(48, 'La dynastie Ptolémaïque', FALSE),
(48, 'La dynastie Séleucide', FALSE),
(48, 'La dynastie Antigonide', FALSE);

-- Question 49: En quelle année le royaume de Numidie a-t-il été établi ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(49, '202 av. J.-C.', TRUE),
(49, '146 av. J.-C.', FALSE),
(49, '264 av. J.-C.', FALSE),
(49, '218 av. J.-C.', FALSE);

-- Question 50: Qui était le premier calife omeyyade ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(50, 'Muawiya Ier', TRUE),
(50, 'Abd al-Malik', FALSE),
(50, 'Hicham ibn Abd al-Malik', FALSE),
(50, 'Marwan Ier', FALSE);
