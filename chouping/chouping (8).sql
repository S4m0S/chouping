-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 14 avr. 2025 à 18:57
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
  PRIMARY KEY (`id_article`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id_article`, `nom`, `stock`, `description`) VALUES
(1, 'Épée en acier runique', 15, 'Épée longue à double tranchant, gravée de runes anciennes'),
(2, 'Hache de guerre naine', 10, 'Hache à une main avec poignée en cuir renforcé'),
(3, 'Arc long elfique', 8, 'Arc en bois de santal avec corde en tendon de dragon'),
(4, 'Plastron en écailles de dragon', 5, 'Armure légère faite d\'écailles forgées'),
(5, 'Cuirasse de chevalier', 7, 'Plaques d\'acier trempé articulées'),
(6, 'Tunique de mage enchantée', 12, 'Tissu imprégné de protections magiques'),
(7, 'Bottes de marcheur infatigable', 20, 'Cuir épais avec semelles en mithril'),
(8, 'Grèves de paladin', 6, 'Protections métalliques pour jambes'),
(9, 'Sandales du voleur ombreux', 9, 'Silencieuses et légères pour les déplacements furtifs'),
(10, 'Potion de soin supérieure', 50, 'Restaure 200 points de vie instantanément'),
(11, 'Elixir de rage berserker', 30, '+30% de force pendant 5 minutes'),
(12, 'Antidote royal', 25, 'Neutralise tous les poisons connus'),
(13, 'Épée en acier runique', 15, 'Épée longue à double tranchant, gravée de runes anciennes'),
(14, 'Hache de guerre naine', 10, 'Hache à une main avec poignée en cuir renforcé'),
(15, 'Arc long elfique', 8, 'Arc en bois de santal avec corde en tendon de dragon'),
(16, 'Plastron en écailles de dragon', 5, 'Armure légère faite d\'écailles forgées'),
(17, 'Cuirasse de chevalier', 7, 'Plaques d\'acier trempé articulées'),
(18, 'Tunique de mage enchantée', 12, 'Tissu imprégné de protections magiques'),
(19, 'Bottes de marcheur infatigable', 20, 'Cuir épais avec semelles en mithril'),
(20, 'Grèves de paladin', 6, 'Protections métalliques pour jambes'),
(21, 'Sandales du voleur ombreux', 9, 'Silencieuses et légères pour les déplacements furtifs'),
(22, 'Potion de soin supérieure', 50, 'Restaure 200 points de vie instantanément'),
(23, 'Elixir de rage berserker', 30, '+30% de force pendant 5 minutes'),
(24, 'Antidote royal', 25, 'Neutralise tous les poisons connus');

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
  `commentaire` int NOT NULL,
  `nb_etoile` float NOT NULL,
  `object` varchar(100) NOT NULL
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
  PRIMARY KEY (`id_article`),
  UNIQUE KEY `id_article` (`id_article`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `caracteristiques`
--

INSERT INTO `caracteristiques` (`id_article`, `prix`, `type`, `classe`, `couleur`, `taille`, `matiere`, `solidite`, `poids`) VALUES
(1, 299.99, 1, 3, 4, 1.2, 3, 5, 1.8),
(2, 299.99, 1, 3, 4, 1.2, 3, 5, 1.8),
(3, 199.99, 1, 2, 1, 0.8, 2, 4, 2.1),
(4, 349.99, 1, 4, 3, 1.5, 5, 3, 1.2),
(5, 599.99, 2, 3, 5, 2, 4, 5, 3.5),
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
(24, 59.99, 4, 0, 3, 0.1, 6, 0, 0.2);

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
  `date_naissance` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id_compte`, `nom`, `mail`, `classe`, `monnaie`, `date_naissance`) VALUES
(1, 'adal', 'ada', 1, 0, '2007-04-14');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id_commande` int NOT NULL AUTO_INCREMENT,
  `id_compte` int NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id_commande`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `commande_articles`
--

DROP TABLE IF EXISTS `commande_articles`;
CREATE TABLE IF NOT EXISTS `commande_articles` (
  `id_commande` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_commande`,`id_article`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id_compte`, `pseudo`, `mdp`, `user_type`) VALUES
(1, 'adal', 'mdp', 0);

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `description_pack`
--

INSERT INTO `description_pack` (`id_pack`, `nb_articles`, `prix`) VALUES
(1, 3, 999.99);

-- --------------------------------------------------------

--
-- Structure de la table `favoris`
--

DROP TABLE IF EXISTS `favoris`;
CREATE TABLE IF NOT EXISTS `favoris` (
  `id_client` int NOT NULL,
  `id_article` int NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `pack`
--

DROP TABLE IF EXISTS `pack`;
CREATE TABLE IF NOT EXISTS `pack` (
  `id_article` int NOT NULL,
  `id_pack` int NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pack`
--

INSERT INTO `pack` (`id_article`, `id_pack`) VALUES
(0, 1),
(0, 1),
(0, 1);

-- --------------------------------------------------------

--
-- Structure de la table `photo_article`
--

DROP TABLE IF EXISTS `photo_article`;
CREATE TABLE IF NOT EXISTS `photo_article` (
  `id_article` int NOT NULL,
  `nom` varchar(40) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `photo_article`
--

INSERT INTO `photo_article` (`id_article`, `nom`) VALUES
(0, 'epee_runique_1'),
(0, 'epee_runique_detail'),
(0, 'hache_guerre'),
(0, 'arc_elfique'),
(0, 'arc_elfique_pull'),
(0, 'plastron_dragon_front'),
(0, 'plastron_dragon_back'),
(0, 'cuirasse_chevalier'),
(0, 'bottes_marcheur'),
(0, 'greves_paladin'),
(0, 'sandales_voleur'),
(0, 'potion_soin'),
(0, 'elixir_rage'),
(0, 'antidote_royal');

-- --------------------------------------------------------

--
-- Structure de la table `photo_client`
--

DROP TABLE IF EXISTS `photo_client`;
CREATE TABLE IF NOT EXISTS `photo_client` (
  `id_client` int NOT NULL,
  `nom` varchar(40) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
