-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 10 mai 2026 à 04:34
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `student_home`
--

-- --------------------------------------------------------

--
-- Structure de la table `common_speace`
--

DROP TABLE IF EXISTS `common_speace`;
CREATE TABLE IF NOT EXISTS `common_speace` (
  `id_common_speace` int NOT NULL,
  `nom_common_speace` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `type_common_speace` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `capaciter` int NOT NULL,
  PRIMARY KEY (`id_common_speace`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `contract`
--

DROP TABLE IF EXISTS `contract`;
CREATE TABLE IF NOT EXISTS `contract` (
  `id_contract` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `_date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `caution` decimal(15,2) NOT NULL,
  `statut_contract` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_payment` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_room` int NOT NULL,
  `id_student` int NOT NULL,
  PRIMARY KEY (`id_contract`),
  KEY `id_payment` (`id_payment`),
  KEY `id_room` (`id_room`),
  KEY `id_student` (`id_student`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `employes`
--

DROP TABLE IF EXISTS `employes`;
CREATE TABLE IF NOT EXISTS `employes` (
  `id_employe` int NOT NULL,
  `poste` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `salaire` decimal(25,2) NOT NULL,
  `date_embauche` date NOT NULL,
  `id_users` int NOT NULL,
  PRIMARY KEY (`id_employe`),
  KEY `id_users` (`id_users`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `incidents`
--

DROP TABLE IF EXISTS `incidents`;
CREATE TABLE IF NOT EXISTS `incidents` (
  `id_incident` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `type_incident` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `priorite` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `statut_incident` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `date_signalement` datetime NOT NULL,
  `id_student` int NOT NULL,
  PRIMARY KEY (`id_incident`),
  KEY `id_student` (`id_student`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `maintenance_tasks`
--

DROP TABLE IF EXISTS `maintenance_tasks`;
CREATE TABLE IF NOT EXISTS `maintenance_tasks` (
  `id_maintenance_tasks` int NOT NULL,
  `date_intervention` datetime NOT NULL,
  `statut` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `commentaire` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_incident` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_employe` int NOT NULL,
  PRIMARY KEY (`id_maintenance_tasks`),
  KEY `id_incident` (`id_incident`),
  KEY `id_employe` (`id_employe`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id_payment` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `montant` decimal(15,2) NOT NULL,
  `date_paiement` date NOT NULL,
  `statut_paiement` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `penaliter` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id_payment`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- DROP TABLE IF EXISTS `reservations`;
--
-- CREATE TABLE IF NOT EXISTS `reservations` (
--
--   `id_reservation` INT NOT NULL AUTO_INCREMENT,
--
--   `date_reservation` DATE NOT NULL,
--
--   `heure_debut` TIME NOT NULL,
--
--   `heure_fin` TIME NOT NULL,
--
--   `statut_reservation` VARCHAR(250) NOT NULL,
--
--   `id_common_speace` INT NOT NULL,
--
--   `id_student` INT NOT NULL,
--
--   PRIMARY KEY (`id_reservation`),
--
--   KEY `id_common_speace` (`id_common_speace`),
--
--   KEY `id_student` (`id_student`)
--
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `residences`
--

DROP TABLE IF EXISTS `residences`;
CREATE TABLE IF NOT EXISTS `residences` (
  `id_residence` int NOT NULL,
  `nom_residence` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `adresse` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `capacite_totale` int NOT NULL,
  PRIMARY KEY (`id_residence`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id_roles` int NOT NULL,
  `nom` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_roles`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE IF NOT EXISTS `rooms` (
  `id_room` int NOT NULL,
  `numero_room` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `type_room` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `capaciter_room` int NOT NULL,
  `statut_room` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `loyer` decimal(15,2) NOT NULL,
  `id_residence` int NOT NULL,
  `id_incident` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_room`),
  KEY `id_residence` (`id_residence`),
  KEY `id_incident` (`id_incident`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `id_student` int NOT NULL,
  `numero_etudiant` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `ecole` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `telephone` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `date_entree` date NOT NULL,
  `date_sortie` date NOT NULL,
  `id_users` int NOT NULL,
  PRIMARY KEY (`id_student`),
  KEY `id_users` (`id_users`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id_users` int NOT NULL,
  `nom_users` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `prenom_user` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `email_user` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `mot_passe_hash` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `statut` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
  `date_creation` datetime NOT NULL,
  `id_roles` int NOT NULL,
  PRIMARY KEY (`id_users`),
  UNIQUE KEY `email_user` (`email_user`),
  KEY `id_roles` (`id_roles`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id_users`, `nom_users`, `prenom_user`, `email_user`, `mot_passe_hash`, `statut`, `date_creation`, `id_roles`)
VALUES(1, 'test', 'user', 'test@gmail.com', '$2a$10$b0/vZqXZH4.5/UT9SQx.CuiGA0dGj/Tor7p1ltpI8pXyY3WPRtCyy', 'ACTIF', '2026-05-10 06:31:50', 1);
COMMIT;

INSERT INTO roles (id_roles, nom)
VALUES (1, 'ADMIN');
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- Ajouter une résidence
INSERT INTO residences
(id_residence, nom_residence, adresse, capacite_totale)
VALUES
    (1, 'Residence A', 'Tunis', 100);

-- Ajouter une chambre
INSERT INTO rooms
(id_room, numero_room, type_room, capaciter_room,
 statut_room, loyer, id_residence, id_incident)
VALUES
    (1, 'A101', 'SIMPLE', 1,
     'LIBRE', 500, 1, 'INC001');

-- Ajouter un paiement
INSERT INTO payments
(id_payment, montant, date_paiement,
 statut_paiement, penaliter)
VALUES
    ('PAY001', 1000,
     '2026-05-10',
     'PAYE',
     0);

-- Ajouter un etudiant
INSERT INTO students
(id_student, numero_etudiant, ecole,
 telephone, date_entree, date_sortie, id_users)
VALUES
    (1,
     'ET001',
     'ESPRIT',
     '12345678',
     '2026-05-10',
     '2027-05-10',
     1);

CREATE TABLE affectations (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              student_id INT NOT NULL,
                              room_id INT NOT NULL,
                              affectation_date DATE NOT NULL
);

ALTER TABLE students
DROP COLUMN numero_etudiant;

ALTER TABLE students
    ADD nom VARCHAR(100);

ALTER TABLE students
    ADD prenom VARCHAR(100);

ALTER TABLE students
    ADD email VARCHAR(150);


<!-- hbox
    <HBox spacing="20" alignment="CENTER"
          style="-fx-padding: 30;"
          HBox.hgrow="ALWAYS">
-->

-- 1. On s'assure que le rôle ETUDIANT existe (ignore l'erreur si l'ID 2 existe déjà)
INSERT IGNORE INTO `roles` (`id_roles`, `nom`) VALUES (2, 'ETUDIANT');

-- 2. On supprime l'ancien étudiant de test pour repartir à neuf
DELETE FROM `students` WHERE `id_users` = 2;
DELETE FROM `users` WHERE `id_users` = 2;

-- 3. On insère proprement l'utilisateur de test avec le mot de passe 'etudiant123'
INSERT INTO `users` (`id_users`, `nom_users`, `prenom_user`, `email_user`, `mot_passe_hash`, `statut`, `date_creation`, `id_roles`)
VALUES (2, 'Durand', 'Lucas', 'lucas@gmail.com', 'etudiant123', 'ACTIF', NOW(), 2);

-- 4. On l'associe dans la table 'students'
INSERT INTO `students` (`id_student`, `telephone`, `date_entree`, `date_sortie`, `id_users`, `nom`, `prenom`, `email`)
VALUES (2, '0601020304', '2026-09-01', '2027-06-30', 2, 'Durand', 'Lucas', 'lucas@gmail.com');