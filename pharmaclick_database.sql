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

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `progress` double DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `customer_address` varchar(255) DEFAULT NULL,
  `customer_city` varchar(100) DEFAULT NULL,
  `customer_country` varchar(100) DEFAULT NULL,
  `customer_postal` varchar(10) DEFAULT NULL,
  `customer_phone` varchar(20) DEFAULT NULL,
  `pharmacy_id` int(11) DEFAULT NULL,
  `pharmacy_name` varchar(100) DEFAULT NULL,
  `pharmacy_address` varchar(255) DEFAULT NULL,
  `pharmacy_city` varchar(100) DEFAULT NULL,
  `pharmacy_country` varchar(100) DEFAULT NULL,
  `pharmacy_postal` varchar(10) DEFAULT NULL,
  `pharmacy_phone` varchar(20) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `product_description` text DEFAULT NULL,
  `product_price` decimal(10,2) DEFAULT NULL,
  `product_image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

INSERT INTO `orders` (`id`, `order_date`, `delivery_date`, `status`, `progress`, `customer_id`, `customer_name`, `customer_address`, `customer_city`, `customer_country`, `customer_postal`, `customer_phone`, `pharmacy_id`, `pharmacy_name`, `pharmacy_address`, `pharmacy_city`, `pharmacy_country`, `pharmacy_postal`, `pharmacy_phone`, `product_id`, `product_name`, `product_description`, `product_price`, `product_image_path`) VALUES
	(3, '2025-04-17', '2025-04-20', 'Ολοκληρώθηκε', 1, 1, 'Γιώργος Παπαδόπουλος', 'Οδός Ειρήνης 10', 'Πάτρα', 'Ελλάδα', '26222', '6901234567', 1, 'Φαρμακείο Παπαδημητρίου', 'Κεντρική Πλατεία 5', 'Πάτρα', 'Ελλάδα', '26222', '2610123456', 1, 'Depon 500mg', 'Αναλγητικό για πονοκεφάλους και πυρετό', 3.50, '/images/category3.png');

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `amka` varchar(255) DEFAULT NULL,
  `user_type` enum('customer','pharmacist') NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

INSERT INTO `users` (`id`, `email`, `password`, `address`, `amka`, `user_type`) VALUES
	(1, 'test@example.com', '123456', 'Athens, Greece', '12345678901', 'customer');


-- Dumping structure for πίνακας pharmaclick.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pharmacy_id` int(11) DEFAULT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `customer_address` varchar(255) DEFAULT NULL,
  `customer_phone` varchar(20) DEFAULT NULL,
  `customer_email` varchar(100) DEFAULT NULL,
  `customer_amka` varchar(20) DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `pickup_date` date DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `status` varchar(20) DEFAULT 'pending',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table pharmaclick.bookings: ~1 rows (approximately)
INSERT INTO `bookings` (`id`, `pharmacy_id`, `customer_name`, `customer_address`, `customer_phone`, `customer_email`, `customer_amka`, `comment`, `pickup_date`, `total_price`, `status`, `created_at`) VALUES
	(6, 3, 'CHARA', 'PATRA 5', '56584552', 'HARA@GMAIL.COM', '1113434', '', '2025-05-23', 7.2, 'pending', '2025-05-13 20:38:50'),
	(7, 1, 'chara', 'agio 6', '37287848', 'haral@gmail.com', '4898493', '', '2025-05-31', 30.98, 'pending', '2025-05-13 20:45:42'),
	(8, 1, 'HAJSD', 'DSJKCJHDFVN DFJ', '84384384', 'ASHHD@gmail.com', '21828', '', '2025-06-06', 18.490000000000002, 'pending', '2025-05-19 18:08:31');

-- Dumping structure for πίνακας pharmaclick.booking_items
CREATE TABLE IF NOT EXISTS `booking_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) NOT NULL,
  `medicine_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `booking_id` (`booking_id`),
  KEY `medicine_id` (`medicine_id`),
  CONSTRAINT `booking_items_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_items_ibfk_2` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.booking_items: ~6 rows (approximately)
INSERT INTO `booking_items` (`id`, `booking_id`, `medicine_id`, `quantity`) VALUES
	(1, 7, 7, 2),
	(2, 7, 2, 2),
	(3, 7, 1, 1),
	(4, 8, 1, 1),
	(5, 8, 2, 1),
	(6, 8, 7, 1);

-- Dumping structure for πίνακας pharmaclick.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_uca1400_ai_ci',
	`description` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_uca1400_ai_ci',
	`image_url` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_uca1400_ai_ci',
	`pharmacy_name` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_uca1400_ai_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_uca1400_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;

ALTER TABLE categories ADD COLUMN is_global BOOLEAN NOT NULL DEFAULT FALSE;

-- Dumping data for table pharmaclick.categories: ~6 rows (approximately)
INSERT INTO `categories` (`id`, `name`, `description`, `image_url`, `pharmacy_name`, `is_global`) VALUES
(1, 'Βιταμίνες & Συμπληρώματα', NULL, NULL, '', TRUE),
(2, 'Βρεφικά Προϊόντα', NULL, NULL, '', TRUE),
(3, 'Δερματολογικά', NULL, NULL, '', TRUE),
(4, 'Ερωτική Υγεία & Προφυλάξεις', NULL, NULL, '', TRUE),
(5, 'Κρυολόγημα & Γρίπη', NULL, NULL, '', TRUE),
(6, 'Αναλγητικά & Αντιφλεγμονώδη', NULL, NULL, '', TRUE);

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

-- Dumping data for table pharmaclick.customers: ~0 rows (approximately)
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

-- Dumping data for table pharmaclick.favorites: ~3 rows (approximately)
INSERT INTO `favorites` (`user_id`, `medicine_id`) VALUES
	(1, 1),
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

-- Dumping structure for πίνακας pharmaclick.notifications
CREATE TABLE IF NOT EXISTS `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(100) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.notifications: ~1 rows (approximately)
INSERT INTO `notifications` (`id`, `user_email`, `message`, `is_read`, `created_at`) VALUES
	(1, 'email@gmail.com', 'Η κράτησή σου καταχωρήθηκε και αναμένει επιβεβαίωση από το φαρμακείο.', 0, '2025-05-19 18:08:31');

-- Dumping structure for πίνακας pharmaclick.pharmacies
CREATE TABLE IF NOT EXISTS `pharmacies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `hours` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.pharmacies: ~7 rows (approximately)
INSERT INTO `pharmacies` (`id`, `name`, `address`, `phone`, `email`, `latitude`, `longitude`, `hours`) VALUES
	(1, 'Φαρμακείο Υγεία', 'Αθηνάς 25, Πάτρα', '2101234567', 'info@ygeia-pharmacy.gr', 38.2466, 21.7346, 'Δευ-Παρ 08:00-20:00, Σαβ 09:00-14:00'),
	(2, 'Φαρμακείο Κεντρικό', 'Ερμού 50, Πάτρα', '2107654321', 'info@kentriko-pharmacy.gr', 38.2485, 21.7355, 'Καθημερινά 09:00-21:00'),
	(3, 'Φαρμακείο Life', 'Σταδίου 15, Αθήνα', '2109988776', 'info@life-pharmacy.gr', 38.247, 21.734, 'Δευ-Σαβ 10:00-22:00'),
	(4, 'Φαρμακείο Αθηνών', 'Ιπποκράτους 22, Αθήνα', '2101111222', 'info@ath-pharmacy.gr', 38.2491, 21.7362, '24 ώρες/7 ημέρες'),
	(5, 'Φαρμακείο Πλάκα', 'Λυσίου 8, Αθήνα', '2109998877', 'info@plaka-pharmacy.gr', 38.2483, 21.7339, 'Δευ-Παρ 08:00-16:00'),
	(6, 'Φαρμακείο Περιστέρι', 'Θηβών 12, Περιστέρι', '2101230000', 'info@peristeri-pharmacy.gr', 38.016, 23.685, 'Καθημερινά 10:00-18:00'),
	(11, NULL, 'aigiouu 6', NULL, 'emailll@gmail.com', NULL, NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.pharmacists: ~6 rows (approximately)
INSERT INTO `pharmacists` (`id`, `full_name`, `email`, `password`, `amka`, `pharmacy_id`) VALUES
	(1, 'Νίκος Παπαδόπουλος', 'nikos.papadopoulos@gmail.com', '123456', NULL, 1),
	(2, 'Μαρία Ιωάννου', 'maria.ioannou@gmail.com', '123456', NULL, 2),
	(3, 'Γιώργος Κωνσταντίνου', 'giorgos.konstantinou@gmail.com', '123456', NULL, 3),
	(5, 'Φώτη Παπαφ', 'fotis@gmail.com', '1111', '48595489', 5),
	(6, 'Κατερίνα Νικολάου', 'katerina@gmail.com', '123456', '555999111', 6),
	(7, NULL, 'emailll@gmail.com', '1111', '344787', 11);

-- Dumping structure for πίνακας pharmaclick.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_type` enum('customer','pharmacist') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table pharmaclick.users: ~11 rows (approximately)
INSERT INTO `users` (`id`, `email`, `password`, `user_type`) VALUES
	(1, 'email@gmail.com', '1111', 'customer'),
	(5, 'emffail@gmail.com', '1111', 'pharmacist'),
	(8, 'nikos.papadopoulos@gmail.com', '123456', 'pharmacist'),
	(9, 'maria.ioannou@gmail.com', '123456', 'pharmacist'),
	(10, 'giorgos.konstantinou@gmail.com', '123456', 'pharmacist'),
	(14, 'charoula@gmail.com', '1111', 'pharmacist'),
	(15, 'fotis@gmail.com', '1111', 'pharmacist'),
	(18, 'chara@gmail.com', '1111', 'customer'),
	(19, 'charlaa@gmail.com', '1111', 'pharmacist'),
	(20, 'charalamp@gmail.com', '1111', 'pharmacist'),
	(21, 'emailll@gmail.com', '1111', 'pharmacist');



-- 1. Αλλάζουμε τον τύπο σε ENUM('pending','approve') με default 'pending'
ALTER TABLE `bookings` MODIFY COLUMN `status`ENUM('pending','approve') NOT NULL DEFAULT 'pending';

-- 2. (Προαιρετικά) Ενημερώνουμε τυχόν υπάρχουσες εγγραφές που δεν είναι 'pending' ή 'approve'
UPDATE `bookings`SET `status` = 'pending' WHERE `status` NOT IN ('pending','approve');
ALTER TABLE `bookings`
	CHANGE COLUMN `status` `status` ENUM('pending','approve','denied') NOT NULL DEFAULT 'pending' COLLATE 'utf8mb4_general_ci' AFTER `total_price`;

ALTER TABLE `bookings`
	CHANGE COLUMN `status` `status` ENUM('pending','approve','denied','done') NOT NULL DEFAULT 'pending' COLLATE 'utf8mb4_general_ci' AFTER `total_price`;


/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
