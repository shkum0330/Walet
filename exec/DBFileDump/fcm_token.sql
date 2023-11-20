-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: a301-rds.cz4ggd8ra6pu.ap-northeast-2.rds.amazonaws.com    Database: allforyou
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.15-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `fcm_token`
--

DROP TABLE IF EXISTS `fcm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fcm_token` (
  `fcm_id` bigint(20) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `member_id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  PRIMARY KEY (`fcm_id`),
  KEY `fcm_member_id` (`member_id`),
  CONSTRAINT `fcm_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcm_token`
--

LOCK TABLES `fcm_token` WRITE;
/*!40000 ALTER TABLE `fcm_token` DISABLE KEYS */;
INSERT INTO `fcm_token` VALUES (4001,'2023-11-17 00:51:40.732276','2023-11-17 00:51:40.732276',2,'fYUK4pvfT4OST9iD3jDPRP:APA91bHTlqH3qANRncJoxK0ubW6H0j4oNbgX4ZKVtqmkxWfe-N90cUOkpPLJvifutQAgFz9ajtxPyhJ8s6waE7SbZHWQnjriPy0oaFx45g4i6iFmFNNhxcDzK-0ao1lhNdDt4wNI9oOQ'),(4002,'2023-11-17 00:57:18.049927','2023-11-17 00:57:18.049927',4,'fYUK4pvfT4OST9iD3jDPRP:APA91bHTlqH3qANRncJoxK0ubW6H0j4oNbgX4ZKVtqmkxWfe-N90cUOkpPLJvifutQAgFz9ajtxPyhJ8s6waE7SbZHWQnjriPy0oaFx45g4i6iFmFNNhxcDzK-0ao1lhNdDt4wNI9oOQ'),(4003,'2023-11-17 00:59:54.447432','2023-11-17 00:59:54.447432',3,'fYUK4pvfT4OST9iD3jDPRP:APA91bHTlqH3qANRncJoxK0ubW6H0j4oNbgX4ZKVtqmkxWfe-N90cUOkpPLJvifutQAgFz9ajtxPyhJ8s6waE7SbZHWQnjriPy0oaFx45g4i6iFmFNNhxcDzK-0ao1lhNdDt4wNI9oOQ'),(4004,'2023-11-17 01:15:53.778702','2023-11-17 01:15:53.778702',1,'dUVhsSItQe6xv81Ve2G-Qr:APA91bHyD0mdz76WAVZ-1GVFcWYu00iZvvOhJPiWnxhgs0MYQ4lsoQoS0s6QfYbnwDLRxbsNoxOSX1J4XpImCtgvk5AiJcsGdV9QiZp7pKZEhxCrJS52xrZsZv7zae7YumIYaiQllMyz');
/*!40000 ALTER TABLE `fcm_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:11:01
