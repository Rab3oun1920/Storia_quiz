# Guide Rapide - Système de Propositions

## 🚀 Installation en 3 étapes

### Étape 1 : Base de données (1 minute)
```sql
mysql -u root -p
USE storia;
SOURCE C:/xampp/htdocs/storia_project/sql/propositions-table.sql;
```

### Étape 2 : Redémarrer l'application (30 secondes)
```bash
# Arrêter l'app (Ctrl+C)
# Puis relancer
./mvnw spring-boot:run
```

### Étape 3 : Tester (2 minutes)
1. Ouvrir : http://localhost:8086/propositions.html
2. Proposer un thème ou une question
3. Aller sur : http://localhost:8086/admin.html
4. Cliquer sur l'onglet "💡 Propositions"
5. Examiner et approuver

**C'est tout ! ✅**

## 📝 Utilisation

### Pour les utilisateurs
```
1. Menu → Propositions
2. Choisir : Thème ou Question
3. Remplir le formulaire
4. Soumettre
5. Voir les propositions et leur statut
```

### Pour les admins
```
1. Admin Panel → Onglet Propositions
2. Voir toutes les propositions
3. Cliquer "Examiner"
4. Approuver ou Rejeter
5. Ajouter un commentaire (optionnel)
```

## 🎯 Types de propositions

### 📚 Thème
- Titre (obligatoire)
- Description (obligatoire)
- Icône (optionnel)

### ❓ Question
- Question (obligatoire)
- Thème (sélection)
- Difficulté (Facile/Moyenne/Difficile)
- 1 bonne réponse
- 3 mauvaises réponses

## 📊 Statuts

- ⏳ **EN_ATTENTE** : Pas encore examinée
- ✅ **APPROUVE** : Acceptée et ajoutée au système
- ❌ **REJETE** : Refusée

## 🔑 API Rapide

### Utilisateur
```bash
POST /api/user/propositions/theme
POST /api/user/propositions/question
GET  /api/user/propositions
```

### Admin
```bash
GET  /api/admin/propositions
PUT  /api/admin/propositions/{id}/review
GET  /api/admin/propositions/stats
```

## ✨ Fonctionnalités clés

1. **Approbation automatique** : Les propositions approuvées sont ajoutées immédiatement
2. **Filtres puissants** : Par type (Thème/Question) et statut
3. **Statistiques en temps réel** : Nombre de propositions par catégorie
4. **Commentaires admin** : Communication avec les utilisateurs
5. **Interface intuitive** : Design cohérent avec STORIA

## 🆘 Problème ?

**Table non trouvée**
```sql
SHOW TABLES LIKE 'propositions';
```

**Erreur 403**
→ Vérifiez que vous êtes connecté

**Rien ne s'affiche**
→ F12 → Console pour voir les erreurs

## 📚 Documentation complète

- `PROPOSITIONS_README.md` - Documentation détaillée
- `API_DOCUMENTATION.md` - Documentation API
- `RECLAMATIONS_README.md` - Système de réclamations

**Bon courage ! 🎉**
