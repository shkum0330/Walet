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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_name` varchar(20) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  `account_password` varchar(64) NOT NULL,
  `account_type` varchar(10) NOT NULL,
  `balance` bigint(20) NOT NULL,
  `business_type` int(11) DEFAULT NULL,
  `depositor_name` varchar(20) NOT NULL,
  `limit_types` int(11) DEFAULT NULL,
  `linked_account_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `pet_birth` date DEFAULT NULL,
  `pet_breed` varchar(30) DEFAULT NULL,
  `pet_gender` varchar(3) DEFAULT NULL,
  `pet_name` varchar(10) DEFAULT NULL,
  `pet_neutered` bit(1) DEFAULT NULL,
  `pet_photo` varchar(100) DEFAULT NULL,
  `pet_type` varchar(10) DEFAULT NULL,
  `pet_weight` float DEFAULT NULL,
  `rfid_code` varchar(64) DEFAULT NULL,
  `account_state` varchar(10) DEFAULT NULL,
  `pin_account` varchar(40) NOT NULL,
  `virtual_account` varchar(20) NOT NULL,
  PRIMARY KEY (`account_id`),
  KEY `fk_member_id` (`member_id`),
  CONSTRAINT `fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2023-11-12 17:48:05.000000','2023-11-17 04:24:48.983963','NH올원e예금','3027932454151','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','01',1421855,1,'참사랑동물병원',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'00','00820111419481425091415098899','79014197486255'),(2,'2023-11-12 17:49:00.000000','2023-11-17 04:24:48.980202','NH고향사랑기부예금','3021037336171','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','02',8123527,NULL,'조힘권',0,NULL,2,'2012-03-11','말티즈','남아','꼬맹',_binary '','https://a301-s3-bucket.s3.ap-northeast-2.amazonaws.com/dog.png',NULL,6.01,'f8f8e0437bd119fe69804ee1ce13ebe5748b263f34f55e6ce2a5070b52c09a59','00','00820111414951420561410568801','79014197486292'),(3,'2023-11-12 17:49:44.000000','2023-11-17 01:23:06.905015','법사랑플러스예금','3010168334251','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','00',110000,NULL,'배수우록',NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'00','00820111430941446551436558930','79014197486311'),(4,'2023-11-12 17:50:37.000000','2023-11-17 01:23:06.904749','NH청년도약계좌','3021364907171','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','00',1190000,NULL,'백종월',NULL,NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'00','00820111433741449351439358940','79014197486334');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:11:06
