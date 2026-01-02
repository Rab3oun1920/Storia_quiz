# 🎓 Storia Quiz - Application de Quiz Éducatif

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

Application web de quiz éducatif avec mode solo et en groupe, système de classements, et panel d'administration.

## ✨ Fonctionnalités

### 🎮 **Modes de jeu**
- **Quiz Solo** : 10 questions, 5 secondes par question, 200 points max
- **Quiz Groupe** : 2-4 groupes, 10 questions/groupe, classement en temps réel

### 🏆 **Classements**
- Classement global des utilisateurs
- Classement par thème
- Historique des performances
- Statistiques détaillées

### 👤 **Gestion utilisateurs**
- Inscription/Connexion sécurisée (JWT)
- Profil personnalisable
- Suivi de progression
- Réclamations et propositions

### 🔧 **Panel Admin**
- Gestion des thèmes et questions
- Modération des réclamations
- Validation des propositions
- Statistiques globales

## 📋 **Prérequis**

- Java 17 ou supérieur
- MySQL 8.0 ou supérieur
- Maven 3.6+
- Navigateur web moderne

## 🚀 **Installation**

### 1. Cloner le repository
\`\`\`bash
git clone https://github.com/Rab3oun1920/Storia_quiz.git
cd Storia_quiz
\`\`\`

### 2. Configurer la base de données
\`\`\`bash
# Créer la base de données
mysql -u root -p < sql/storia-structure.sql
mysql -u root -p < sql/storia-data.sql
\`\`\`

### 3. Configurer l'application
Modifier \`src/main/resources/application.properties\` :
\`\`\`properties
spring.datasource.url=jdbc:mysql://localhost:3306/storia
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
\`\`\`

### 4. Lancer l'application
\`\`\`bash
./mvnw spring-boot:run
\`\`\`

### 5. Accéder à l'application
Ouvrir votre navigateur : **http://localhost:8086**

## 🏗️ **Architecture**

\`\`\`
storia_project/
├── src/
│   ├── main/
│   │   ├── java/org/storia/
│   │   │   ├── controllers/      # API REST endpoints
│   │   │   ├── services/         # Logique métier
│   │   │   ├── repositories/     # Accès données
│   │   │   ├── entities/         # Modèles JPA
│   │   │   ├── security/         # JWT & Auth
│   │   │   └── dto/              # Transfer objects
│   │   └── resources/
│   │       ├── static/           # Frontend (HTML/CSS/JS)
│   │       └── application.properties
│   └── test/                     # Tests unitaires
├── sql/                          # Scripts SQL
└── pom.xml                       # Configuration Maven
\`\`\`

## 🛠️ **Technologies utilisées**

### Backend
- **Spring Boot 3.2** - Framework Java
- **Spring Security** - Authentication JWT
- **Spring Data JPA** - ORM
- **MySQL** - Base de données
- **Maven** - Gestion dépendances

### Frontend
- **HTML5/CSS3** - Interface utilisateur
- **JavaScript (Vanilla)** - Logique client
- **Responsive Design** - Compatible mobile

## 📚 **Documentation**

- [Guide d'utilisation](GUIDE_RAPIDE_PROPOSITIONS.md)
- [API Documentation](API_DOCUMENTATION.md)
- [Réclamations](RECLAMATIONS_README.md)
- [Propositions](PROPOSITIONS_README.md)

## 🎯 **Roadmap**

- [ ] Mode multijoueur en temps réel
- [ ] Application mobile (React Native)
- [ ] IA pour génération de questions
- [ ] Badges et achievements
- [ ] Mode compétition par équipes

## 🤝 **Contribution**

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créez votre branche (\`git checkout -b feature/AmazingFeature\`)
3. Commit vos changements (\`git commit -m 'Add AmazingFeature'\`)
4. Push vers la branche (\`git push origin feature/AmazingFeature\`)
5. Ouvrez une Pull Request

## 📝 **License**

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👥 **Auteurs**

- **Votre Nom** - *Développement initial* - [Rab3oun1920](https://github.com/Rab3oun1920)

## 🙏 **Remerciements**

- Spring Framework Team
- Communauté Open Source
- Tous les contributeurs

---

⭐ **N'oubliez pas de mettre une étoile si ce projet vous plaît !**

🤖 *Développé avec l'aide de [Claude Code](https://claude.com/claude-code)*
