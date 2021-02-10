-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 28 jan. 2021 à 11:12
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gc`
--

-- --------------------------------------------------------

--
-- Structure de la table `factures`
--

CREATE TABLE `factures` (
  `id` int(11) NOT NULL,
  `medecin` varchar(255) DEFAULT NULL,
  `medecin_id` int(11) NOT NULL,
  `medicament` varchar(255) DEFAULT NULL,
  `medicament_id` int(11) NOT NULL,
  `patient` varchar(255) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `traitement_id` int(11) NOT NULL,
  `date_ajout` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `prix` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `factures`
--

INSERT INTO `factures` (`id`, `medecin`, `medecin_id`, `medicament`, `medicament_id`, `patient`, `patient_id`, `traitement_id`, `date_ajout`, `date_update`, `prix`) VALUES
(2, 'Medecin2 Medecin2', 3, 'Doliprane 500mg', 1, 'Patient2 Patient2', 2, 2, '2021-01-25 03:16:51', '2021-01-25 03:16:51', '132'),
(3, 'Medecin1 Medecin1', 2, 'Doliprane 500mg', 1, 'Patient1 Patient1', 1, 5, '2021-01-27 23:29:11', '2021-01-27 23:29:11', '200dh'),
(4, 'Medecin3 Medecin3', 4, 'Doliprane 500mg', 1, 'Patient3 Patient3', 3, 3, '2021-01-28 09:49:38', '2021-01-28 09:49:38', '200DH');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(4);

-- --------------------------------------------------------

--
-- Structure de la table `medicaments`
--

CREATE TABLE `medicaments` (
  `id` int(11) NOT NULL,
  `caract` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantite` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `medicaments`
--

INSERT INTO `medicaments` (`id`, `caract`, `name`, `quantite`) VALUES
(1, 'Tete', 'Doliprane', '500mg'),
(2, 'Tete', 'Dol', '1g');

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

CREATE TABLE `patient` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `cin` varchar(255) DEFAULT NULL,
  `date_ajout` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `docteur_id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `maladie` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`id`, `address`, `age`, `cin`, `date_ajout`, `date_update`, `docteur_id`, `email`, `genre`, `maladie`, `nom`, `prenom`, `tel`) VALUES
(1, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 23, 'M9899', '2021-01-17 20:31:50', '2021-01-17 20:31:50', 2, 'patient1@gmail.com', 'homme', 'Tete', 'Patient1', 'Patient1', '+212648631936'),
(2, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 21, 'L2314', '2021-01-17 20:32:17', '2021-01-17 20:32:17', 3, 'patient2@gmail.com', 'femme', 'Main', 'Patient2', 'Patient2', '+212648631936'),
(3, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 32, 'G9808', '2021-01-17 20:32:49', '2021-01-17 20:32:49', 6, 'patient3@gmail.com', 'homme', 'Dois', 'Patient3', 'Patient3', '+212648631936'),
(6, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 25, 'L2314', '2021-01-28 09:47:25', '2021-01-28 09:47:25', 3, 'patient4@gmail.com', 'homme', 'Tete', 'Patient4', 'Patient4', '+212648631936');

-- --------------------------------------------------------

--
-- Structure de la table `rendez_vous`
--

CREATE TABLE `rendez_vous` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `date_ajout` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `medecin` varchar(255) DEFAULT NULL,
  `medecin_id` int(11) NOT NULL,
  `patient` varchar(255) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `time` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `rendez_vous`
--

INSERT INTO `rendez_vous` (`id`, `date`, `date_ajout`, `date_update`, `medecin`, `medecin_id`, `patient`, `patient_id`, `time`) VALUES
(3, '2021-01-19', '2021-01-25 02:27:49', '2021-01-25 02:27:49', 'Medecin1 Medecin1', 2, 'Patient1 Patient1', 1, '12:20'),
(5, '2021-01-31', '2021-01-28 09:48:24', '2021-01-28 09:48:24', 'Medecin2 Medecin2', 3, 'Patient4 Patient4', 6, '13:00');

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`role_id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MEDECIN'),
(3, 'ROLE_GP');

-- --------------------------------------------------------

--
-- Structure de la table `scanners`
--

CREATE TABLE `scanners` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `patient` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `scanners`
--

INSERT INTO `scanners` (`id`, `date`, `des`, `nom`, `patient_id`, `patient`) VALUES
(2, '2021-01-17', 'Description 1', 'Scanner 1', 1, 'Patient1 Patient1'),
(3, '2021-01-17', 'Description 2', 'Scanner 2', 2, 'Patient2 Patient2'),
(4, '2021-01-17', 'Description 3', 'Scanner 3', 3, 'Patient3 Patient3'),
(5, '2021-01-07', 'Scanner 4 ', 'Scanner 4', 2, 'Patient2 Patient2');

-- --------------------------------------------------------

--
-- Structure de la table `traitements`
--

CREATE TABLE `traitements` (
  `id` int(11) NOT NULL,
  `medicament_id` int(11) NOT NULL,
  `periode` varchar(255) DEFAULT NULL,
  `medecin_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `medecin` varchar(255) DEFAULT NULL,
  `medicament` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `patient` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `traitements`
--

INSERT INTO `traitements` (`id`, `medicament_id`, `periode`, `medecin_id`, `patient_id`, `medecin`, `medicament`, `nombre`, `patient`) VALUES
(2, 1, '2mois', 3, 2, 'Medecin2 Medecin2', 'Doliprane', '3fois/Jour', 'Patient2 Patient2'),
(3, 1, '3mois', 4, 3, 'Medecin3 Medecin3', 'Doliprane', '3fois/Jour', 'Patient3 Patient3'),
(5, 1, '1mois', 2, 1, 'Medecin1 Medecin1', 'Doliprane', '3fois/Jour', 'Patient1 Patient1'),
(9, 1, '1mois', 2, 2, 'Medecin1 Medecin1', 'Doliprane', '3fois/Jour', 'Patient2 Patient2');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cin` varchar(255) DEFAULT NULL,
  `date_ajout` datetime DEFAULT NULL,
  `date_naiss` date DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `address`, `cin`, `date_ajout`, `date_naiss`, `date_update`, `email`, `enabled`, `genre`, `nom`, `password`, `prenom`, `tel`, `username`, `role_id`) VALUES
(1, 'AV Joulan, WILAYA, Tetouan', 'L98932', '2021-01-17 21:12:04', '1998-12-23', '2021-01-17 21:12:04', 'ali.sebaoui1@gmail.com', b'1', 'homme', 'Sebaoui', '$2a$10$XUI.h5dEbR9poOekOqEhiO0cyd1kLuderBYfFkJOH4ROqo88gu2s6', 'Ali', '+212648631936', 'admin', 1),
(2, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 'O0432', '2021-01-17 20:25:27', '1998-01-23', '2021-01-17 20:25:27', 'medecin1@gmail.com', b'1', 'homme', 'Medecin1', '$2a$10$xxdEo9OA16ZgJiJVPd.V1uZ93eoagjRHUtIVYIDXUNYVsRJnKzd.O', 'Medecin1', '+212648631936', 'Umedecin1', 2),
(3, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 'L2314', '2021-01-17 20:30:46', '1998-01-23', '2021-01-17 20:30:46', 'medecin2@gmail.com', b'1', 'femme', 'Medecin2', '$2a$10$XxUOq5VtsJnpkLeWd1JsGOlTnowvDsiwzwoMx1WAgGi.BXCJhRrsi', 'Medecin2', '+212648631936', 'Umedecin2', 2),
(5, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 'P23132', '2021-01-18 21:21:47', '1998-01-23', '2021-01-18 21:21:47', 'gp1@gmail.com', b'1', 'homme', 'GP1', '$2a$10$us0vz6Wi0WQ9rOXzp6EGSu.g5WdudZdAmHNp04ASFyGLdVU0xIKQa', 'GP1', '+212648631936', 'Ugp1', 3),
(6, 'AV JOULAN RESIDENCE SAADA ETG 3 N 8 TETOUAN', 'L2314', '2021-01-25 00:55:31', '2021-01-31', '2021-01-25 00:55:31', 'medecin3@gmail.com', b'1', 'homme', 'Medecin3', '$2a$10$.LWV7GRVVetCN9TdbE.x/OI16Lsq9NsFTnZFsvw9LHQVRmhpJzlk6', 'Medecin3', '+212648631936', 'Umedecin3', 2);

-- --------------------------------------------------------

--
-- Structure de la table `users_roles`
--

CREATE TABLE `users_roles` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users_roles`
--

INSERT INTO `users_roles` (`role_id`, `user_id`) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 5),
(2, 6);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `factures`
--
ALTER TABLE `factures`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `medicaments`
--
ALTER TABLE `medicaments`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Index pour la table `scanners`
--
ALTER TABLE `scanners`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `traitements`
--
ALTER TABLE `traitements`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `factures`
--
ALTER TABLE `factures`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `medicaments`
--
ALTER TABLE `medicaments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `scanners`
--
ALTER TABLE `scanners`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `traitements`
--
ALTER TABLE `traitements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `users_roles`
--
ALTER TABLE `users_roles`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
