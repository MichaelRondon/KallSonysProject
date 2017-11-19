CREATE DATABASE  IF NOT EXISTS `bonita_journal` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bonita_journal`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 192.168.99.100    Database: bonita_journal
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `form_mapping`
--

DROP TABLE IF EXISTS `form_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_mapping` (
  `tenantId` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `process` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `task` varchar(255) DEFAULT NULL,
  `page_mapping_tenant_id` bigint(20) DEFAULT NULL,
  `page_mapping_id` bigint(20) DEFAULT NULL,
  `lastUpdateDate` bigint(20) DEFAULT NULL,
  `lastUpdatedBy` bigint(20) DEFAULT NULL,
  `target` varchar(16) NOT NULL,
  PRIMARY KEY (`tenantId`,`id`),
  KEY `fk_form_mapping_key` (`page_mapping_tenant_id`,`page_mapping_id`),
  CONSTRAINT `fk_form_mapping_key` FOREIGN KEY (`page_mapping_tenant_id`, `page_mapping_id`) REFERENCES `page_mapping` (`tenantId`, `id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_mapping`
--

LOCK TABLES `form_mapping` WRITE;
/*!40000 ALTER TABLE `form_mapping` DISABLE KEYS */;
INSERT INTO `form_mapping` VALUES (1,1,6567068885008966562,3,'Validacion Orden',1,7,0,0,'INTERNAL'),(1,2,6567068885008966562,1,NULL,1,8,0,0,'NONE'),(1,3,6567068885008966562,2,NULL,1,9,0,0,'INTERNAL');
/*!40000 ALTER TABLE `form_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:12
