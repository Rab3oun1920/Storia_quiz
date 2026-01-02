-- ============================================
-- STORIA - QUESTIONS SCIENTIFIQUE (Theme 4)
-- 50 questions: 17 EASY, 17 MEDIUM, 16 HARD
-- ============================================

USE storia;

-- ============================================
-- QUESTIONS EASY (17 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(4, 'Quelle planète est la plus proche du Soleil ?', 'EASY', 10, TRUE, TRUE),
(4, 'Combien de chromosomes possède l''être humain ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel gaz les plantes absorbent-elles lors de la photosynthèse ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quelle est la formule chimique de l''eau ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel est l''organe le plus grand du corps humain ?', 'EASY', 10, TRUE, TRUE),
(4, 'Combien de planètes y a-t-il dans le système solaire ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel élément a pour symbole chimique "O" ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quelle est la vitesse de la lumière dans le vide ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel organe pompe le sang dans le corps humain ?', 'EASY', 10, TRUE, TRUE),
(4, 'Combien de temps la Terre met-elle pour faire un tour complet autour du Soleil ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel scientifique a découvert la pénicilline ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quelle est l''unité de mesure de la force ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel est le plus petit os du corps humain ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quelle planète est surnommée la "planète rouge" ?', 'EASY', 10, TRUE, TRUE),
(4, 'Combien d''atomes d''hydrogène y a-t-il dans une molécule d''eau ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quel animal pond des oeufs et allaite ses petits ?', 'EASY', 10, TRUE, TRUE),
(4, 'Quelle est la température d''ébullition de l''eau au niveau de la mer ?', 'EASY', 10, TRUE, TRUE);

-- ============================================
-- QUESTIONS MEDIUM (17 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(4, 'Quel scientifique a formulé la théorie de la relativité ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quelle est l''unité de mesure de la résistance électrique ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Combien de lunes possède la planète Mars ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quel est le nom du processus par lequel l''ADN est copié ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quelle particule subatomique a une charge négative ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quel est le pH d''une solution neutre ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Qui a découvert la structure en double hélice de l''ADN ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quelle est la planète la plus massive du système solaire ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Combien de valves le coeur humain possède-t-il ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quel est le symbole chimique du fer ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quelle théorie explique l''évolution des espèces ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Combien de temps la lumière du Soleil met-elle pour atteindre la Terre ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quel organe produit l''insuline dans le corps humain ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quelle est la distance moyenne entre la Terre et la Lune ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quel gaz compose environ 78% de l''atmosphère terrestre ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Combien d''os possède un adulte humain ?', 'MEDIUM', 20, TRUE, TRUE),
(4, 'Quelle particule a découvert Marie Curie dans ses recherches ?', 'MEDIUM', 20, TRUE, TRUE);

-- ============================================
-- QUESTIONS HARD (16 questions)
-- ============================================
INSERT INTO questions (theme_id, question_text, difficulty, points, is_solo_question, is_group_question) VALUES
(4, 'Quel est le nombre d''Avogadro ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle est la constante de Planck ?', 'HARD', 30, TRUE, TRUE),
(4, 'Combien de paires de nerfs crâniens possède l''être humain ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quel est le point de fusion du mercure ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle est la demi-vie du carbone 14 ?', 'HARD', 30, TRUE, TRUE),
(4, 'Qui a découvert le neutron ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle est la masse d''un électron en kilogrammes ?', 'HARD', 30, TRUE, TRUE),
(4, 'Combien de chromosomes possède une cellule de drosophile ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quel est le nombre atomique du carbone ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle est la distance d''une année-lumière en kilomètres ?', 'HARD', 30, TRUE, TRUE),
(4, 'Qui a formulé les lois du mouvement planétaire ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle est la formule du glucose ?', 'HARD', 30, TRUE, TRUE),
(4, 'Combien de bases azotées composent l''ADN ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quel est le nom du processus de division cellulaire qui produit les gamètes ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle particule a été découverte au CERN en 2012 ?', 'HARD', 30, TRUE, TRUE),
(4, 'Quelle est la constante gravitationnelle universelle ?', 'HARD', 30, TRUE, TRUE);
