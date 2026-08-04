# CoreFlow — SaaS Order Management Backend

CoreFlow est un microservice SaaS backend de gestion et de traitement des commandes, conçu pour garantir la cohérence des transactions (ACID) et servir de socle à une architecture orientée événements (*Event-Driven Architecture*).

## 🛠️ Stack Technique
* **Runtime :** Java 17 / Spring Boot 3
* **Persistance :** PostgreSQL 15 / Spring Data JPA
* **Messaging :** Apache Kafka 
* **Tests :** JUnit 5 / Mockito / AssertJ
* **Conteneurisation :** Docker & Docker Compose

## 🎯 Fonctionnalités (non exhaustives)
* **APIs REST robustes :**
  * `POST /orders` : Création et persistance d'une commande
  * `GET /orders/{id}` : Consultation par ID (UUID)
  * `GET /orders` : Récupération de la liste complète des commandes
* **Validation des payloads :** Contrôle strict des requêtes via Bean Validation (`@Valid`).
* **Gestion globale des exceptions :** Interception centralisée via `@RestControllerAdvice` avec réponses JSON standardisées (`400 Bad Request`, `404 Not Found`).
* **Tests Unitaires :** Couverture de la logique métier `OrderService` avec Mockito (exécution environ 3s).

## 📌 Contrats d'API REST

| Méthode | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/orders` | Création d'une commande | `201 Created` / `400 Bad Request` |
| `GET` | `/orders/{id}` | Récupération par ID | `200 OK` / `404 Not Found` |
| `GET` | `/orders` | Obtenir la liste complète | `200 OK` |

## ⚙️ Démarrage Rapide (Environnement Local)

### 1. Démarrer l'infrastructure
```bash
docker-compose up -d
```

### 2. Lancer l'application
```bash
mvn spring-boot:run
```

### 3. Exécuter les tests unitaires
```bash
mvn clean test
```
