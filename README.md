# Application de Gestion des Congés

## Description
Une application desktop de gestion des congés, avec deux interfaces principales :  
- **Interface Employé** : pour gérer leurs demandes de congés.  
- **Interface Directeur RH** : pour gérer les employés et les demandes de congés.

### Fonctionnalités
Pour une description complète des fonctionnalités, veuillez consulter le fichier : **[Cahier de charge _ Projet de gestion de congés](./Projet%20_%20GESTION_DE_CONGES/Cahier%20de%20charge%20_%20Projet%20de%20gestion%20de%20congés.pdf)**.

## Pré-requis
Avant de commencer, assurez-vous d'avoir installé :  
- **Java Development Kit (JDK)** version **17**.  
- **MySQL** pour la gestion de la base de données (ex. MySQL Workbench ou phpMyAdmin).  
- Un IDE tel que **Eclipse** ou **IntelliJ IDEA**.  

## Démarrage

1. **Cloner le Projet**  
- Téléchargez ou clonez ce projet sur votre machine :
   ```bash
   git clone https://github.com/oussamajaouabi/leave-management-system.git
   cd "Projet _ GESTION_DE_CONGES"
2. **Importer la Base de Données**
- Importez le fichier **[gestion_conges_db.sql](./Projet%20_%20GESTION_DE_CONGES/gestion_conges_db.sql)** dans votre instance MySQL.
3. **Configurer les Bibliothèques Externes**
- Assurez-vous que les fichiers ```.jar``` situés dans le dossier **[jar_files](./Projet%20_%20GESTION_DE_CONGES/jar_files)** sont ajoutés aux bibliothèques externes. 

## Configuration
Pour adapter le projet à votre environnement local, modifiez les fichiers suivants :
1. Configurer la Base de Données
- Mettez à jour les informations de connexion dans ```DatabaseConfig``` :
     ```java
     public interface DatabaseConfig { 
         String URL = "jdbc:mysql://localhost:3307/gestion_conges_db";
         String USER = "votre_utilisateur";
         String PASSWORD = "votre_mot_de_passe";
     }
     ```
2. Configurer l'Email
- Mettez à jour les informations de l'email dans ```Mail``` :
     ```java
     public class Mail {    
         private static final String FROM_EMAIL = "votre_email@gmail.com";
         private static final String FROM_EMAIL_PASSWORD = "votre_mot_de_passe"; // utilisez un mot de passe d'application
         ...
     }
     ```
## Test des Fonctionnalités
https://github.com/user-attachments/assets/bf4e3eef-95c0-4029-af7d-ee34a755b038
