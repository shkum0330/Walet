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
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modify_time` datetime DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `sub_title` varchar(100) DEFAULT NULL,
  `banner_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (53,'2023-11-17 00:35:11','2023-11-17 00:12:27','농협은행은 애완동물을 사랑하는 고객들을 위해 새로운 서비스를 출시했습니다! ? 이제 농협은행에서는 펫 전용 계좌를 제공하여 고객들의 애완동물에 대한 책임 있는 돌봄을 지원하고 있어요. 이 특별한 계좌는 애완동물 소유주들을 위한 편리한 서비스를 제공합니다. 펫 전용 계좌를 사용하면 애완동물의 의료 비용, 사료 및 용품 구매 등에 대한 지출을 체계적으로 관리할 수 있어요. ? 농협은행의 펫 전용 계좌는 편리한 기능과 다양한 혜택을 제공해요! 이 계좌를 사용하면 애완동물의 건강을 관리하기 위한 병원비 지출을 쉽게 추적할 수 있고, 특별한 할인 혜택과 보너스 포인트를 받을 수 있어요. 또한, 계좌 소유주들을 위한 애완동물 관련 이벤트와 커뮤니티에 참여할 수 있는 기회도 제공됩니다. ? 농협은행은 항상 고객들의 다양한 요구를 충족시키기 위해 노력하고 있어요. 이제 애완동물을 키우는 고객들은 농협은행의 펫 전용 계좌를 통해 보다 안정적으로 애완동물을 돌보고 관리할 수 있어요. 농협은행은 고객들의 삶을 더 행복하고 편리하게 만들기 위해 최선을 다하고 있어요! ?',_binary '','농협은행 단독 펫계좌 출시','간편한 올포유 페이 사용가능!','https://a301-s3-bucket.s3.ap-northeast-2.amazonaws.com/pngwing%203.png'),(54,'2023-11-17 00:35:11','2023-11-17 00:32:23',' ',_binary '\0','Walet 단독 펫계좌 출시','Walet만의 고유 계좌 서비스 제공','https://a301-s3-bucket.s3.ap-northeast-2.amazonaws.com/cat-removebg-preview.png'),(55,'2023-11-17 00:34:57','2023-11-17 00:34:57',' ',NULL,'Walet, 농협과 연계  발표','농협 & Walet만의 서비스 출시!','https://a301-s3-bucket.s3.ap-northeast-2.amazonaws.com/123449_145665_1147-removebg-preview.png');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:11:03
