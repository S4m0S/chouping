-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 27 avr. 2025 à 20:10
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `chouping`
--

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(40) NOT NULL,
  `stock` int NOT NULL,
  `description` text NOT NULL,
  `promo` int NOT NULL DEFAULT '100',
  PRIMARY KEY (`id_article`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id_article`, `nom`, `stock`, `description`, `promo`) VALUES
(1, 'Épée en acier runique', 15, 'Épée longue à double tranchant, gravée de runes anciennes', 50),
(2, 'Hache de guerre naine', 10, 'Hache à une main avec poignée en cuir renforcé', 100),
(3, 'Arc long elfique', 8, 'Arc en bois de santal avec corde en tendon de dragon', 100),
(4, 'Plastron en écailles de dragon', 5, 'Armure légère faite d\'écailles forgées', 100),
(5, 'Cuirasse de chevalier', 7, 'Plaques d\'acier trempé articulées', 100),
(6, 'Tunique de mage enchantée', 12, 'Tissu imprégné de protections magiques', 100),
(7, 'Bottes de marcheur infatigable', 20, 'Cuir épais avec semelles en mithril', 100),
(8, 'Grèves de paladin', 6, 'Protections métalliques pour jambes', 100),
(9, 'Sandales du voleur ombreux', 9, 'Silencieuses et légères pour les déplacements furtifs', 100),
(10, 'Potion de soin supérieure', 50, 'Restaure 200 points de vie instantanément', 100),
(11, 'Elixir de rage berserker', 30, '+30% de force pendant 5 minutes', 100),
(12, 'Antidote royal', 25, 'Neutralise tous les poisons connus', 100),
(13, 'Épée en acier runique', 15, 'Épée longue à double tranchant, gravée de runes anciennes', 100),
(14, 'Hache de guerre naine', 10, 'Hache à une main avec poignée en cuir renforcé', 100),
(15, 'Arc long elfique', 8, 'Arc en bois de santal avec corde en tendon de dragon', 100),
(16, 'Plastron en écailles de dragon', 5, 'Armure légère faite d\'écailles forgées', 100),
(17, 'Cuirasse de chevalier', 7, 'Plaques d\'acier trempé articulées', 100),
(18, 'Tunique de mage enchantée', 12, 'Tissu imprégné de protections magiques', 100),
(19, 'Bottes de marcheur infatigable', 20, 'Cuir épais avec semelles en mithril', 100),
(20, 'Grèves de paladin', 6, 'Protections métalliques pour jambes', 100),
(21, 'Sandales du voleur ombreux', 9, 'Silencieuses et légères pour les déplacements furtifs', 100),
(22, 'Potion de soin supérieure', 50, 'Restaure 200 points de vie instantanément', 100),
(23, 'Elixir de rage berserker', 30, '+30% de force pendant 5 minutes', 100),
(24, 'Antidote royal', 25, 'Neutralise tous les poisons connus', 100),
(30, 'victor rouleau a vendre', 282, 'kjdnazcnjackz	', 100);

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `id_produit` int NOT NULL,
  `id_client` int NOT NULL,
  `id_commande` int NOT NULL,
  `date` date NOT NULL,
  `commentaire` text NOT NULL,
  `nb_etoile` float NOT NULL,
  `object` varchar(100) NOT NULL,
  PRIMARY KEY (`id_produit`,`id_client`,`id_commande`),
  KEY `fk_avis_client` (`id_client`),
  KEY `fk_avis_commande` (`id_commande`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `caracteristiques`
--

DROP TABLE IF EXISTS `caracteristiques`;
CREATE TABLE IF NOT EXISTS `caracteristiques` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `prix` double NOT NULL,
  `type` int NOT NULL,
  `classe` int NOT NULL,
  `couleur` int NOT NULL,
  `taille` double NOT NULL,
  `matiere` int NOT NULL,
  `solidite` int NOT NULL,
  `poids` double NOT NULL,
  UNIQUE KEY `id_article` (`id_article`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `caracteristiques`
--

INSERT INTO `caracteristiques` (`id_article`, `prix`, `type`, `classe`, `couleur`, `taille`, `matiere`, `solidite`, `poids`) VALUES
(1, 299.99, 1, 3, 4, 1.2, 3, 0, 1.8),
(2, 299.99, 1, 3, 4, 1.2, 3, 0, 1.8),
(3, 199.99, 1, 2, 1, 0.8, 2, 4, 2.1),
(4, 349.99, 1, 4, 3, 1.5, 5, 3, 1.2),
(5, 599.99, 2, 3, 5, 2, 4, 0, 3.5),
(6, 799.99, 2, 2, 2, 2.2, 3, 6, 5),
(7, 199.99, 2, 1, 3, 1.8, 1, 2, 0.8),
(8, 149.99, 3, 1, 1, 1, 2, 3, 1.2),
(9, 249.99, 3, 2, 2, 1.1, 3, 4, 2.5),
(10, 99.99, 3, 4, 4, 0.9, 1, 2, 0.5),
(11, 49.99, 4, 0, 1, 0.1, 6, 0, 0.2),
(12, 79.99, 4, 0, 2, 0.1, 6, 0, 0.2),
(13, 59.99, 4, 0, 3, 0.1, 6, 0, 0.2),
(14, 199.99, 1, 2, 1, 0.8, 2, 4, 2.1),
(15, 349.99, 1, 4, 3, 1.5, 5, 3, 1.2),
(16, 599.99, 2, 3, 5, 2, 4, 5, 3.5),
(17, 799.99, 2, 2, 2, 2.2, 3, 6, 5),
(18, 199.99, 2, 1, 3, 1.8, 1, 2, 0.8),
(19, 149.99, 3, 1, 1, 1, 2, 3, 1.2),
(20, 249.99, 3, 2, 2, 1.1, 3, 4, 2.5),
(21, 99.99, 3, 4, 4, 0.9, 1, 2, 0.5),
(22, 49.99, 4, 0, 1, 0.1, 6, 0, 0.2),
(23, 79.99, 4, 0, 2, 0.1, 6, 0, 0.2),
(24, 59.99, 4, 0, 3, 0.1, 6, 0, 0.2),
(29, 1111, 3, 2, 3, 111, 3, 2, 24),
(30, 11, 2, 2, 2, 12, 2, 3, 29992929299);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_compte` int NOT NULL,
  `nom` varchar(50) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `classe` int NOT NULL,
  `monnaie` double NOT NULL,
  `date_naissance` date NOT NULL,
  PRIMARY KEY (`id_compte`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id_compte`, `nom`, `mail`, `classe`, `monnaie`, `date_naissance`) VALUES
(1, 'adal', 'ada', 1, 0, '2007-04-14'),
(2, 'ada', 'adam', 1, 0, '2007-04-14'),
(3, 'Adam', 'adamnasreddine@outlook.com', 3, 2.5, '2007-04-15'),
(10, 'op', 'op', 1, 0, '2007-04-27'),
(9, 'Superop', 'superop', 1, 0, '2007-04-27'),
(12, 'ehoh', 'ehoh', 1, 0, '2007-04-27');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id_commande` int NOT NULL AUTO_INCREMENT,
  `id_compte` int NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id_commande`),
  KEY `fk_commande_compte` (`id_compte`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `id_compte`, `date`) VALUES
(2, 2, '2025-04-15'),
(3, 2, '2025-04-18'),
(4, 2, '2025-04-25'),
(5, 2, '2025-04-27');

-- --------------------------------------------------------

--
-- Structure de la table `commande_articles`
--

DROP TABLE IF EXISTS `commande_articles`;
CREATE TABLE IF NOT EXISTS `commande_articles` (
  `id_commande` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int NOT NULL DEFAULT '1',
  KEY `fk_commande_articles_commande` (`id_commande`),
  KEY `fk_commande_articles_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande_articles`
--

INSERT INTO `commande_articles` (`id_commande`, `id_article`, `quantite`) VALUES
(2, 2, 4),
(3, 15, 4),
(3, 23, 6),
(4, 30, 11),
(5, 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte` (
  `id_compte` int NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(50) NOT NULL,
  `mdp` varchar(50) NOT NULL,
  `user_type` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_compte`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id_compte`, `pseudo`, `mdp`, `user_type`) VALUES
(1, 'adal', 'mdp', 0),
(2, 'ada', 'mdp', 0),
(3, 'Adam', 'mdp', 0),
(4, 'adam', 'nas', 1),
(5, 'julien', 'hgm', 1),
(6, 'moha', 'lasqualle', 1),
(8, 'adam', 'mdp', 0),
(9, 'superop', 'mdp', 0),
(10, 'op', 'mdp', 0),
(11, 'Superop', 'mdp', 0),
(12, 'ehoh', 'ehoh', 0);

-- --------------------------------------------------------

--
-- Structure de la table `description_pack`
--

DROP TABLE IF EXISTS `description_pack`;
CREATE TABLE IF NOT EXISTS `description_pack` (
  `id_pack` int NOT NULL AUTO_INCREMENT,
  `nb_articles` int NOT NULL,
  `prix` double NOT NULL,
  PRIMARY KEY (`id_pack`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `description_pack`
--

INSERT INTO `description_pack` (`id_pack`, `nb_articles`, `prix`) VALUES
(1, 3, 999.99),
(2, 10, 233);

-- --------------------------------------------------------

--
-- Structure de la table `favoris`
--

DROP TABLE IF EXISTS `favoris`;
CREATE TABLE IF NOT EXISTS `favoris` (
  `id_client` int NOT NULL,
  `id_article` int NOT NULL,
  PRIMARY KEY (`id_client`,`id_article`),
  KEY `fk_favoris_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `pack`
--

DROP TABLE IF EXISTS `pack`;
CREATE TABLE IF NOT EXISTS `pack` (
  `id_article` int NOT NULL,
  `id_pack` int NOT NULL,
  PRIMARY KEY (`id_article`,`id_pack`),
  KEY `fk_pack_description` (`id_pack`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pack`
--

INSERT INTO `pack` (`id_article`, `id_pack`) VALUES
(7, 2),
(8, 2);

-- --------------------------------------------------------

--
-- Structure de la table `photo_article`
--

DROP TABLE IF EXISTS `photo_article`;
CREATE TABLE IF NOT EXISTS `photo_article` (
  `id_article` int NOT NULL,
  `nom` varchar(40) NOT NULL,
  PRIMARY KEY (`id_article`,`nom`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `photo_article`
--

INSERT INTO `photo_article` (`id_article`, `nom`) VALUES
(1, 'epee_runique_1'),
(1, 'epee_runique_detail'),
(2, 'hache_guerre'),
(3, 'arc_elfique'),
(3, 'arc_elfique_pull'),
(4, 'plastron_dragon_back'),
(4, 'plastron_dragon_front'),
(5, 'cuirasse_chevalier'),
(7, 'bottes_marcheur'),
(8, 'greves_paladin'),
(9, 'sandales_voleur'),
(10, 'potion_soin'),
(11, 'elixir_rage'),
(12, 'antidote_royal');

-- --------------------------------------------------------

--
-- Structure de la table `photo_client`
--

DROP TABLE IF EXISTS `photo_client`;
CREATE TABLE IF NOT EXISTS `photo_client` (
  `id_client` int NOT NULL,
  `nom` varchar(40) NOT NULL,
  PRIMARY KEY (`id_client`,`nom`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
