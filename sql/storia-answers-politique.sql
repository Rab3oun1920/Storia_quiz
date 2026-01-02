-- ============================================
-- STORIA - RÉPONSES POUR LE THÈME POLITIQUE TUNISIE
-- 4 réponses par question (1 seule correcte)
-- ============================================

USE storia;

-- ============================================
-- RÉPONSES POUR LES QUESTIONS EASY (201-217)
-- ============================================

-- Question 201: Qui était le premier président de la République tunisienne ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(201, 'Habib Bourguiba', TRUE),
(201, 'Zine El Abidine Ben Ali', FALSE),
(201, 'Moncef Bey', FALSE),
(201, 'Béji Caïd Essebsi', FALSE);

-- Question 202: En quelle année la Tunisie a-t-elle obtenu son indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(202, '1956', TRUE),
(202, '1952', FALSE),
(202, '1960', FALSE),
(202, '1945', FALSE);

-- Question 203: Quelle est la capitale de la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(203, 'Tunis', TRUE),
(203, 'Sfax', FALSE),
(203, 'Sousse', FALSE),
(203, 'Bizerte', FALSE);

-- Question 204: Quel événement a déclenché la Révolution tunisienne de 2011 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(204, 'L''immolation de Mohamed Bouazizi', TRUE),
(204, 'Une manifestation à Tunis', FALSE),
(204, 'Une grève générale', FALSE),
(204, 'Un coup d''État militaire', FALSE);

-- Question 205: Qui était le deuxième président de la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(205, 'Zine El Abidine Ben Ali', TRUE),
(205, 'Moncef Marzouki', FALSE),
(205, 'Béji Caïd Essebsi', FALSE),
(205, 'Kaïs Saïed', FALSE);

-- Question 206: Dans quelle ville Mohamed Bouazizi s''est-il immolé ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(206, 'Sidi Bouzid', TRUE),
(206, 'Tunis', FALSE),
(206, 'Kasserine', FALSE),
(206, 'Gafsa', FALSE);

-- Question 207: Quel pays colonisait la Tunisie avant l''indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(207, 'La France', TRUE),
(207, 'L''Italie', FALSE),
(207, 'L''Espagne', FALSE),
(207, 'L''Angleterre', FALSE);

-- Question 208: En quelle année la Tunisie est-elle devenue une république ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(208, '1957', TRUE),
(208, '1956', FALSE),
(208, '1958', FALSE),
(208, '1959', FALSE);

-- Question 209: Quel parti politique Habib Bourguiba a-t-il fondé ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(209, 'Le Néo-Destour', TRUE),
(209, 'Le Destour', FALSE),
(209, 'L''Union Générale Tunisienne du Travail', FALSE),
(209, 'Le Parti Socialiste Destourien', FALSE);

-- Question 210: Qui a été élu président après la révolution de 2011 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(210, 'Moncef Marzouki', TRUE),
(210, 'Béji Caïd Essebsi', FALSE),
(210, 'Rached Ghannouchi', FALSE),
(210, 'Mehdi Jomaa', FALSE);

-- Question 211: Quelle est la religion officielle de la Tunisie selon la Constitution ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(211, 'L''Islam', TRUE),
(211, 'État laïc (pas de religion officielle)', FALSE),
(211, 'Le Christianisme', FALSE),
(211, 'Le Judaïsme', FALSE);

-- Question 212: Combien de gouvernorats compte la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(212, '24 gouvernorats', TRUE),
(212, '20 gouvernorats', FALSE),
(212, '26 gouvernorats', FALSE),
(212, '22 gouvernorats', FALSE);

-- Question 213: Quel est le nom du parlement tunisien ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(213, 'L''Assemblée des Représentants du Peuple', TRUE),
(213, 'L''Assemblée Nationale', FALSE),
(213, 'Le Sénat', FALSE),
(213, 'La Chambre des Députés', FALSE);

-- Question 214: En quelle année Ben Ali a-t-il quitté le pouvoir ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(214, '2011', TRUE),
(214, '2010', FALSE),
(214, '2012', FALSE),
(214, '2009', FALSE);

-- Question 215: Quel prix Nobel la Tunisie a-t-elle remporté en 2015 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(215, 'Le Prix Nobel de la Paix', TRUE),
(215, 'Le Prix Nobel de Littérature', FALSE),
(215, 'Le Prix Nobel d''Économie', FALSE),
(215, 'Le Prix Nobel de Chimie', FALSE);

-- Question 216: Qui est le président actuel de la Tunisie (2023) ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(216, 'Kaïs Saïed', TRUE),
(216, 'Béji Caïd Essebsi', FALSE),
(216, 'Moncef Marzouki', FALSE),
(216, 'Youssef Chahed', FALSE);

-- Question 217: Quelle est la monnaie officielle de la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(217, 'Le dinar tunisien', TRUE),
(217, 'L''euro', FALSE),
(217, 'Le dirham', FALSE),
(217, 'La livre tunisienne', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS MEDIUM (218-234)
-- ============================================

-- Question 218: En quelle année Habib Bourguiba a-t-il été destitué ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(218, '1987', TRUE),
(218, '1985', FALSE),
(218, '1990', FALSE),
(218, '1988', FALSE);

-- Question 219: Quel traité a établi le protectorat français en Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(219, 'Le traité du Bardo', TRUE),
(219, 'Le traité de la Marsa', FALSE),
(219, 'Le traité de Paris', FALSE),
(219, 'Le traité de Versailles', FALSE);

-- Question 220: Qui était le bey de Tunis lors de l''indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(220, 'Lamine Bey', TRUE),
(220, 'Moncef Bey', FALSE),
(220, 'Ahmed II Bey', FALSE),
(220, 'Muhammad VIII al-Amin', FALSE);

-- Question 221: Quel était le principal opposant à Bourguiba dans les années 1950 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(221, 'Salah Ben Youssef', TRUE),
(221, 'Farhat Hached', FALSE),
(221, 'Tahar Ben Ammar', FALSE),
(221, 'Mongi Slim', FALSE);

-- Question 222: En quelle année la nouvelle Constitution tunisienne a-t-elle été adoptée après la révolution ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(222, '2014', TRUE),
(222, '2012', FALSE),
(222, '2013', FALSE),
(222, '2015', FALSE);

-- Question 223: Quel mouvement syndicaliste tunisien a été fondé en 1946 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(223, 'L''UGTT (Union Générale Tunisienne du Travail)', TRUE),
(223, 'L''UTICA', FALSE),
(223, 'Le GATT', FALSE),
(223, 'La CNSS', FALSE);

-- Question 224: Qui a succédé à Béji Caïd Essebsi après son décès ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(224, 'Kaïs Saïed', TRUE),
(224, 'Youssef Chahed', FALSE),
(224, 'Rached Ghannouchi', FALSE),
(224, 'Abdelfattah Mourou', FALSE);

-- Question 225: Quelle organisation a reçu le Prix Nobel de la Paix en 2015 pour la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(225, 'Le Quartet du dialogue national tunisien', TRUE),
(225, 'L''UGTT', FALSE),
(225, 'L''Assemblée Constituante', FALSE),
(225, 'Ennahdha', FALSE);

-- Question 226: En quelle année le Code du Statut Personnel a-t-il été promulgué ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(226, '1956', TRUE),
(226, '1957', FALSE),
(226, '1959', FALSE),
(226, '1958', FALSE);

-- Question 227: Quel parti islamiste est arrivé au pouvoir après la révolution ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(227, 'Ennahdha', TRUE),
(227, 'Le CPR', FALSE),
(227, 'Ettakatol', FALSE),
(227, 'Al-Aridha', FALSE);

-- Question 228: Qui était le Premier ministre lors de la révolution de 2011 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(228, 'Mohamed Ghannouchi', TRUE),
(228, 'Béji Caïd Essebsi', FALSE),
(228, 'Hamadi Jebali', FALSE),
(228, 'Mehdi Jomaa', FALSE);

-- Question 229: Combien de fois Ben Ali a-t-il été réélu président ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(229, '4 fois (1989, 1994, 1999, 2004, 2009)', TRUE),
(229, '3 fois', FALSE),
(229, '5 fois', FALSE),
(229, '2 fois', FALSE);

-- Question 230: Quelle ville tunisienne a été le berceau de la révolution ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(230, 'Sidi Bouzid', TRUE),
(230, 'Tunis', FALSE),
(230, 'Kasserine', FALSE),
(230, 'Sfax', FALSE);

-- Question 231: Quel leader syndicaliste a été assassiné en 1952 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(231, 'Farhat Hached', TRUE),
(231, 'Salah Ben Youssef', FALSE),
(231, 'Habib Achour', FALSE),
(231, 'Ahmed Tlili', FALSE);

-- Question 232: En quelle année la Tunisie a-t-elle rejoint la Ligue arabe ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(232, '1958', TRUE),
(232, '1956', FALSE),
(232, '1960', FALSE),
(232, '1945', FALSE);

-- Question 233: Qui a été le premier Premier ministre de la Tunisie indépendante ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(233, 'Habib Bourguiba', TRUE),
(233, 'Tahar Ben Ammar', FALSE),
(233, 'Bahi Ladgham', FALSE),
(233, 'Hédi Nouira', FALSE);

-- Question 234: Quel régime politique était en place avant le protectorat français ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(234, 'La monarchie beylicale (Régence de Tunis)', TRUE),
(234, 'La république', FALSE),
(234, 'L''Empire ottoman direct', FALSE),
(234, 'La démocratie parlementaire', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS HARD (235-250)
-- ============================================

-- Question 235: En quelle année le traité de la Marsa a-t-il été signé ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(235, '1883', TRUE),
(235, '1881', FALSE),
(235, '1885', FALSE),
(235, '1880', FALSE);

-- Question 236: Qui était le résident général français lors de l''indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(236, 'Roger Seydoux', TRUE),
(236, 'Jean de Hauteclocque', FALSE),
(236, 'Louis Périllier', FALSE),
(236, 'Gabriel Puaux', FALSE);

-- Question 237: Combien d''articles compte la Constitution tunisienne de 2014 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(237, '149 articles', TRUE),
(237, '120 articles', FALSE),
(237, '160 articles', FALSE),
(237, '100 articles', FALSE);

-- Question 238: Quel était le nom du mouvement nationaliste fondé par Bourguiba en 1934 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(238, 'Le Néo-Destour', TRUE),
(238, 'Le Destour', FALSE),
(238, 'Le Parti Socialiste Destourien', FALSE),
(238, 'Le Rassemblement Constitutionnel Démocratique', FALSE);

-- Question 239: En quelle année Farhat Hached a-t-il été assassiné ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(239, '1952', TRUE),
(239, '1950', FALSE),
(239, '1954', FALSE),
(239, '1951', FALSE);

-- Question 240: Quel bey a aboli l''esclavage en Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(240, 'Ahmed I ibn Mustafa Bey', TRUE),
(240, 'Muhammad III as-Sadiq Bey', FALSE),
(240, 'Hammouda Pacha Bey', FALSE),
(240, 'Ali Bey', FALSE);

-- Question 241: Combien de députés compte l''Assemblée des Représentants du Peuple ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(241, '217 députés', TRUE),
(241, '200 députés', FALSE),
(241, '250 députés', FALSE),
(241, '180 députés', FALSE);

-- Question 242: En quelle année le Pacte fondamental a-t-il été promulgué ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(242, '1857', TRUE),
(242, '1861', FALSE),
(242, '1850', FALSE),
(242, '1865', FALSE);

-- Question 243: Qui était le dernier bey de Tunis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(243, 'Lamine Bey (Muhammad VIII al-Amin)', TRUE),
(243, 'Moncef Bey', FALSE),
(243, 'Ahmed II Bey', FALSE),
(243, 'Naceur Bey', FALSE);

-- Question 244: Quel article de la Constitution de 2014 garantit la liberté de conscience ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(244, 'L''article 6', TRUE),
(244, 'L''article 1', FALSE),
(244, 'L''article 10', FALSE),
(244, 'L''article 20', FALSE);

-- Question 245: En quelle année la Tunisie a-t-elle adhéré à l''ONU ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(245, '1956', TRUE),
(245, '1957', FALSE),
(245, '1958', FALSE),
(245, '1955', FALSE);

-- Question 246: Quel était le nom du journal fondé par Bourguiba en 1932 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(246, 'L''Action Tunisienne', TRUE),
(246, 'La Tunisie Nouvelle', FALSE),
(246, 'Le Destour', FALSE),
(246, 'La Voix de Tunisie', FALSE);

-- Question 247: Combien de constitutions la Tunisie a-t-elle connues depuis l''indépendance ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(247, '3 constitutions (1959, 2014, 2022)', TRUE),
(247, '2 constitutions', FALSE),
(247, '4 constitutions', FALSE),
(247, '1 constitution', FALSE);

-- Question 248: Qui a rédigé le Code du Statut Personnel de 1956 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(248, 'Une commission dirigée par des juristes sous Bourguiba', TRUE),
(248, 'Habib Bourguiba seul', FALSE),
(248, 'Tahar Ben Ammar', FALSE),
(248, 'Mongi Slim', FALSE);

-- Question 249: En quelle année le multipartisme a-t-il été instauré en Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(249, '1988', TRUE),
(249, '1987', FALSE),
(249, '1990', FALSE),
(249, '1989', FALSE);

-- Question 250: Quel était le premier nom du Néo-Destour avant 1964 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(250, 'Il s''appelait déjà Néo-Destour (changé en PSD en 1964)', TRUE),
(250, 'Le Destour', FALSE),
(250, 'Le Parti Libéral', FALSE),
(250, 'Le Rassemblement National', FALSE);
