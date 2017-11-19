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
-- Table structure for table `process_definition`
--

DROP TABLE IF EXISTS `process_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_definition` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `processId` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `deploymentDate` bigint(20) NOT NULL,
  `deployedBy` bigint(20) NOT NULL,
  `activationState` varchar(30) NOT NULL,
  `configurationState` varchar(30) NOT NULL,
  `displayName` varchar(75) DEFAULT NULL,
  `displayDescription` varchar(255) DEFAULT NULL,
  `lastUpdateDate` bigint(20) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `iconPath` varchar(255) DEFAULT NULL,
  `content_tenantid` bigint(20) NOT NULL,
  `content_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`name`,`version`),
  KEY `fk_process_definition_content` (`content_tenantid`,`content_id`),
  CONSTRAINT `fk_process_definition_content` FOREIGN KEY (`content_tenantid`, `content_id`) REFERENCES `process_content` (`tenantid`, `id`),
  CONSTRAINT `fk_process_definition_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_definition`
--

LOCK TABLES `process_definition` WRITE;
/*!40000 ALTER TABLE `process_definition` DISABLE KEYS */;
INSERT INTO `process_definition` VALUES (1,1,6567068885008966562,'OrderRequest','1.0','',1510772029817,1,'ENABLED','RESOLVED','OrderRequest','',1510772113002,NULL,NULL,1,1);
/*!40000 ALTER TABLE `process_definition` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:11
