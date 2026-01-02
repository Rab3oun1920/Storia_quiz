-- ============================================
-- STORIA - RÉPONSES POUR LE THÈME GÉOGRAPHIE
-- 4 réponses par question (1 seule correcte)
-- ============================================

USE storia;

-- ============================================
-- RÉPONSES POUR LES QUESTIONS EASY (51-67)
-- ============================================

-- Question 51: Quelle est la capitale de la France ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(51, 'Paris', TRUE),
(51, 'Lyon', FALSE),
(51, 'Marseille', FALSE),
(51, 'Toulouse', FALSE);

-- Question 52: Quel est le plus grand désert du monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(52, 'Le Sahara', TRUE),
(52, 'Le désert de Gobi', FALSE),
(52, 'Le désert d''Arabie', FALSE),
(52, 'Le désert de Kalahari', FALSE);

-- Question 53: Sur quel continent se trouve la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(53, 'Afrique', TRUE),
(53, 'Europe', FALSE),
(53, 'Asie', FALSE),
(53, 'Amérique', FALSE);

-- Question 54: Quel océan borde la côte ouest de l''Afrique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(54, 'L''océan Atlantique', TRUE),
(54, 'L''océan Indien', FALSE),
(54, 'L''océan Pacifique', FALSE),
(54, 'L''océan Arctique', FALSE);

-- Question 55: Quelle est la capitale de l'Italie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(55, 'Rome', TRUE),
(55, 'Milan', FALSE),
(55, 'Naples', FALSE),
(55, 'Florence', FALSE);

-- Question 56: Combien de continents y a-t-il sur Terre ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(56, '7', TRUE),
(56, '5', FALSE),
(56, '6', FALSE),
(56, '8', FALSE);

-- Question 57: Quel est le plus long fleuve du monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(57, 'Le Nil', TRUE),
(57, 'L''Amazone', FALSE),
(57, 'Le Yangtsé', FALSE),
(57, 'Le Mississippi', FALSE);

-- Question 58: Dans quel pays se trouve la tour Eiffel ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(58, 'France', TRUE),
(58, 'Belgique', FALSE),
(58, 'Suisse', FALSE),
(58, 'Luxembourg', FALSE);

-- Question 59: Quelle mer borde le nord de la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(59, 'La mer Méditerranée', TRUE),
(59, 'La mer Rouge', FALSE),
(59, 'La mer Noire', FALSE),
(59, 'La mer Adriatique', FALSE);

-- Question 60: Quelle est la capitale de l''Espagne ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(60, 'Madrid', TRUE),
(60, 'Barcelone', FALSE),
(60, 'Séville', FALSE),
(60, 'Valence', FALSE);

-- Question 61: Quel est le plus petit continent ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(61, 'L''Océanie (Australie)', TRUE),
(61, 'L''Europe', FALSE),
(61, 'L''Antarctique', FALSE),
(61, 'L''Amérique du Sud', FALSE);

-- Question 62: Dans quel pays se trouve le Sahara ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(62, 'Plusieurs pays d''Afrique du Nord', TRUE),
(62, 'Uniquement en Égypte', FALSE),
(62, 'Uniquement en Libye', FALSE),
(62, 'Uniquement au Maroc', FALSE);

-- Question 63: Quelle est la capitale de l''Égypte ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(63, 'Le Caire', TRUE),
(63, 'Alexandrie', FALSE),
(63, 'Louxor', FALSE),
(63, 'Assouan', FALSE);

-- Question 64: Quel est le plus haut sommet du monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(64, 'L''Everest', TRUE),
(64, 'Le K2', FALSE),
(64, 'Le Kilimandjaro', FALSE),
(64, 'Le Mont Blanc', FALSE);

-- Question 65: Dans quel pays se trouve la ville de Tunis ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(65, 'Tunisie', TRUE),
(65, 'Maroc', FALSE),
(65, 'Algérie', FALSE),
(65, 'Libye', FALSE);

-- Question 66: Quel océan est le plus grand ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(66, 'L''océan Pacifique', TRUE),
(66, 'L''océan Atlantique', FALSE),
(66, 'L''océan Indien', FALSE),
(66, 'L''océan Arctique', FALSE);

-- Question 67: Quelle est la capitale du Maroc ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(67, 'Rabat', TRUE),
(67, 'Casablanca', FALSE),
(67, 'Marrakech', FALSE),
(67, 'Fès', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS MEDIUM (68-84)
-- ============================================

-- Question 68: Quelle est la capitale de l''Australie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(68, 'Canberra', TRUE),
(68, 'Sydney', FALSE),
(68, 'Melbourne', FALSE),
(68, 'Brisbane', FALSE);

-- Question 69: Quel est le deuxième plus grand pays du monde par superficie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(69, 'Le Canada', TRUE),
(69, 'La Chine', FALSE),
(69, 'Les États-Unis', FALSE),
(69, 'Le Brésil', FALSE);

-- Question 70: Dans quelle chaîne de montagnes se trouve le mont Everest ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(70, 'L''Himalaya', TRUE),
(70, 'Les Alpes', FALSE),
(70, 'Les Andes', FALSE),
(70, 'Les Rocheuses', FALSE);

-- Question 71: Quel détroit sépare l''Afrique de l''Europe ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(71, 'Le détroit de Gibraltar', TRUE),
(71, 'Le détroit du Bosphore', FALSE),
(71, 'Le canal de Suez', FALSE),
(71, 'Le détroit de Sicile', FALSE);

-- Question 72: Quelle ville tunisienne est connue pour son amphithéâtre romain ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(72, 'El Jem', TRUE),
(72, 'Carthage', FALSE),
(72, 'Kairouan', FALSE),
(72, 'Dougga', FALSE);

-- Question 73: Quel fleuve traverse le Grand Canyon ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(73, 'Le Colorado', TRUE),
(73, 'Le Mississippi', FALSE),
(73, 'Le Missouri', FALSE),
(73, 'Le Rio Grande', FALSE);

-- Question 74: Dans quel pays se trouve le lac Titicaca ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(74, 'Pérou et Bolivie', TRUE),
(74, 'Chili et Argentine', FALSE),
(74, 'Équateur et Colombie', FALSE),
(74, 'Brésil et Paraguay', FALSE);

-- Question 75: Quelle est la plus grande île de la Méditerranée ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(75, 'La Sicile', TRUE),
(75, 'La Sardaigne', FALSE),
(75, 'La Crète', FALSE),
(75, 'Chypre', FALSE);

-- Question 76: Quel désert se trouve au sud de la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(76, 'Le Sahara', TRUE),
(76, 'Le désert de Libye', FALSE),
(76, 'Le désert du Néguev', FALSE),
(76, 'Le désert d''Arabie', FALSE);

-- Question 77: Quelle ville est considérée comme la capitale économique du Maroc ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(77, 'Casablanca', TRUE),
(77, 'Rabat', FALSE),
(77, 'Marrakech', FALSE),
(77, 'Tanger', FALSE);

-- Question 78: Quel pays possède le plus grand nombre de volcans actifs ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(78, 'L''Indonésie', TRUE),
(78, 'Le Japon', FALSE),
(78, 'Les Philippines', FALSE),
(78, 'Le Chili', FALSE);

-- Question 79: Dans quelle mer se jette le Nil ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(79, 'La mer Méditerranée', TRUE),
(79, 'La mer Rouge', FALSE),
(79, 'La mer Noire', FALSE),
(79, 'Le golfe Persique', FALSE);

-- Question 80: Quelle est la capitale du Canada ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(80, 'Ottawa', TRUE),
(80, 'Toronto', FALSE),
(80, 'Montréal', FALSE),
(80, 'Vancouver', FALSE);

-- Question 81: Quel est le point le plus bas de la Terre ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(81, 'La mer Morte', TRUE),
(81, 'La fosse des Mariannes', FALSE),
(81, 'La vallée de la Mort', FALSE),
(81, 'Le lac Assal', FALSE);

-- Question 82: Dans quel pays se trouve la péninsule du Sinaï ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(82, 'L''Égypte', TRUE),
(82, 'Israël', FALSE),
(82, 'La Jordanie', FALSE),
(82, 'L''Arabie Saoudite', FALSE);

-- Question 83: Quelle chaîne de montagnes sépare l''Europe de l''Asie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(83, 'L''Oural', TRUE),
(83, 'Le Caucase', FALSE),
(83, 'Les Carpates', FALSE),
(83, 'Les Alpes', FALSE);

-- Question 84: Combien de pays bordent la Tunisie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(84, '2 (Algérie et Libye)', TRUE),
(84, '3 (Algérie, Libye et Maroc)', FALSE),
(84, '1 (Algérie)', FALSE),
(84, '4 (Algérie, Libye, Niger et Mali)', FALSE);

-- ============================================
-- RÉPONSES POUR LES QUESTIONS HARD (85-100)
-- ============================================

-- Question 85: Quelle est la longitude du méridien de Greenwich ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(85, '0 degré', TRUE),
(85, '90 degrés', FALSE),
(85, '180 degrés', FALSE),
(85, '45 degrés', FALSE);

-- Question 86: Quel est le plus grand lac d''Afrique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(86, 'Le lac Victoria', TRUE),
(86, 'Le lac Tanganyika', FALSE),
(86, 'Le lac Malawi', FALSE),
(86, 'Le lac Tchad', FALSE);

-- Question 87: Dans quel pays se trouve le mont Kilimandjaro ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(87, 'La Tanzanie', TRUE),
(87, 'Le Kenya', FALSE),
(87, 'L''Ouganda', FALSE),
(87, 'L''Éthiopie', FALSE);

-- Question 88: Quelle ville tunisienne était autrefois appelée Hadrumetum ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(88, 'Sousse', TRUE),
(88, 'Sfax', FALSE),
(88, 'Bizerte', FALSE),
(88, 'Gabès', FALSE);

-- Question 89: Quel est le fleuve le plus long d''Europe ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(89, 'La Volga', TRUE),
(89, 'Le Danube', FALSE),
(89, 'Le Rhin', FALSE),
(89, 'Le Dniepr', FALSE);

-- Question 90: Dans quel océan se trouve l''archipel des Maldives ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(90, 'L''océan Indien', TRUE),
(90, 'L''océan Pacifique', FALSE),
(90, 'L''océan Atlantique', FALSE),
(90, 'La mer d''Arabie', FALSE);

-- Question 91: Quel pays a la plus grande forêt tropicale ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(91, 'Le Brésil (Amazonie)', TRUE),
(91, 'Le Congo', FALSE),
(91, 'L''Indonésie', FALSE),
(91, 'La Colombie', FALSE);

-- Question 92: Quelle est la capitale de la Mongolie ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(92, 'Oulan-Bator', TRUE),
(92, 'Pékin', FALSE),
(92, 'Astana', FALSE),
(92, 'Almaty', FALSE);

-- Question 93: Quel golfe sépare la Tunisie de la Sicile ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(93, 'Le canal de Sicile', TRUE),
(93, 'Le golfe de Tunis', FALSE),
(93, 'Le golfe de Gabès', FALSE),
(93, 'Le détroit de Messine', FALSE);

-- Question 94: Quel est le plus petit pays du monde ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(94, 'Le Vatican', TRUE),
(94, 'Monaco', FALSE),
(94, 'Saint-Marin', FALSE),
(94, 'Liechtenstein', FALSE);

-- Question 95: Dans quelle mer se trouve l''île de Chypre ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(95, 'La mer Méditerranée', TRUE),
(95, 'La mer Égée', FALSE),
(95, 'La mer Noire', FALSE),
(95, 'La mer Ionienne', FALSE);

-- Question 96: Quelle est la ville la plus peuplée d''Afrique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(96, 'Lagos (Nigeria)', TRUE),
(96, 'Le Caire (Égypte)', FALSE),
(96, 'Kinshasa (RDC)', FALSE),
(96, 'Johannesburg (Afrique du Sud)', FALSE);

-- Question 97: Quel détroit relie la mer Méditerranée à l''océan Atlantique ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(97, 'Le détroit de Gibraltar', TRUE),
(97, 'Le détroit du Bosphore', FALSE),
(97, 'Le détroit des Dardanelles', FALSE),
(97, 'Le canal de Suez', FALSE);

-- Question 98: Quelle ville tunisienne abrite les ruines de Carthage ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(98, 'Tunis (banlieue)', TRUE),
(98, 'Sousse', FALSE),
(98, 'Bizerte', FALSE),
(98, 'Hammamet', FALSE);

-- Question 99: Quel pays possède le plus grand nombre de fuseaux horaires ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(99, 'La France (avec ses territoires)', TRUE),
(99, 'La Russie', FALSE),
(99, 'Les États-Unis', FALSE),
(99, 'La Chine', FALSE);

-- Question 100: Quelle est la profondeur maximale de la mer Méditerranée ?
INSERT INTO answers (question_id, answer_text, is_correct) VALUES
(100, 'Environ 5 267 mètres', TRUE),
(100, 'Environ 3 000 mètres', FALSE),
(100, 'Environ 7 000 mètres', FALSE),
(100, 'Environ 2 000 mètres', FALSE);
