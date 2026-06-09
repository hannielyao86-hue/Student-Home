# Smart Student Residence

Application JavaFX de gestion d'une résidence étudiante. Cette application couvre l'authentification, la gestion des étudiants, des logements, des réservations, des incidents et des paiements côté administrateur et étudiant.

## 📌 Objectif

L'objectif du projet est de fournir une interface de gestion pour :
- administrer les étudiants et les contrats,
- gérer les logements et les réservations,
- suivre les incidents et les demandes,
- enregistrer les paiements depuis l'espace étudiant et l'interface admin.

## 🧱 Technologies utilisées

- Java 17
- JavaFX 17
- Maven
- MySQL / MariaDB
- JDBC
- BCrypt pour le hachage des mots de passe

## 📁 Structure du projet

- `pom.xml` : configuration Maven et dépendances.
- `src/main/java/org/example` : code source principal.
  - `MainApp.java` : point d'entrée JavaFX.
  - `config/DatabaseConnection.java` : gestion de la connexion MySQL.
  - `controller/` : contrôleurs JavaFX pour les écrans.
  - `dao/` : accesseurs aux données MySQL.
  - `model/` : classes de domaine (Payment, Student, etc.).
  - `utils/Session.java` : gestion de session utilisateur.
- `src/main/resources/view/` : fichiers FXML des interfaces.
- `src/main/resources/db.properties` : configuration de la base de données.
- `student_home.sql` : script SQL de création et d'initialisation des tables.

## 🧠 Comment fonctionne le projet

1. L'application démarre depuis `MainApp.java` et charge la vue `login.fxml`.
2. L'utilisateur se connecte via `LoginController.java` :
   - le contrôleur valide les identifiants,
   - il charge la session utilisateur dans `utils/Session.java`,
   - il redirige vers le tableau de bord admin ou étudiant selon le rôle.
3. Chaque écran JavaFX est défini dans un fichier FXML et a un contrôleur associé dans `controller/`.
4. Les contrôleurs utilisent les classes `dao/` pour lire et écrire les données dans la base MySQL.
5. Les objets métiers sont modélisés dans `model/` pour représenter les étudiants, les paiements, les logements, les incidents, etc.

### Flux de données principal

- `controller` reçoit les événements de l'interface utilisateur (clics, envoi de formulaires).
- `controller` appelle `dao` pour exécuter des requêtes SQL.
- `dao` utilise `DatabaseConnection.java` pour obtenir la connexion MySQL.
- `dao` retourne des objets `model` au contrôleur.
- Le contrôleur met à jour la vue FXML avec les données reçues.

### Exemple : ajout d'un paiement admin

- L'admin saisit le montant, la date et le statut dans l'écran `paiements.fxml`.
- `PaiementsController.java` construit un objet `Payment`.
- Le contrôleur appelle `PaymentDAO` pour enregistrer le paiement en base.
- Le paiement est stocké dans la table `payments` avec le nom et le prénom de l'étudiant.

### Exemple : paiement étudiant

- L'étudiant saisit le nom du porteur de carte et le montant dans `student_paiement.fxml`.
- `StudentPaiementController.java` vérifie les données puis enregistre le paiement.
- Le paiement peut être consulté depuis le back-office admin si nécessaire.

## �️ Écrans principaux

- `login.fxml` : écran de connexion pour les administrateurs et les étudiants.
- `dashboard.fxml` : tableau de bord principal de l'administrateur.
- `student_dashboard.fxml` : tableau de bord principal de l'étudiant après connexion.
- `paiements.fxml` : gestion des paiements côté admin, création et affichage des paiements.
- `student_paiement.fxml` : formulaire de paiement étudiant (nom du porteur, montant, date, statut).
- `logements.fxml` : écran de gestion des logements.
- `Incident.fxml` et `student_incident.fxml` : gestion des incidents côté admin et étudiant.
- `Inscription.fxml` : inscription d'un nouvel étudiant.
- `student_reservation.fxml` : écran de réservation et consultation des réservations étudiant.
- `students.fxml` : liste des étudiants et administration des données étudiantes.

## �🚀 Installation et exécution

### Prérequis

- Java 17 installé
- Maven installé
- MySQL ou MariaDB en fonctionnement
- WAMP/XAMPP ou un autre serveur local si besoin

### Configuration de la base de données

1. Créez la base de données `student_home` dans MySQL.
2. Exécutez le script `student_home.sql` pour créer les tables et charger les données initiales.
3. Ouvrez `src/main/resources/db.properties` et mettez à jour :
   - `db.url`
   - `db.user`
   - `db.password`

### Lancer l'application

Depuis la racine du projet :

```bash
mvn clean javafx:run
```

Si vous utilisez un IDE (IntelliJ, Eclipse), importez le projet Maven puis lancez `org.example.MainApp`.

## 🧩 Fonctionnalités principales

### Authentification
- Écran de connexion
- Gestion des sessions étudiant/admin
- Inscription d'un nouvel étudiant

### Administration
- Dashboard de gestion
- Gestion des paiements
- Gestion des logements
- Suivi des incidents
- Gestion des réservations

### Étudiant
- Tableau de bord étudiant
- Paiement par carte bancaire
- Consultation des informations de logement
- Déclaration d'incidents
- Suivi des réservations

## 🔧 Points importants

- La connexion MySQL est centralisée dans `DatabaseConnection.java`.
- Les paramètres de connexion sont chargés depuis `db.properties`.
- Les vues JavaFX sont déclarées en FXML et liées aux contrôleurs correspondants.

## 🛠️ Développement

- Utilisez le bundle Maven pour compiler et exécuter.
- Les dépendances JavaFX sont déclarées dans le `pom.xml`.
- Le projet s'appuie sur des DAO pour isoler la logique de persistance.

## ⚠️ Remarques

- Assurez-vous que MySQL accepte les connexions locales et que le mot de passe dans `db.properties` est correct.
- Le script `student_home.sql` doit être réexécuté ou adapté si la base de données existe déjà.

## ✨ Améliorations possibles

- Ajout d'un système de rôles plus granulaire.
- Passage à une base de données embarquée pour les tests.
- Ajout de tests unitaires et d'intégration.
- Amélioration de l'interface utilisateur et du support multilingue.

## 📄 Licence

À compléter selon les besoins du projet.
