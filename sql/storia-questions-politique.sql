-- ============================================
-- STORIA - QUESTIONS POLITIQUE TUNISIE (Theme 5)
-- 50 questions: 17 EASY, 17 MEDIUM, 16 HARD
-- ============================================

USE storia;

-- ============================================
-- QUESTIONS EASY (17 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(5, 'Qui était le premier président de la République tunisienne ?', 'EASY', 10, TRUE, TRUE),
(5, 'En quelle année la Tunisie a-t-elle obtenu son indépendance ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quelle est la capitale de la Tunisie ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quel événement a déclenché la Révolution tunisienne de 2011 ?', 'EASY', 10, TRUE, TRUE),
(5, 'Qui était le deuxième président de la Tunisie ?', 'EASY', 10, TRUE, TRUE),
(5, 'Dans quelle ville Mohamed Bouazizi s''est-il immolé ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quel pays colonisait la Tunisie avant l''indépendance ?', 'EASY', 10, TRUE, TRUE),
(5, 'En quelle année la Tunisie est-elle devenue une république ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quel parti politique Habib Bourguiba a-t-il fondé ?', 'EASY', 10, TRUE, TRUE),
(5, 'Qui a été élu président après la révolution de 2011 ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quelle est la religion officielle de la Tunisie selon la Constitution ?', 'EASY', 10, TRUE, TRUE),
(5, 'Combien de gouvernorats compte la Tunisie ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quel est le nom du parlement tunisien ?', 'EASY', 10, TRUE, TRUE),
(5, 'En quelle année Ben Ali a-t-il quitté le pouvoir ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quel prix Nobel la Tunisie a-t-elle remporté en 2015 ?', 'EASY', 10, TRUE, TRUE),
(5, 'Qui est le président actuel de la Tunisie (2023) ?', 'EASY', 10, TRUE, TRUE),
(5, 'Quelle est la monnaie officielle de la Tunisie ?', 'EASY', 10, TRUE, TRUE);

-- ============================================
-- QUESTIONS MEDIUM (17 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(5, 'En quelle année Habib Bourguiba a-t-il été destitué ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quel traité a établi le protectorat français en Tunisie ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Qui était le bey de Tunis lors de l''indépendance ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quel était le principal opposant à Bourguiba dans les années 1950 ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'En quelle année la nouvelle Constitution tunisienne a-t-elle été adoptée après la révolution ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quel mouvement syndicaliste tunisien a été fondé en 1946 ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Qui a succédé à Béji Caïd Essebsi après son décès ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quelle organisation a reçu le Prix Nobel de la Paix en 2015 pour la Tunisie ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'En quelle année le Code du Statut Personnel a-t-il été promulgué ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quel parti islamiste est arrivé au pouvoir après la révolution ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Qui était le Premier ministre lors de la révolution de 2011 ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Combien de fois Ben Ali a-t-il été réélu président ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quelle ville tunisienne a été le berceau de la révolution ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quel leader syndicaliste a été assassiné en 1952 ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'En quelle année la Tunisie a-t-elle rejoint la Ligue arabe ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Qui a été le premier Premier ministre de la Tunisie indépendante ?', 'MEDIUM', 20, TRUE, TRUE),
(5, 'Quel régime politique était en place avant le protectorat français ?', 'MEDIUM', 20, TRUE, TRUE);

-- ============================================
-- QUESTIONS HARD (16 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(5, 'En quelle année le traité de la Marsa a-t-il été signé ?', 'HARD', 30, TRUE, TRUE),
(5, 'Qui était le résident général français lors de l''indépendance ?', 'HARD', 30, TRUE, TRUE),
(5, 'Combien d''articles compte la Constitution tunisienne de 2014 ?', 'HARD', 30, TRUE, TRUE),
(5, 'Quel était le nom du mouvement nationaliste fondé par Bourguiba en 1934 ?', 'HARD', 30, TRUE, TRUE),
(5, 'En quelle année Farhat Hached a-t-il été assassiné ?', 'HARD', 30, TRUE, TRUE),
(5, 'Quel bey a aboli l''esclavage en Tunisie ?', 'HARD', 30, TRUE, TRUE),
(5, 'Combien de députés compte l''Assemblée des Représentants du Peuple ?', 'HARD', 30, TRUE, TRUE),
(5, 'En quelle année le Pacte fondamental a-t-il été promulgué ?', 'HARD', 30, TRUE, TRUE),
(5, 'Qui était le dernier bey de Tunis ?', 'HARD', 30, TRUE, TRUE),
(5, 'Quel article de la Constitution de 2014 garantit la liberté de conscience ?', 'HARD', 30, TRUE, TRUE),
(5, 'En quelle année la Tunisie a-t-elle adhéré à l''ONU ?', 'HARD', 30, TRUE, TRUE),
(5, 'Quel était le nom du journal fondé par Bourguiba en 1932 ?', 'HARD', 30, TRUE, TRUE),
(5, 'Combien de constitutions la Tunisie a-t-elle connues depuis l''indépendance ?', 'HARD', 30, TRUE, TRUE),
(5, 'Qui a rédigé le Code du Statut Personnel de 1956 ?', 'HARD', 30, TRUE, TRUE),
(5, 'En quelle année le multipartisme a-t-il été instauré en Tunisie ?', 'HARD', 30, TRUE, TRUE),
(5, 'Quel était le premier nom du Néo-Destour avant 1964 ?', 'HARD', 30, TRUE, TRUE);
