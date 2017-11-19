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
-- Table structure for table `group_`
--

DROP TABLE IF EXISTS `group_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(125) NOT NULL,
  `parentPath` varchar(255) DEFAULT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `description` text,
  `createdBy` bigint(20) DEFAULT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `lastUpdate` bigint(20) DEFAULT NULL,
  `iconid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`parentPath`,`name`),
  CONSTRAINT `fk_group__tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_`
--

LOCK TABLES `group_` WRITE;
/*!40000 ALTER TABLE `group_` DISABLE KEYS */;
INSERT INTO `group_` VALUES (1,1,'acme',NULL,'Acme','This group represents the acme department of the ACME organization',-1,1510771899821,1510771899821,NULL),(1,2,'hr','/acme','Human Resources','This group represents the human resources department of the ACME organization',-1,1510771899906,1510771899906,NULL),(1,3,'finance','/acme','Finance','This group represents the finance department of the ACME organization',-1,1510771899911,1510771899911,NULL),(1,4,'it','/acme','Infrastructure','This group represents the infrastructure department of the ACME organization',-1,1510771899916,1510771899916,NULL),(1,5,'marketing','/acme','Marketing','This group represents the marketing department of the ACME organization',-1,1510771899922,1510771899922,NULL),(1,6,'production','/acme','Production','This group represents the production department of the ACME organization',-1,1510771899927,1510771899927,NULL),(1,7,'sales','/acme','Sales','This group represents the sales department of the ACME organization',-1,1510771899933,1510771899933,NULL),(1,8,'europe','/acme/sales','Europe','This group represents the europe department of the ACME organization',-1,1510771899939,1510771899939,NULL),(1,9,'asia','/acme/sales','Asia','This group represents the asia department of the ACME organization',-1,1510771899945,1510771899945,NULL),(1,10,'latin_america','/acme/sales','Latin America','This group represents the latin america department of the ACME organization',-1,1510771899951,1510771899951,NULL),(1,11,'north_america','/acme/sales','North America','This group represents the north america department of the ACME organization',-1,1510771899960,1510771899960,NULL),(1,12,'rd','/acme/production','Research & Development','This group represents the research & development department of the ACME organization',-1,1510771899966,1510771899966,NULL),(1,13,'services','/acme/production','Services','This group represents the services department of the ACME organization',-1,1510771899972,1510771899972,NULL);
/*!40000 ALTER TABLE `group_` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:19
