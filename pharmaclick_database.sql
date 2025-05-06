-- --------------------------------------------------------
-- Διακομιστής:                  127.0.0.1
-- Έκδοση διακομιστή:            11.7.2-MariaDB - mariadb.org binary distribution
-- Λειτ. σύστημα διακομιστή:     Win64
-- HeidiSQL Έκδοση:              12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for pharmaclick
CREATE DATABASE IF NOT EXISTS `pharmaclick` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `pharmaclick`;

-- Dumping structure for πίνακας pharmaclick.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.categories: ~6 rows (approximately)
INSERT INTO `categories` (`id`, `name`) VALUES
	(1, 'Βιταμίνες & Συμπληρώματα'),
	(2, 'Βρεφικά Προϊόντα'),
	(3, 'Δερματολογικά'),
	(4, 'Ερωτική Υγεία & Προφυλάξεις'),
	(5, 'Κρυολόγημα & Γρίπη'),
	(6, 'Αναλγητικά & Αντιφλεγμονώδη');

-- Dumping structure for πίνακας pharmaclick.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `amka` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `amka` (`amka`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.customers: ~1 rows (approximately)
INSERT INTO `customers` (`id`, `first_name`, `last_name`, `email`, `address`, `phone`, `amka`, `password`) VALUES
	(1, NULL, NULL, 'chara@gmail.com', 'patra 10', NULL, '43434', '1111');

-- Dumping structure for πίνακας pharmaclick.favorites
CREATE TABLE IF NOT EXISTS `favorites` (
  `user_id` int(11) NOT NULL,
  `medicine_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`medicine_id`),
  KEY `medicine_id` (`medicine_id`),
  CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.favorites: ~1 rows (approximately)
INSERT INTO `favorites` (`user_id`, `medicine_id`) VALUES
	(1, 3);

-- Dumping structure for πίνακας pharmaclick.medicines
CREATE TABLE IF NOT EXISTS `medicines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `form` varchar(50) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  `drug_code` varchar(100) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `pharmacy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category` (`category_id`),
  KEY `fk_pharmacy` (`pharmacy_id`),
  CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `fk_pharmacy` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.medicines: ~8 rows (approximately)
INSERT INTO `medicines` (`id`, `name`, `description`, `form`, `quantity`, `price`, `availability`, `drug_code`, `category_id`, `pharmacy_id`) VALUES
	(1, 'Vitamin C 1000mg', 'Συμπλήρωμα βιταμίνης C', 'Δισκία Αναβράζοντα', 70, 6.00, 'Διαθέσιμο', 'VITC1000', 1, 1),
	(2, 'Depon', 'Παυσίπονο και αντιπυρετικό', 'Δισκία', 100, 2.50, 'Διαθέσιμο', 'DEPON500', 6, 1),
	(3, 'Vicks', 'Σιρόπι για βήχα', 'Σιρόπι', 30, 5.50, 'Διαθέσιμο', 'VICKS100', 5, 2),
	(4, 'Daktarin', 'Αντιμυκητιασικό στόματος', 'Gel', 20, 7.20, 'Διαθέσιμο', 'DAKTARINGEL', 3, 3),
	(5, 'Algofren', 'Αντιφλεγμονώδες', 'Κάψουλες', 50, 3.90, 'Διαθέσιμο', 'ALGO200', 6, 4),
	(6, 'Panadol', 'Αναλγητικό για πονοκέφαλο', 'Δισκία', 60, 2.80, 'Διαθέσιμο', 'PANA500', 6, 5),
	(7, 'Centrum', 'Πολυβιταμίνες', 'Δισκία', 100, 9.99, 'Διαθέσιμο', 'CENTRUM', 1, 1),
	(8, 'OTRIVIN', 'Ρινικό αποσυμφορητικό', 'Σπρέι', 30, 5.20, 'Διαθέσιμο', 'OTRI15', 5, 2);

-- Dumping structure for πίνακας pharmaclick.pharmacies
CREATE TABLE IF NOT EXISTS `pharmacies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.pharmacies: ~6 rows (approximately)
INSERT INTO `pharmacies` (`id`, `name`, `address`, `phone`, `email`, `latitude`, `longitude`) VALUES
	(1, 'Φαρμακείο Υγεία', 'Αθηνάς 25, Πάτρα', '2101234567', 'info@ygeia-pharmacy.gr', 38.2466, 21.7346),
	(2, 'Φαρμακείο Κεντρικό', 'Ερμού 50, Πάτρα', '2107654321', 'info@kentriko-pharmacy.gr', 38.2485, 21.7355),
	(3, 'Φαρμακείο Life', 'Σταδίου 15, Αθήνα', '2109988776', 'info@life-pharmacy.gr', 38.247, 21.734),
	(4, 'Φαρμακείο Αθηνών', 'Ιπποκράτους 22, Αθήνα', '2101111222', 'info@ath-pharmacy.gr', 38.2491, 21.7362),
	(5, 'Φαρμακείο Πλάκα', 'Λυσίου 8, Αθήνα', '2109998877', 'info@plaka-pharmacy.gr', 38.2483, 21.7339),
	(6, 'Φαρμακείο Περιστέρι', 'Θηβών 12, Περιστέρι', '2101230000', 'info@peristeri-pharmacy.gr', 38.016, 23.685);

-- Dumping structure for πίνακας pharmaclick.pharmacists
CREATE TABLE IF NOT EXISTS `pharmacists` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `amka` varchar(20) DEFAULT NULL,
  `pharmacy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `amka` (`amka`),
  KEY `pharmacy_id` (`pharmacy_id`),
  CONSTRAINT `pharmacists_ibfk_1` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.pharmacists: ~5 rows (approximately)
INSERT INTO `pharmacists` (`id`, `full_name`, `email`, `password`, `amka`, `pharmacy_id`) VALUES
	(1, 'Νίκος Παπαδόπουλος', 'nikos.papadopoulos@gmail.com', '123456', NULL, 1),
	(2, 'Μαρία Ιωάννου', 'maria.ioannou@gmail.com', '123456', NULL, 2),
	(3, 'Γιώργος Κωνσταντίνου', 'giorgos.konstantinou@gmail.com', '123456', NULL, 3),
	(5, 'Φώτη Παπαφ', 'fotis@gmail.com', '1111', '48595489', 5),
	(6, 'Κατερίνα Νικολάου', 'katerina@gmail.com', '123456', '555999111', 6);

-- Dumping structure for πίνακας pharmaclick.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_type` enum('customer','pharmacist') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.users: ~8 rows (approximately)
INSERT INTO `users` (`id`, `email`, `password`, `user_type`) VALUES
	(1, 'email@gmail.com', '1111', 'customer'),
	(5, 'emffail@gmail.com', '1111', 'pharmacist'),
	(8, 'nikos.papadopoulos@gmail.com', '123456', 'pharmacist'),
	(9, 'maria.ioannou@gmail.com', '123456', 'pharmacist'),
	(10, 'giorgos.konstantinou@gmail.com', '123456', 'pharmacist'),
	(14, 'charoula@gmail.com', '1111', 'pharmacist'),
	(15, 'fotis@gmail.com', '1111', 'pharmacist'),
	(18, 'chara@gmail.com', '1111', 'customer');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
