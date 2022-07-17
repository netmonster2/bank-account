# Bank Account

## Objectif

Ce projet multi-module est développé dans le cadre d'un challenge d'architecture hexagonale.

Le but est d'écrire un API REST simple pour imiter un « compte bancaire ».
Grâce à ce service Web, on peut déposer de l'argent, retirer de l'argent et voir l'historique des opérations.

Les détails sont décrits ci-dessous :

- Le programme a 3 endpoints : Historique, Dépôt et Retrait.
- Aucune exigence d'authentification : l'API est destiné à un seul compte et est ouvert au monde.
- L'API gère les cas d'erreurs suivants:
    - Le solde insuffisant lors d'un retrait.
    - Le dépôt et le retrait avec un montant inférieur ou égal à 0.

Les approches qui seront utilisées dans ce projects sont:

- Le DDD (Domain-Driven Design) à travers l'architecture hexagonale.
- Le TDD (Test-Driven Design) à travers l'approche red/green/refactor.

## Organisation du projet

Le projet est organisé en 3 modules:

- ``` domain``` : Contient le modèle metier isolé derrière les ports (architecture hexagonale).
- ``` api``` : Contient l'adaptateur API qui expose l'application à travers un API REST.
- ``` persistence``` : Contient l'adaptateur de persistence de l'application.

## Stack technique

- **Language** : Java
- **Gestion de dépendances** : Gradle
- **Base de données** : H2
- **Framework API REST** : Spring boot
- **Documentation API** : Swagger/OpenAPI
- **Framework de test unitaire** : JUnit

## Comment exécuter l'application

```bash
docker build -t kata-bank-account .

docker run -d -p 8080:8081 kata-bank-account
```

L'application sera prête sous ``` http://localhost:8080/```

##### Comment exécuter les tests ?

```bash
./gradlew clean test
```

Le rapport de test sera disponible sous ``` build/testReport/index.html ```

## Endpoints

- ``` GET {APP_HOST}/history``` : Historique des opérations
- ``` POST {APP_HOST}/deposit``` : Dépôt de l'argent
- ``` POST {APP_HOST}/withdrawal``` : Retrait de l'argent
- ``` GET {APP_HOST}/swagger-ui.html``` : Documentation de l'API