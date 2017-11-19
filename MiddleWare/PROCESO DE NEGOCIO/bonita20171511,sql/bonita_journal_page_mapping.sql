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
-- Table structure for table `page_mapping`
--

DROP TABLE IF EXISTS `page_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_mapping` (
  `tenantId` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `key_` varchar(255) NOT NULL,
  `pageId` bigint(20) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `urladapter` varchar(255) DEFAULT NULL,
  `page_authoriz_rules` text,
  `lastUpdateDate` bigint(20) DEFAULT NULL,
  `lastUpdatedBy` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tenantId`,`id`),
  UNIQUE KEY `tenantId` (`tenantId`,`key_`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page_mapping`
--

LOCK TABLES `page_mapping` WRITE;
/*!40000 ALTER TABLE `page_mapping` DISABLE KEYS */;
INSERT INTO `page_mapping` VALUES (1,1,'apiExtension|GET|demo/logExample',9,NULL,NULL,NULL,0,0),(1,2,'apiExtension|GET|demo/headerExample',9,NULL,NULL,NULL,0,0),(1,3,'apiExtension|POST|demo/postExample',9,NULL,NULL,NULL,0,0),(1,4,'apiExtension|GET|demo/xmlExample',9,NULL,NULL,NULL,0,0),(1,5,'apiExtension|GET|demo/getExample',9,NULL,NULL,NULL,0,0),(1,6,'apiExtension|GET|demo/soapExample',9,NULL,NULL,NULL,0,0),(1,7,'taskInstance/OrderRequest/1.0/Validacion Orden',11,NULL,NULL,'IS_ADMIN,IS_PROCESS_OWNER,IS_TASK_AVAILABLE_FOR_USER,',0,0),(1,8,'process/OrderRequest/1.0',NULL,NULL,NULL,'IS_ADMIN,IS_PROCESS_OWNER,IS_ACTOR_INITIATOR,',0,0),(1,9,'processInstance/OrderRequest/1.0',6,NULL,NULL,'IS_ADMIN,IS_PROCESS_OWNER,IS_PROCESS_INITIATOR,IS_TASK_PERFORMER,IS_INVOLVED_IN_PROCESS_INSTANCE,',0,0);
/*!40000 ALTER TABLE `page_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:17
