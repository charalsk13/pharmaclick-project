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

-- Dumping structure for πίνακας pharmaclick.medicines
CREATE TABLE IF NOT EXISTS `medicines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pharmacy_name` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `form` varchar(50) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  `drug_code` varchar(100) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category` (`category_id`),
  CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.medicines: ~4 rows (approximately)
INSERT INTO `medicines` (`id`, `pharmacy_name`, `name`, `description`, `form`, `quantity`, `price`, `availability`, `drug_code`, `category_id`, `category`) VALUES
	(1, 'Φαρμακείο Υγεία', 'Vitamin C 1000mg', 'Συμπλήρωμα βιταμίνης C', 'Δισκία Αναβράζοντα', 70, 6.00, 'Διαθέσιμο', 'VITC1000', 1, NULL),
	(2, 'Φαρμακείο Υγεία', 'Depon', 'Παυσίπονο και αντιπυρετικό', 'Δισκία', 100, 2.50, 'Διαθέσιμο', 'DEPON500', 6, NULL),
	(3, 'Φαρμακείο Κεντρικό', 'Vicks', 'Σιρόπι για βήχα', 'Σιρόπι', 30, 5.50, 'Διαθέσιμο', 'VICKS100', 5, NULL),
	(4, 'Φαρμακείο Life', 'Daktarin', 'Αντιμυκητιασικό στόματος', 'Gel', 20, 7.20, 'Διαθέσιμο', 'DAKTARINGEL', 3, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.pharmacies: ~3 rows (approximately)
INSERT INTO `pharmacies` (`id`, `name`, `address`, `phone`, `email`, `latitude`, `longitude`) VALUES
	(1, 'Φαρμακείο Υγεία', 'Αθηνάς 25, Αθήνα', '2101234567', 'info@ygeia-pharmacy.gr', 38.2466, 21.7346),
	(2, 'Φαρμακείο Κεντρικό', 'Ερμού 50, Αθήνα', '2107654321', 'info@kentriko-pharmacy.gr', 38.2485, 21.7355),
	(3, 'Φαρμακείο Life', 'Σταδίου 15, Αθήνα', '2109988776', 'info@life-pharmacy.gr', 38.247, 21.734);

-- Dumping structure for πίνακας pharmaclick.pharmacists
CREATE TABLE IF NOT EXISTS `pharmacists` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `amka` varchar(20) DEFAULT NULL,
  `pharmacy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `amka` (`amka`),
  KEY `pharmacy_id` (`pharmacy_id`),
  CONSTRAINT `pharmacists_ibfk_1` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.pharmacists: ~5 rows (approximately)
INSERT INTO `pharmacists` (`id`, `full_name`, `email`, `phone`, `password`, `address`, `amka`, `pharmacy_id`) VALUES
	(1, 'Νίκος Παπαδόπουλος', 'nikos.papadopoulos@gmail.com', '6901234567', '123456', 'Πανεπιστημίου 40, Αθήνα', NULL, 1),
	(2, 'Μαρία Ιωάννου', 'maria.ioannou@gmail.com', '6907654321', '123456', 'Σόλωνος 100, Αθήνα', NULL, 2),
	(3, 'Γιώργος Κωνσταντίνου', 'giorgos.konstantinou@gmail.com', '6988887777', '123456', 'Ακαδημίας 20, Αθήνα', NULL, 3),
	(4, NULL, 'charoula@gmail.com', NULL, '1111', 'pente 2', '48493892', NULL),
	(5, NULL, 'fotis@gmail.com', NULL, '1111', 'deka 10', '48595489', NULL);

-- Dumping structure for πίνακας pharmaclick.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `amka` varchar(255) DEFAULT NULL,
  `user_type` enum('customer','pharmacist') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.users: ~8 rows (approximately)
INSERT INTO `users` (`id`, `email`, `password`, `address`, `amka`, `user_type`) VALUES
	(1, 'email@gmail.com', '1111', 'Aigio 1', '100101', 'customer'),
	(5, 'emffail@gmail.com', '1111', 'pame', '4838394', 'pharmacist'),
	(8, 'nikos.papadopoulos@gmail.com', '123456', 'Πανεπιστημίου 40, Αθήνα', '12345678901', 'pharmacist'),
	(9, 'maria.ioannou@gmail.com', '123456', 'Σόλωνος 100, Αθήνα', '10987654321', 'pharmacist'),
	(10, 'giorgos.konstantinou@gmail.com', '123456', 'Ακαδημίας 20, Αθήνα', '11223344556', 'pharmacist'),
	(14, 'charoula@gmail.com', '1111', 'pente 2', '48493892', 'pharmacist'),
	(15, 'fotis@gmail.com', '1111', 'deka 10', '48595489', 'pharmacist'),
	(18, 'chara@gmail.com', '1111', 'patra 10', '43434', 'customer');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
