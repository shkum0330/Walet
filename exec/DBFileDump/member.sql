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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `birth` varchar(10) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `finger_print` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `pin_number` varchar(255) DEFAULT NULL,
  `role` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UK_mbmcqelty0fbrvxp1q58dn57t` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'2023-11-10 07:48:35.355572','2023-11-10 07:48:35.355572','19930726','khg@naver.com',NULL,_binary '\0','계효건','$2a$10$BCjQlMQaEb7YnZzAg4Gaiuvx5M2SacYTDlCGF3nQVKbliXzFojgKG','01000000000','000000','USER'),(2,'2023-11-10 07:50:26.538293','2023-11-10 07:50:26.538293','19880920','chg@naver.com',NULL,_binary '\0','조힘권','$2a$10$fSepe3VwIk6qG6T6TTSiVO2rXo60SzQNDFD2h8WErEv0D38z1/qcS','01000000000','000000','USER'),(3,'2023-11-10 07:50:52.466876','2023-11-10 07:50:52.466876','19820330','pswr@naver.com',NULL,_binary '\0','배수우록','$2a$10$j8dJOgUOD5mlRAJn1muHAOF8H/tOnpF6t0oxs4QQY91zulm..3zya','01000000000','000000','USER'),(4,'2023-11-10 07:51:25.724921','2023-11-10 07:51:25.724921','19930513','bjw@naver.com',NULL,_binary '\0','백종월','$2a$10$GOEN8rzFxF8pS2nbOBNClOggSCaru1jj/zOsSz76.gsxPI9GzafOm','01000000000','000000','USER'),(39,'2023-11-13 01:26:23.879016','2023-11-13 01:26:23.879016','1900.1.1','coach@coach.com',NULL,_binary '\0','코치','$2a$10$OOg3wiL7r.GmAxs8Q06KNur/D6JorLRz4rOwsU3vAJENolBB1yRsq','010-3001-8738','141414','USER'),(40,'2023-11-17 00:07:06.778166','2023-11-17 00:07:06.778166','20195220','admin',NULL,_binary '\0','박종권','$2a$10$kAxOT1Fa8C24z.qif66B9OS7CrSN/eiZmQsfO.dhSYSc.F7WAhoZW','010-0000-0000','0000000','ADMIN');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:11:04
