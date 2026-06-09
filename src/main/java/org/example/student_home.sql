-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 08 juin 2026 à 18:57
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
-- Structure de la table `affectations`
--

DROP TABLE IF EXISTS `affectations`;
CREATE TABLE IF NOT EXISTS `affectations` (
                                              `id` int NOT NULL AUTO_INCREMENT,
                                              `id_student` int NOT NULL,
                                              `room_id` int NOT NULL,
                                              `affectation_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                              `status` varchar(20) DEFAULT 'EN_ATTENTE',
    `date_entree` varchar(20) DEFAULT NULL,
    `date_sortie` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `affectations`
--

INSERT INTO `affectations` (`id`, `id_student`, `room_id`, `affectation_date`, `status`, `date_entree`, `date_sortie`) VALUES
                                                                                                                           (40, 3, 14, '2026-06-08 05:05:48', 'EN_ATTENTE', '2026-06-08', '2027-07-16'),
                                                                                                                           (41, 3, 15, '2026-06-08 15:32:41', 'EN_ATTENTE', '2026-06-08', '2027-07-09'),
                                                                                                                           (42, 11, 11, '2026-06-08 16:20:49', 'EN_ATTENTE', '2026-06-08', '2027-07-23');

-- --------------------------------------------------------

--
-- Structure de la table `common_speace`
--

DROP TABLE IF EXISTS `common_speace`;
CREATE TABLE IF NOT EXISTS `common_speace` (
                                               `id_common_speace` int NOT NULL,
                                               `nom_common_speace` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `type_common_speace` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `capaciter` int NOT NULL,
    PRIMARY KEY (`id_common_speace`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `contract`
--

DROP TABLE IF EXISTS `contract`;
CREATE TABLE IF NOT EXISTS `contract` (
                                          `id_contract` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `_date_debut` date NOT NULL,
    `date_fin` date NOT NULL,
    `caution` decimal(15,2) NOT NULL,
    `statut_contract` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `id_payment` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `id_room` int NOT NULL,
    `id_student` int NOT NULL,
    PRIMARY KEY (`id_contract`),
    KEY `id_payment` (`id_payment`),
    KEY `id_room` (`id_room`),
    KEY `id_student` (`id_student`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `contract`
--

INSERT INTO `contract` (`id_contract`, `_date_debut`, `date_fin`, `caution`, `statut_contract`, `id_payment`, `id_room`, `id_student`) VALUES
                                                                                                                                           ('CT007', '2026-05-10', '2027-05-10', 1500.00, 'ACTIF', 'PAY001', 1, 1),
                                                                                                                                           ('CTR-1780063921105', '2026-05-29', '2026-11-29', 0.00, 'LIBRE', 'NONE', 9, 0),
                                                                                                                                           ('CTR1780064285233', '2026-05-31', '2027-05-29', 0.00, 'OCCUPEE', 'NONE', 9, 0),
                                                                                                                                           ('CTR1780065040635', '2027-05-22', '2027-05-23', 0.00, 'RESERVE', 'NONE', 6, 0),
                                                                                                                                           ('CTR1780065059136', '2026-05-14', '2027-05-07', 0.00, 'OCCUPEE', 'NONE', 8, 0),
                                                                                                                                           ('CTR1780065114917', '2026-05-10', '2027-05-30', 0.00, 'OCCUPEE', 'NONE', 6, 0),
                                                                                                                                           ('CTR1780065252202', '2026-05-14', '2027-05-15', 0.00, 'OCCUPEE', 'NONE', 3, 0),
                                                                                                                                           ('CTR1780065567709', '2026-05-31', '2027-05-21', 0.00, 'OCCUPEE', 'NONE', 2, 0),
                                                                                                                                           ('CTR1780069606554', '2026-05-09', '2027-05-29', 0.00, 'OCCUPEE', 'NONE', 6, 0),
                                                                                                                                           ('CTR1780069687887', '2026-05-20', '2027-05-22', 0.00, 'OCCUPEE', 'NONE', 5, 0),
                                                                                                                                           ('CTR1780154714086', '2027-05-05', '2028-05-26', 0.00, 'OCCUPEE', 'NONE', 4, 0),
                                                                                                                                           ('CTR1780323275078', '2026-06-12', '2027-06-26', 0.00, 'OCCUPEE', 'NONE', 7, 0),
                                                                                                                                           ('CTR1780487584735', '2026-06-19', '2027-06-27', 0.00, 'OCCUPEE', 'NONE', 4, 0),
                                                                                                                                           ('CTR1780491170245', '2027-06-19', '2028-06-09', 0.00, 'OCCUPEE', 'NONE', 4, 0),
                                                                                                                                           ('CTR1780703140477', '2026-06-20', '2027-06-20', 0.00, 'OCCUPEE', 'NONE', 4, 0);

-- --------------------------------------------------------

--
-- Structure de la table `employes`
--

DROP TABLE IF EXISTS `employes`;
CREATE TABLE IF NOT EXISTS `employes` (
                                          `id_employe` int NOT NULL,
                                          `poste` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `salaire` decimal(25,2) NOT NULL,
    `date_embauche` date NOT NULL,
    `id_users` int NOT NULL,
    PRIMARY KEY (`id_employe`),
    KEY `id_users` (`id_users`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `housing_requests`
--

DROP TABLE IF EXISTS `housing_requests`;
CREATE TABLE IF NOT EXISTS `housing_requests` (
                                                  `id_request` int NOT NULL AUTO_INCREMENT,
                                                  `student_id` int NOT NULL,
                                                  `room_id` int NOT NULL,
                                                  `request_date` datetime NOT NULL,
                                                  `status` varchar(50) NOT NULL,
    PRIMARY KEY (`id_request`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `incidents`
--

DROP TABLE IF EXISTS `incidents`;
CREATE TABLE IF NOT EXISTS `incidents` (
                                           `id_incident` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `type_incident` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `description` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `priorite` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `statut_incident` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `date_signalement` datetime NOT NULL,
    `id_student` int NOT NULL,
    PRIMARY KEY (`id_incident`),
    KEY `id_student` (`id_student`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `incidents`
--

INSERT INTO `incidents` (`id_incident`, `type_incident`, `description`, `priorite`, `statut_incident`, `date_signalement`, `id_student`) VALUES
                                                                                                                                             ('INC001', 'Chauffage', 'au feu', 'Moyen', 'En cours', '2026-05-28 00:00:00', 1),
                                                                                                                                             ('INC002', 'Serrure', 'la porte est bloquer', 'Moyen', 'En cours', '2026-06-03 00:00:00', 1);

-- --------------------------------------------------------

--
-- Structure de la table `maintenance_tasks`
--

DROP TABLE IF EXISTS `maintenance_tasks`;
CREATE TABLE IF NOT EXISTS `maintenance_tasks` (
                                                   `id_maintenance_tasks` int NOT NULL,
                                                   `date_intervention` datetime NOT NULL,
                                                   `statut` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `commentaire` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `id_incident` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
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
                                          `id_payment` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `montant` decimal(15,2) NOT NULL,
    `date_paiement` date NOT NULL,
    `statut_paiement` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `penaliter` decimal(15,2) NOT NULL,
    `id_student` int NOT NULL,
    `student_nom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '',
    `student_prenom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '',
    PRIMARY KEY (`id_payment`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `payments`
--

INSERT INTO `payments` (`id_payment`, `montant`, `date_paiement`, `statut_paiement`, `penaliter`, `id_student`, `student_nom`, `student_prenom`) VALUES
    ('PAY001', 1000.00, '2026-05-10', 'PAYE', 0.00, 0, '', '');

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
                                              `id_reservation` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `date_reservation` date NOT NULL,
    `heure_debut` time NOT NULL,
    `heure_fin` time NOT NULL,
    `statut_reservation` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `id_common_speace` int NOT NULL,
    `id_student` int NOT NULL,
    PRIMARY KEY (`id_reservation`),
    KEY `id_common_speace` (`id_common_speace`),
    KEY `id_student` (`id_student`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `residences`
--

DROP TABLE IF EXISTS `residences`;
CREATE TABLE IF NOT EXISTS `residences` (
                                            `id_residence` int NOT NULL,
                                            `nom_residence` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `adresse` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
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
                                       `nom` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    PRIMARY KEY (`id_roles`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id_roles`, `nom`) VALUES
                                            (1, 'ADMIN'),
                                            (2, 'ETUDIANT');

-- --------------------------------------------------------

--
-- Structure de la table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE IF NOT EXISTS `rooms` (
                                       `id_room` int NOT NULL,
                                       `numero_room` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `type_room` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `capaciter_room` int NOT NULL,
    `statut_room` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'LIBRE',
    `loyer` decimal(15,2) NOT NULL,
    `id_residence` int NOT NULL,
    `id_incident` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    PRIMARY KEY (`id_room`),
    KEY `id_residence` (`id_residence`),
    KEY `id_incident` (`id_incident`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `rooms`
--

INSERT INTO `rooms` (`id_room`, `numero_room`, `type_room`, `capaciter_room`, `statut_room`, `loyer`, `id_residence`, `id_incident`) VALUES
                                                                                                                                         (17, 'D102', 'SIMPLE', 19, 'LIBRE', 377.68, 1, 'NONE'),
                                                                                                                                         (1, 'A101', 'SIMPLE', 17, 'LIBRE', 300.00, 1, 'NONE'),
                                                                                                                                         (2, 'A102', 'SIMPLE', 9, 'LIBRE', 250.00, 1, 'NONE'),
                                                                                                                                         (3, 'A103', 'SIMPLE', 18, 'LIBRE', 390.00, 1, 'NONE'),
                                                                                                                                         (4, 'A104', 'SIMPLE', 19, 'OCCUPEE', 340.00, 1, 'NONE'),
                                                                                                                                         (5, 'A105', 'SIMPLE', 20, 'OCCUPEE', 400.00, 1, 'NONE'),
                                                                                                                                         (6, 'B101', 'SIMPLE', 17, 'OCCUPEE', 350.00, 1, 'NONE'),
                                                                                                                                         (7, 'B102', 'SIMPLE', 19, 'OCCUPEE', 480.00, 1, 'NONE'),
                                                                                                                                         (8, 'B103', 'SIMPLE', 16, 'OCCUPEE', 350.00, 1, 'NONE'),
                                                                                                                                         (9, 'B104', 'SIMPLE', 19, 'OCCUPEE', 400.00, 1, 'NONE'),
                                                                                                                                         (10, 'B105', 'SIMPLE', 19, 'LIBRE', 388.00, 1, 'NONE'),
                                                                                                                                         (11, 'C101', 'SIMPLE', 20, 'RESERVEE', 500.00, 1, 'NONE'),
                                                                                                                                         (12, 'C102', 'SIMPLE', 9, 'LIBRE', 240.00, 1, 'NONE'),
                                                                                                                                         (13, 'C103', 'SIMPLE', 14, 'RESERVEE', 330.00, 1, 'NONE'),
                                                                                                                                         (14, 'C104', 'SIMPLE', 9, 'RESERVEE', 255.00, 1, 'NONE'),
                                                                                                                                         (15, 'C105', 'SIMPLE', 16, 'RESERVEE', 350.00, 1, 'NONE'),
                                                                                                                                         (16, 'D101', 'SIMPLE', 14, 'LIBRE', 340.00, 1, 'NONE'),
                                                                                                                                         (18, 'D103', 'SIMPLE', 17, 'LIBRE', 325.80, 1, 'NONE'),
                                                                                                                                         (19, 'D104', 'SIMPLE', 20, 'LIBRE', 450.00, 1, 'NONE'),
                                                                                                                                         (20, 'D105', 'SIMPLE', 18, 'LIBRE', 365.00, 1, 'NONE'),
                                                                                                                                         (21, 'C203', 'SIMPLE', 18, 'LIBRE', 377.00, 1, 'NONE');

-- --------------------------------------------------------

--
-- Structure de la table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
                                          `id_student` int NOT NULL AUTO_INCREMENT,
                                          `ecole` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `telephone` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `date_entree` date NOT NULL,
    `date_sortie` date NOT NULL,
    `id_users` int NOT NULL,
    `nom` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
    `prenom` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
    `email` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id_student`),
    KEY `id_users` (`id_users`)
    ) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `students`
--

INSERT INTO `students` (`id_student`, `ecole`, `telephone`, `date_entree`, `date_sortie`, `id_users`, `nom`, `prenom`, `email`) VALUES
                                                                                                                                    (4, 'inssac', '0102030405', '2026-06-13', '2027-06-27', 2, 'yao', 'christ', 'christ@gmail.com'),
                                                                                                                                    (5, 'eseo', '0102030405', '2027-05-13', '2027-06-26', 3, 'Durand', 'Lucas', 'lucas@gmail.come'),
                                                                                                                                    (3, 'UPB', '0405060102', '2026-05-31', '2027-05-06', 1, 'koffi', 'ervé', 'koffi@gmail.com'),
                                                                                                                                    (6, 'upb', '0748006752', '2026-06-17', '2027-06-15', 4, 'yao', 'serge', 'armand@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `id_users` int NOT NULL AUTO_INCREMENT,
                                       `nom_users` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `prenom_user` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `email_user` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `mot_passe_hash` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `statut` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
    `date_creation` datetime NOT NULL,
    `id_roles` int NOT NULL,
    PRIMARY KEY (`id_users`),
    UNIQUE KEY `email_user` (`email_user`),
    KEY `id_roles` (`id_roles`)
    ) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id_users`, `nom_users`, `prenom_user`, `email_user`, `mot_passe_hash`, `statut`, `date_creation`, `id_roles`) VALUES
                                                                                                                                        (1, 'Martin', 'user', 'test@gmail.com', '$2a$10$b0/vZqXZH4.5/UT9SQx.CuiGA0dGj/Tor7p1ltpI8pXyY3WPRtCyy', 'ACTIF', '2026-05-10 06:31:50', 1),
                                                                                                                                        (2, 'Durand', 'Lucas', 'lucas@gmail.com', 'etudiant123', 'ACTIF', '2026-06-03 01:45:48', 1),
                                                                                                                                        (6, 'vernus', 'claude', 'claude@gmail.com', '$2a$12$KGeFzS9RK/HdRJhmGv5vrOP8gPX1G3zkVh5b8oy.OCedflv9GIVD.', 'ACTIF', '2026-06-04 14:32:35', 2),
                                                                                                                                        (3, 'maturain', 'clode', 'clode@gmail.com', '$2a$12$vAy3XAQjzWurDq/yd8l9dOFJRO.Mbtz5kiHR9SDK/wkZH2o5eHnJe', 'ACTIF', '2026-05-25 16:02:50', 2),
                                                                                                                                        (4, 'yao', 'christ', 'hanniel2@gmail.com', '$2a$12$FYCk2dHaocLDcg8DgCou6.iJmJzkD43gGEyrQOVfA8YS4pEZCevRO', 'ACTIF', '2026-05-25 23:40:15', 2),
                                                                                                                                        (5, 'yi', 'serge', 'serge@gmail.com', '$2a$12$47KmBriV0UhWE2aU5kKImOgg2wyJHblBYRiC9T6i8C8SK0uTfKkB.', 'ACTIF', '2026-06-03 02:13:42', 2),
                                                                                                                                        (7, 'yao', 'kouassi', 'kouassi@gmail.com', '$2a$12$4q3vb4TZo296EboiG5J31.IdLvQa2/Pf4XWMaSODOIreubC7j/sBO', 'ACTIF', '2026-06-04 14:53:41', 2),
                                                                                                                                        (8, 'kouassi', 'serge', 'kouss@gmail.com', '$2a$12$sUxIyGF9LChe/Fi0i96uKOcC/XBbTX5cCXGokT/aNSjmZFGe0iZq.', 'ACTIF', '2026-06-06 04:37:56', 2),
                                                                                                                                        (9, 'yao', 'serge', 'armand@gmail.com', '$2a$12$hiMxQM9TeEFJUU9EwpKxTu.XcpzeqY2mHbPzuJqZABYPkiY.GL.Nq', 'ACTIF', '2026-06-06 20:47:06', 2),
                                                                                                                                        (10, 'yeo', 'christ', 'yeo@gmail.com', '$2a$12$N.c143UqoHrR8oowPsRC4uMJIgRrnxGOWKpNRxDnGJ.2PT8wCcALy', 'ACTIF', '2026-06-06 23:46:12', 2),
                                                                                                                                        (11, 'merv', 'mervel', 'merv@gmail.com', '$2a$12$O/5uxAN5G7p.QUQ8NrGsR.3tFUIF6MEPl4YyLAAOVYmIKsQWOYQau', 'ACTIF', '2026-06-08 17:31:47', 2);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
