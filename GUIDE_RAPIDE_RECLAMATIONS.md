# Guide Rapide - Système de Réclamations

## 🚀 Installation en 3 étapes

### Étape 1 : Base de données (1 minute)
```sql
mysql -u root -p
USE storia;
SOURCE C:/xampp/htdocs/storia_project/sql/reclamations-table.sql;
```

### Étape 2 : Redémarrer l'application (30 secondes)
```bash
# Arrêter l'app (Ctrl+C)
# Puis relancer
./mvnw spring-boot:run
```

### Étape 3 : Tester (2 minutes)
1. Ouvrir : http://localhost:8086/reclamations.html
2. Créer une réclamation
3. Aller sur : http://localhost:8086/admin.html
4. Cliquer sur l'onglet "📮 Réclamations"
5. Répondre à la réclamation

**C'est tout ! ✅**

## 📝 Utilisation

### Pour les utilisateurs
```
1. Menu → Réclamations
2. Bouton "Nouvelle Réclamation"
3. Remplir sujet + description
4. Envoyer
5. Voir les réponses
```

### Pour les admins
```
1. Admin Panel → Onglet Réclamations
2. Voir toutes les réclamations
3. Cliquer "Répondre"
4. Rédiger réponse + changer statut
5. Envoyer
```

## 🎯 Statuts

- ⏳ **EN_ATTENTE** : Nouvelle réclamation
- 🔄 **EN_COURS** : En traitement
- ✅ **RESOLU** : Problème résolu
- 🔒 **FERME** : Fermé

## 🔑 API Rapide

### Utilisateur
```bash
POST /api/user/reclamations
GET  /api/user/reclamations
```

### Admin
```bash
GET  /api/admin/reclamations
GET  /api/admin/reclamations?status=EN_ATTENTE
PUT  /api/admin/reclamations/{id}/respond
GET  /api/admin/reclamations/stats
```

## 📊 Données de test (optionnel)

Pour ajouter des réclamations de test :
```sql
SOURCE C:/xampp/htdocs/storia_project/sql/reclamations-test-data.sql;
```

## 🆘 Problème ?

**Table non trouvée**
```sql
-- Vérifier que la table existe
SHOW TABLES LIKE 'reclamations';
```

**Erreur 403**
→ Vérifiez que vous êtes connecté et avez le bon rôle

**Rien ne s'affiche**
→ F12 → Console pour voir les erreurs

## 📚 Documentation complète

- `RECLAMATIONS_README.md` - Documentation détaillée
- `INSTALLATION_RECLAMATIONS.md` - Guide complet
- `RECAPITULATIF_RECLAMATIONS.md` - Liste des modifications

## ✨ C'est prêt !

Le système est maintenant opérationnel. Les utilisateurs peuvent créer des réclamations et les admins peuvent y répondre.

**Bon courage ! 🎉**
