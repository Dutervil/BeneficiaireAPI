-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: bms
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `depence`
--

DROP TABLE IF EXISTS `depence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `depence` (
  `id` bigint NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `beneficiare_id` bigint DEFAULT NULL,
  `date_depence` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moncash_number` varchar(255) DEFAULT NULL,
  `montant` double NOT NULL,
  `nom_cheque` varchar(255) DEFAULT NULL,
  `numero_cheque` varchar(255) DEFAULT NULL,
  `type_depence` varchar(255) DEFAULT NULL,
  `unite_monetaire` varchar(255) DEFAULT NULL,
  `versement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depence`
--

LOCK TABLES `depence` WRITE;
/*!40000 ALTER TABLE `depence` DISABLE KEYS */;
INSERT INTO `depence` VALUES (6,'264347',5,'2023-10-26','payrol','2663873',10000,'Pierre Peterson','04338','Payroll','','1'),(7,'454566',5,'2023-10-18','Paiement Scolaire','',900,'Abdias Pierre','23874','Scolaire','USD','1'),(8,'32652',5,'2023-10-24','Paiment medical','',470,'Murdet Pierre','82938','Medical','HTG','1'),(11,'3223425',5,'2023-11-02','Paiment Logement','',45000,'Pierre Peterson','53322','Logement','USD','1');
/*!40000 ALTER TABLE `depence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-02 15:14:50
