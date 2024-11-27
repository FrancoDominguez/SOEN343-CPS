-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: franco-db.czes8i20a6iw.us-east-1.rds.amazonaws.com    Database: cps-db
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `client_id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `home_address` int DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_home_address` (`home_address`),
  CONSTRAINT `fk_home_address` FOREIGN KEY (`home_address`) REFERENCES `locations` (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Franco','Dominguez','hoboslime@gmail.com','supersafe',NULL),(2,'Mark','Ghaby','markghaby@gmail.com','supersafe',NULL),(6,'Antoine','Mansour','antoine.rmcr77@gmail.com','supersafe',NULL),(28,'mark','ghaby','markg@gmail.com','123',NULL),(29,'john','doe','johndoe@gmail.com','123321',NULL),(30,'mark ','ghaby','mark@email.com','123321',NULL),(31,'final','test','final@test.com','123321',NULL),(32,'finafinal','test','test@final.com','123321',NULL),(33,'Borat','Elmansouri','franco.dominguez343@gmail.com','supersafe',NULL),(34,'sign','up','signup@gmail.com','123321',NULL),(35,'Jhon','Doe','doejohn@gmail.com','123321',NULL);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracts` (
  `contract_id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `eta` time NOT NULL,
  `signature_required` tinyint(1) NOT NULL,
  `priority_shipping` tinyint(1) NOT NULL,
  `warranted_amount` decimal(10,2) NOT NULL,
  `parcel_id` int NOT NULL,
  `destination_id` int NOT NULL,
  `origin_station_id` int DEFAULT NULL,
  `origin_location_id` int DEFAULT NULL,
  `pickupTime` datetime DEFAULT NULL,
  `isFlexible` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`contract_id`),
  KEY `client_id` (`client_id`),
  KEY `parcel_id` (`parcel_id`),
  KEY `destination_id` (`destination_id`),
  KEY `origin_station_id` (`origin_station_id`),
  KEY `origin_location_id` (`origin_location_id`),
  CONSTRAINT `contracts_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`),
  CONSTRAINT `contracts_ibfk_2` FOREIGN KEY (`parcel_id`) REFERENCES `parcels` (`parcel_id`),
  CONSTRAINT `contracts_ibfk_3` FOREIGN KEY (`destination_id`) REFERENCES `locations` (`location_id`),
  CONSTRAINT `contracts_ibfk_4` FOREIGN KEY (`origin_station_id`) REFERENCES `stations` (`station_id`),
  CONSTRAINT `contracts_ibfk_5` FOREIGN KEY (`origin_location_id`) REFERENCES `locations` (`location_id`),
  CONSTRAINT `chk_origin_exclusivity` CHECK ((((`origin_location_id` is not null) and (`origin_station_id` is null) and (`pickupTime` is not null) and (`isFlexible` is not null)) or ((`origin_location_id` is null) and (`origin_station_id` is not null) and (`pickupTime` is null) and (`isFlexible` is null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dropoff_locations`
--

DROP TABLE IF EXISTS `dropoff_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dropoff_locations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dropoff_locations`
--

LOCK TABLES `dropoff_locations` WRITE;
/*!40000 ALTER TABLE `dropoff_locations` DISABLE KEYS */;
INSERT INTO `dropoff_locations` VALUES (1,'Canadiens Plaza','1909 Av. des Canadiens-de-Montréal, Montréal, QC H3B 5E8'),(2,'William St Dropoff','2020 William St, Montreal, Quebec H3J 1R8'),(3,'Saint-Catherine Center','2313 Saint-Catherine St W #101, Montreal, Quebec H3H 1N2'),(4,'Westmount Park Place','15 Park Pl, Westmount, Quebec H3Z 2K4'),(5,'Saint-Antoine Station','4625 Rue Saint-Antoine O, Montréal, QC H4C 1E2'),(6,'Sherbrooke Stop','5716 Sherbrooke St W, Montreal, Quebec H4A 1W8'),(7,'West Hill Drop','4985 W Hill Ave, Montreal, Quebec H4V 2W6'),(8,'Cavendish Corner','5755 Cavendish Blvd, Côte Saint-Luc, Quebec H4W 2X8'),(9,'Liesse Mountain Depot','477 Mnt de Liesse, Saint-Laurent, QC H4T 1P5'),(10,'Dobrin Hub','4705 Rue Dobrin, Saint-Laurent, QC H4R 2P7'),(11,'St Regis Depot','1611 St Regis Blvd, Dollard-Des Ormeaux, Quebec H9B 3H7'),(12,'Sources South Drop','1866-A Sources Blvd, Pointe-Claire, Quebec H9R 5B1'),(13,'Trans-Canada Point','5701 Trans-Canada Hwy, Pointe-Claire, Quebec H9R 1B7'),(14,'Roxboro Sources Stop','4463 Sources Blvd, Roxboro, Quebec H8Y 3C1'),(15,'Pierrefonds Plaza','11851 Pierrefonds Blvd., Pierrefonds, Quebec H9A 1A1'),(16,'Pierrefonds West End','13555 Pierrefonds Blvd., Pierrefonds, Quebec H9A 1A6'),(17,'Saint-Charles Hub','3908 Saint-Charles Blvd, Pierrefonds-Roxboro, Quebec H9H 3C6'),(18,'Jean Yves Junction','3140 Jean Yves St, Kirkland, Quebec H9J 2R6'),(19,'Lakeshore Station','21 275 Rue Lakeshore Road, Sainte-Anne-de-Bellevue, QC H9X 3L9'),(20,'Cardinal Léger Corner','92 Bd Cardinal Léger, Pincourt, QC J7V 3Y4'),(21,'Laurentien Central','11847 Blvd. Laurentien, Montreal, Quebec H4J 2M1'),(22,'Pie-IX Depot','9204 Pie-IX Blvd, Montreal, Quebec H1Z 4H7'),(23,'Bélanger Base','5000 Rue Bélanger, Montréal, QC H1T 1C8'),(24,'Sherbrooke East Hub','7275 Sherbrooke St E, Montreal, Quebec H1N 1E9'),(25,'Sciences Boulevard Dropoff','9401 Bd des Sciences, Anjou, QC H1J 0A6'),(26,'Pointe-aux-Trembles East','12585 Sherbrooke St E, Pointe-aux-Trembles, Quebec H1B 1C8'),(27,'Riviere-des-Prairies Station','15200 Sherbrooke St E, Riviere-des-Prairies—Pointe-aux-Trembles, Quebec H1A 3P9'),(28,'La Hontan Depot','12555 Rue La Hontan, Montréal, QC H1C 2L2'),(29,'Claude-Gagné Base','1950 Rue Claude-Gagné, Laval, QC H7N 0E4'),(30,'Laurentian Stop','2150 Laurentian Autoroute, Laval, Quebec H7T 2T8'),(31,'Chomedey West End','1050 Autoroute Chomedey Ouest, Laval, Quebec H7X 4C9'),(32,'Dagenais West Hub','4341 Bd Dagenais O, Laval, QC H7R 1L3'),(33,'Riviera Point','2090-2090 Rue Riviera, Laval, QC'),(34,'Ambassadeurs Avenue Stop','3330 Av. des Ambassadeurs, Laval, QC H7E 5K7'),(35,'Moulin Summit','600 Mnt du Moulin, Laval, QC H7A 3B7'),(36,'Chambly Route','2655 Ch. de Chambly, Longueuil, QC J4L 1M3'),(37,'Taschereau Center','3398 Taschereau Blvd, Greenfield Park, Quebec J4V 2H7'),(38,'Roberval Depot','1402 Rue Roberval, Saint-Bruno-de-Montarville, QC J3V 5J2'),(39,'Brien Plaza','85 Boul. Brien Local 101, Repentigny, Quebec J6A 8B6'),(40,'Maple Boulevard Stop','161 Bd Maple, Châteauguay, QC J6J 3R1'),(41,'Georges-Gagné South Hub','31 Boulevard Georges-Gagné S, Delson, QC J5B 2E4');
/*!40000 ALTER TABLE `dropoff_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `location_id` int NOT NULL AUTO_INCREMENT,
  `street_address` varchar(255) NOT NULL,
  `postal_code` varchar(50) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcels`
--

DROP TABLE IF EXISTS `parcels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parcels` (
  `parcel_id` int NOT NULL AUTO_INCREMENT,
  `height` decimal(10,2) NOT NULL,
  `width` decimal(10,2) NOT NULL,
  `length` decimal(10,2) NOT NULL,
  `weight` decimal(10,2) NOT NULL,
  `is_fragile` tinyint(1) NOT NULL,
  PRIMARY KEY (`parcel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcels`
--

LOCK TABLES `parcels` WRITE;
/*!40000 ALTER TABLE `parcels` DISABLE KEYS */;
/*!40000 ALTER TABLE `parcels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `station_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  PRIMARY KEY (`station_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-18 16:56:16
