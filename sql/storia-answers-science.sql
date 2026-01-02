-- ============================================
-- STORIA - RÉPONSES POUR LE THÈME SCIENTIFIQUE
-- 4 réponses par question (1 seule correcte)
-- ============================================

USE storia;

-- ============================================
-- RÉPONSES POUR LES QUESTIONS EASY (151-167)
-- ============================================

-- Question 151: Quelle planète est la plus proche du Soleil ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(151, 'Mercure', TRUE),
(151, 'Vénus', FALSE),
(151, 'Mars', FALSE),
(151, 'Terre', FALSE);

-- Question 152: Combien de chromosomes possède l''être humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(152, '46 chromosomes', TRUE),
(152, '48 chromosomes', FALSE),
(152, '44 chromosomes', FALSE),
(152, '42 chromosomes', FALSE);

-- Question 153: Quel gaz les plantes absorbent-elles lors de la photosynthèse ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(153, 'Le dioxyde de carbone (CO2)', TRUE),
(153, 'L''oxygène (O2)', FALSE),
(153, 'L''azote (N2)', FALSE),
(153, 'L''hydrogène (H2)', FALSE);

-- Question 154: Quelle est la formule chimique de l''eau ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(154, 'H2O', TRUE),
(154, 'H2O2', FALSE),
(154, 'HO', FALSE),
(154, 'H3O', FALSE);

-- Question 155: Quel est l''organe le plus grand du corps humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(155, 'La peau', TRUE),
(155, 'Le foie', FALSE),
(155, 'Le cerveau', FALSE),
(155, 'L''intestin', FALSE);

-- Question 156: Combien de planètes y a-t-il dans le système solaire ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(156, '8 planètes', TRUE),
(156, '9 planètes', FALSE),
(156, '7 planètes', FALSE),
(156, '10 planètes', FALSE);

-- Question 157: Quel élément a pour symbole chimique "O" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(157, 'L''oxygène', TRUE),
(157, 'L''or', FALSE),
(157, 'L''osmium', FALSE),
(157, 'L''oganesson', FALSE);

-- Question 158: Quelle est la vitesse de la lumière dans le vide ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(158, 'Environ 300 000 km/s', TRUE),
(158, 'Environ 150 000 km/s', FALSE),
(158, 'Environ 500 000 km/s', FALSE),
(158, 'Environ 200 000 km/s', FALSE);

-- Question 159: Quel organe pompe le sang dans le corps humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(159, 'Le coeur', TRUE),
(159, 'Les poumons', FALSE),
(159, 'Le foie', FALSE),
(159, 'Les reins', FALSE);

-- Question 160: Combien de temps la Terre met-elle pour faire un tour complet autour du Soleil ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(160, '365 jours (1 an)', TRUE),
(160, '366 jours', FALSE),
(160, '360 jours', FALSE),
(160, '364 jours', FALSE);

-- Question 161: Quel scientifique a découvert la pénicilline ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(161, 'Alexander Fleming', TRUE),
(161, 'Louis Pasteur', FALSE),
(161, 'Marie Curie', FALSE),
(161, 'Robert Koch', FALSE);

-- Question 162: Quelle est l''unité de mesure de la force ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(162, 'Le Newton (N)', TRUE),
(162, 'Le Joule (J)', FALSE),
(162, 'Le Watt (W)', FALSE),
(162, 'Le Pascal (Pa)', FALSE);

-- Question 163: Quel est le plus petit os du corps humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(163, 'L''étrier (dans l''oreille)', TRUE),
(163, 'La rotule', FALSE),
(163, 'Le coccyx', FALSE),
(163, 'La phalange', FALSE);

-- Question 164: Quelle planète est surnommée la "planète rouge" ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(164, 'Mars', TRUE),
(164, 'Vénus', FALSE),
(164, 'Jupiter', FALSE),
(164, 'Saturne', FALSE);

-- Question 165: Combien d''atomes d''hydrogène y a-t-il dans une molécule d''eau ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(165, '2 atomes', TRUE),
(165, '1 atome', FALSE),
(165, '3 atomes', FALSE),
(165, '4 atomes', FALSE);

-- Question 166: Quel animal pond des oeufs et allaite ses petits ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(166, 'L''ornithorynque', TRUE),
(166, 'La tortue', FALSE),
(166, 'Le crocodile', FALSE),
(166, 'Le serpent', FALSE);

-- Question 167: Quelle est la température d''ébullition de l''eau au niveau de la mer ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(167, '100°C', TRUE),
(167, '90°C', FALSE),
(167, '110°C', FALSE),
(167, '98°C', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS MEDIUM (168-184)
-- ============================================

-- Question 168: Quel scientifique a formulé la théorie de la relativité ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(168, 'Albert Einstein', TRUE),
(168, 'Isaac Newton', FALSE),
(168, 'Stephen Hawking', FALSE),
(168, 'Niels Bohr', FALSE);

-- Question 169: Quelle est l''unité de mesure de la résistance électrique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(169, 'L''Ohm (Ω)', TRUE),
(169, 'L''Ampère (A)', FALSE),
(169, 'Le Volt (V)', FALSE),
(169, 'Le Watt (W)', FALSE);

-- Question 170: Combien de lunes possède la planète Mars ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(170, '2 lunes (Phobos et Deimos)', TRUE),
(170, '1 lune', FALSE),
(170, '3 lunes', FALSE),
(170, '0 lune', FALSE);

-- Question 171: Quel est le nom du processus par lequel l''ADN est copié ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(171, 'La réplication', TRUE),
(171, 'La transcription', FALSE),
(171, 'La traduction', FALSE),
(171, 'La mitose', FALSE);

-- Question 172: Quelle particule subatomique a une charge négative ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(172, 'L''électron', TRUE),
(172, 'Le proton', FALSE),
(172, 'Le neutron', FALSE),
(172, 'Le photon', FALSE);

-- Question 173: Quel est le pH d''une solution neutre ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(173, '7', TRUE),
(173, '0', FALSE),
(173, '14', FALSE),
(173, '10', FALSE);

-- Question 174: Qui a découvert la structure en double hélice de l''ADN ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(174, 'Watson et Crick', TRUE),
(174, 'Mendel', FALSE),
(174, 'Darwin', FALSE),
(174, 'Pasteur', FALSE);

-- Question 175: Quelle est la planète la plus massive du système solaire ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(175, 'Jupiter', TRUE),
(175, 'Saturne', FALSE),
(175, 'Neptune', FALSE),
(175, 'Uranus', FALSE);

-- Question 176: Combien de valves le coeur humain possède-t-il ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(176, '4 valves', TRUE),
(176, '2 valves', FALSE),
(176, '3 valves', FALSE),
(176, '5 valves', FALSE);

-- Question 177: Quel est le symbole chimique du fer ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(177, 'Fe', TRUE),
(177, 'Fr', FALSE),
(177, 'F', FALSE),
(177, 'Fe2', FALSE);

-- Question 178: Quelle théorie explique l''évolution des espèces ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(178, 'La théorie de l''évolution par sélection naturelle de Darwin', TRUE),
(178, 'La théorie de la génération spontanée', FALSE),
(178, 'La théorie du fixisme', FALSE),
(178, 'La théorie de Lamarck', FALSE);

-- Question 179: Combien de temps la lumière du Soleil met-elle pour atteindre la Terre ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(179, 'Environ 8 minutes', TRUE),
(179, 'Environ 1 heure', FALSE),
(179, 'Environ 1 minute', FALSE),
(179, 'Environ 15 minutes', FALSE);

-- Question 180: Quel organe produit l''insuline dans le corps humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(180, 'Le pancréas', TRUE),
(180, 'Le foie', FALSE),
(180, 'L''estomac', FALSE),
(180, 'Les reins', FALSE);

-- Question 181: Quelle est la distance moyenne entre la Terre et la Lune ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(181, 'Environ 384 400 km', TRUE),
(181, 'Environ 500 000 km', FALSE),
(181, 'Environ 250 000 km', FALSE),
(181, 'Environ 1 million de km', FALSE);

-- Question 182: Quel gaz compose environ 78% de l''atmosphère terrestre ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(182, 'L''azote (N2)', TRUE),
(182, 'L''oxygène (O2)', FALSE),
(182, 'Le dioxyde de carbone (CO2)', FALSE),
(182, 'L''argon (Ar)', FALSE);

-- Question 183: Combien d''os possède un adulte humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(183, '206 os', TRUE),
(183, '200 os', FALSE),
(183, '210 os', FALSE),
(183, '195 os', FALSE);

-- Question 184: Quelle particule a découvert Marie Curie dans ses recherches ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(184, 'La radioactivité (radium et polonium)', TRUE),
(184, 'Le neutron', FALSE),
(184, 'L''électron', FALSE),
(184, 'Le proton', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS HARD (185-200)
-- ============================================

-- Question 185: Quel est le nombre d''Avogadro ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(185, '6,022 × 10²³', TRUE),
(185, '3,14 × 10²³', FALSE),
(185, '9,81 × 10²³', FALSE),
(185, '1,602 × 10²³', FALSE);

-- Question 186: Quelle est la constante de Planck ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(186, '6,626 × 10⁻³⁴ J·s', TRUE),
(186, '9,81 × 10⁻³⁴ J·s', FALSE),
(186, '3,14 × 10⁻³⁴ J·s', FALSE),
(186, '1,602 × 10⁻³⁴ J·s', FALSE);

-- Question 187: Combien de paires de nerfs crâniens possède l''être humain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(187, '12 paires', TRUE),
(187, '10 paires', FALSE),
(187, '14 paires', FALSE),
(187, '8 paires', FALSE);

-- Question 188: Quel est le point de fusion du mercure ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(188, '-38,83°C', TRUE),
(188, '0°C', FALSE),
(188, '-20°C', FALSE),
(188, '-50°C', FALSE);

-- Question 189: Quelle est la demi-vie du carbone 14 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(189, 'Environ 5 730 ans', TRUE),
(189, 'Environ 10 000 ans', FALSE),
(189, 'Environ 2 000 ans', FALSE),
(189, 'Environ 1 000 ans', FALSE);

-- Question 190: Qui a découvert le neutron ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(190, 'James Chadwick', TRUE),
(190, 'Ernest Rutherford', FALSE),
(190, 'Niels Bohr', FALSE),
(190, 'J.J. Thomson', FALSE);

-- Question 191: Quelle est la masse d''un électron en kilogrammes ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(191, '9,109 × 10⁻³¹ kg', TRUE),
(191, '1,672 × 10⁻²⁷ kg', FALSE),
(191, '6,626 × 10⁻³⁴ kg', FALSE),
(191, '3,14 × 10⁻³¹ kg', FALSE);

-- Question 192: Combien de chromosomes possède une cellule de drosophile ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(192, '8 chromosomes', TRUE),
(192, '6 chromosomes', FALSE),
(192, '10 chromosomes', FALSE),
(192, '12 chromosomes', FALSE);

-- Question 193: Quel est le nombre atomique du carbone ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(193, '6', TRUE),
(193, '12', FALSE),
(193, '8', FALSE),
(193, '14', FALSE);

-- Question 194: Quelle est la distance d''une année-lumière en kilomètres ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(194, 'Environ 9,46 × 10¹² km', TRUE),
(194, 'Environ 3 × 10⁸ km', FALSE),
(194, 'Environ 1,5 × 10⁸ km', FALSE),
(194, 'Environ 6 × 10¹² km', FALSE);

-- Question 195: Qui a formulé les lois du mouvement planétaire ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(195, 'Johannes Kepler', TRUE),
(195, 'Isaac Newton', FALSE),
(195, 'Galilée', FALSE),
(195, 'Copernic', FALSE);

-- Question 196: Quelle est la formule du glucose ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(196, 'C6H12O6', TRUE),
(196, 'C12H22O11', FALSE),
(196, 'C6H6O6', FALSE),
(196, 'C5H10O5', FALSE);

-- Question 197: Combien de bases azotées composent l''ADN ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(197, '4 bases (A, T, G, C)', TRUE),
(197, '5 bases', FALSE),
(197, '3 bases', FALSE),
(197, '6 bases', FALSE);

-- Question 198: Quel est le nom du processus de division cellulaire qui produit les gamètes ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(198, 'La méiose', TRUE),
(198, 'La mitose', FALSE),
(198, 'La cytokinèse', FALSE),
(198, 'La réplication', FALSE);

-- Question 199: Quelle particule a été découverte au CERN en 2012 ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(199, 'Le boson de Higgs', TRUE),
(199, 'Le graviton', FALSE),
(199, 'Le neutrino', FALSE),
(199, 'Le quark top', FALSE);

-- Question 200: Quelle est la constante gravitationnelle universelle ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(200, '6,674 × 10⁻¹¹ N·m²/kg²', TRUE),
(200, '9,81 m/s²', FALSE),
(200, '3 × 10⁸ m/s', FALSE),
(200, '6,022 × 10²³', FALSE);
