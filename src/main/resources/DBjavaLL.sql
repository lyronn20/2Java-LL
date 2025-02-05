-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 05 fév. 2025 à 16:23
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `dbjavall`
--

-- --------------------------------------------------------

--
-- Structure de la table `inventories`
--

DROP TABLE IF EXISTS `inventories`;
CREATE TABLE IF NOT EXISTS `inventories` (
                                             `id` int NOT NULL AUTO_INCREMENT,
                                             `name` text NOT NULL,
                                             `quantity` int NOT NULL,
                                             `price` int NOT NULL,
                                             `store_id` int NOT NULL,
                                             PRIMARY KEY (`id`),
    UNIQUE KEY `store_id` (`store_id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `inventories`
--

INSERT INTO `inventories` (`id`, `name`, `quantity`, `price`, `store_id`) VALUES
                                                                              (5, 'leolechien', 22, 100, 1),
                                                                              (8, 'leon', 100, 1000000000, 2);

-- --------------------------------------------------------

--
-- Structure de la table `stores`
--

DROP TABLE IF EXISTS `stores`;
CREATE TABLE IF NOT EXISTS `stores` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `name` varchar(100) NOT NULL,
    `location` text NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
    ) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `stores`
--

INSERT INTO `stores` (`id`, `name`, `location`) VALUES
                                                    (1, 'Magasin Central', ''),
                                                    (2, 'Dépôt Sud', '');

-- --------------------------------------------------------

--
-- Structure de la table `store_users`
--

DROP TABLE IF EXISTS `store_users`;
CREATE TABLE IF NOT EXISTS `store_users` (
                                             `user_id` int NOT NULL,
                                             `store_id` int NOT NULL,
                                             PRIMARY KEY (`user_id`,`store_id`),
    KEY `store_id` (`store_id`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `store_users`
--

INSERT INTO `store_users` (`user_id`, `store_id`) VALUES
                                                      (2, 1),
                                                      (8, 1),
                                                      (27, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `email` varchar(191) NOT NULL,
    `pseudo` varchar(50) NOT NULL,
    `password` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `role` enum('EMPLOYE','ADMIN') NOT NULL DEFAULT 'EMPLOYE',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
    ) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `pseudo`, `password`, `role`) VALUES
                                                                      (1, 'admin@istore.com', 'Admin', 'hashed_password_admin', 'ADMIN'),
                                                                      (2, 'employee1@istore.com', 'JohnDoe', 'hashed_password_emp1', 'EMPLOYE'),
                                                                      (8, 'employee2@istore.com', 'TestUser', '$2a$10$13fwGFipm8exqOjX2WI82uBzR8Z8SU/cAlvpH/7R8euPAlFL49waS', 'EMPLOYE'),
                                                                      (4, 'test@store.com', 'test', 'Baltrou14000$', 'ADMIN'),
                                                                      (24, 'Lyronn2012@gmail.com', 'Lyronn', '$2a$10$RvijyJnidtxSQKnAb33ORujSkH0.1b2X7g3beWoO4bWDaUHCPPkc2', 'ADMIN'),
                                                                      (25, 'Test2con@gmail.com', 'Lyy', '$2a$10$ygkhyZUnWResaQ3ME1wFuuPJWRvelookIEyAaYfFl.AK378DY3xKi', 'ADMIN'),
                                                                      (26, 'Testemploye@gmail.com', 'EM', '$2a$10$qlT0rcjwkUbCaVbH6gmr1.f3K5vsdSGEq65UIn8RmyG.NLbqneN1.', 'EMPLOYE'),
                                                                      (27, 'Leo@gmail.com', 'LLLLLLLL', '$2a$10$8SKPMLvEcIavEO5PcNLK5eBbz9ozWkRYd4QltRA64ibA1WzUMvpJG', 'EMPLOYE');

-- --------------------------------------------------------

--
-- Structure de la table `whitelist`
--

DROP TABLE IF EXISTS `whitelist`;
CREATE TABLE IF NOT EXISTS `whitelist` (
                                           `email` varchar(191) NOT NULL,
    PRIMARY KEY (`email`),
    UNIQUE KEY `email` (`email`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `whitelist`
--

INSERT INTO `whitelist` (`email`) VALUES
                                      ('admin@istore.com'),
                                      ('employee1@istore.com'),
                                      ('employee2@istore.com'),
                                      ('Leo@gmail.com'),
                                      ('Lyronn2012@gmail.com'),
                                      ('Test2con@gmail.com'),
                                      ('Testemploye@gmail.com');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
