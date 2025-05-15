# Projet MongoDB – Split Databases

## Collaborateurs
- Jérémy MONGUIN
- Soufiane SAIDI
- Samrou TEOURI

## Choix d’implémentation

Notre projet utilise plusieurs instances MongoDB, chacune dédiée à une région géographique (Europe, Asie, Globale). Cette organisation permet :
- **Réduction de la duplication** : Les données spécifiques à une région (ex : produits, utilisateurs) sont stockées uniquement dans l’instance correspondante, évitant ainsi la duplication inutile entre régions.
- **Performance** : Les requêtes sont dirigées vers l’instance appropriée, ce qui réduit la charge sur chaque base et améliore la latence.
- **Résilience** : Chaque instance est configurée en replica set (primaire + réplique), assurant la haute disponibilité et la tolérance aux pannes.
- **Scalabilité** : Il est possible d’ajouter de nouvelles régions facilement en déployant de nouvelles instances et configurations.

Ce découpage résout les problèmes de duplication (pas de redondance inter-régions) et de performance (chaque requête cible la base la plus pertinente).

## Lancer le projet

### 1. Prérequis
- Docker et Docker Compose installés
- Java 17+
- Maven

### 2. Démarrage des instances MongoDB

Lancez les bases de données et leurs réplicas :
```bash
docker-compose up -d
```

### 3. Initialisation des Replica Sets
Dans un terminal pour chaque instance primaire (Europe, Asie, Globale) :

Europe :
```bash
docker exec -it <container_db-europe> mongosh --eval 'rs.initiate({_id: "rs-europe", members: [{ _id: 0, host: "db-europe:27017" }, { _id: 1, host: "db-europe-replica:27017" }]})'
```
Asie :
```bash
docker exec -it <container_db-asia> mongosh --eval 'rs.initiate({_id: "rs-asia", members: [{ _id: 0, host: "db-asia:27017" }, { _id: 1, host: "db-asia-replica:27017" }]})'
```
Globale :
```bash
docker exec -it <container_db-global> mongosh --eval 'rs.initiate({_id: "rs-global", members: [{ _id: 0, host: "db-global:27017" }]})'
```

Remplacez `<container_db-europe>` etc. par l’ID du container (visible via `docker ps`).

### 4. Démarrage du serveur Spring Boot
```bash
mvn spring-boot:run
```

L’application démarre sur `http://localhost:8080`.

## Documentation de l’API

Les principaux endpoints sont :

### Utilisateurs
- `POST /api/utilisateurs` : Créer un utilisateur (body : JSON)
- `GET /api/utilisateurs/{email}?pays=XX` : Récupérer un utilisateur par email et pays
- `GET /api/utilisateurs?pays=XX` : Liste des utilisateurs d’un pays
- `PUT /api/utilisateurs/{email}` : Modifier un utilisateur
- `DELETE /api/utilisateurs/{email}?pays=XX` : Supprimer un utilisateur

**Exemple de body JSON Utilisateur :**
```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@mail.com",
  "pays": "FR"
}
```

### Produits
- `POST /api/produits` : Créer un produit
- `GET /api/produits/{nom}?pays=XX` : Récupérer un produit par nom et pays
- `GET /api/produits?pays=XX` : Liste des produits d’un pays
- `PUT /api/produits/{nom}` : Modifier un produit
- `DELETE /api/produits/{nom}?pays=XX` : Supprimer un produit

**Exemple de body JSON Produit :**
```json
{
  "nom": "Clavier mécanique",
  "prix": 99.99,
  "description": "Clavier RGB avec switches bleus",
  "pays": "FR"
}
```

### Commandes
- `POST /api/commandes` : Créer une commande
- `GET /api/commandes/{numeroCommande}` : Récupérer une commande
- `GET /api/commandes` : Liste des commandes
- `PUT /api/commandes/{numeroCommande}` : Modifier une commande
- `DELETE /api/commandes/{numeroCommande}` : Supprimer une commande

**Exemple de body JSON Commande :**
```json
{
  "numeroCommande": "CMD20240001",
  "nomsProduits": ["Clavier mécanique", "Souris optique"],
  "emailClient": "jean.dupont@mail.com"
}
```

Pour chaque endpoint, envoyez/recevez les données au format JSON. Le paramètre `pays` permet de router la requête vers la bonne base régionale.

---
