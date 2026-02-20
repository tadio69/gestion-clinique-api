# gestion-clinique-api

## Description

gestion-clinique-api est le backend d'une plateforme de gestion de clinique. La plateforme gère les patients, les médecins,  
les rendevez-vous de médécin avec son patient, les prescriptions médicales, les factures. Le système est sécurisé et chaque utilisateur (médecin, secrétaire) 
a un rôle (admin, médecin, secrétaire). Le système permet aussi une communication interne par chat en temps réel entre les utilisateurs.
Il intègre une génération automatique de documents médicaux en PDF.

## Prérequis

openjdk 23.0.1 2024-10-15
OpenJDK Runtime Environment (build 23.0.1+11-39)
OpenJDK 64-Bit Server VM (build 23.0.1+11-39, mixed mode, sharing)
Apache Maven 3.6.3
Base de données sur psql (PostgreSQL) 14.17

## Installation

1.  **Cloner le dépôt via le lien GitHub :**
    https://github.com/tadio69/gestion-clinique-api.git
2. **Construire le projet avec Maven :**
   a) Naviger dans l'exporateur de fichier jusqu'au dossier gestion-clinique-api
   ~/kfomkam48_site2/JAVA SPRING BOOT-kfokam48/projets/gestion-clinique-api$
   b) Exécuter la commande
   mvn clean install


## Exécution

1.  **Exécuter avec Maven :**
    mvn spring-boot:run
2.  **Accéder à l'application :**
    sous swagger:
    http://localhost:8080/gestion-clinique-api/swagger-ui

## Fonctionnalités **
    L'api gestion-clinique-api permet:
    1- de se logger ;
    2- d'ajouter/modifier/supprimer des utilisateurs, les rendez-vous, les prescriptions ;
    2- d'éditer/télécharger/imprimer les factures ;

### `application.properties` :

# PostgreSQL config
spring.datasource.url=jdbc:postgresql://localhost:5432/gestion-clinique-api
spring.datasource.username=chijou
spring.datasource.password=duro@chijou

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

springdoc.swagger-ui.path=/gestion-clinique-api/swagger-ui

