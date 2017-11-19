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
-- Table structure for table `user_contactinfo`
--

DROP TABLE IF EXISTS `user_contactinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_contactinfo` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `building` varchar(50) DEFAULT NULL,
  `room` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zipCode` varchar(50) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `personal` tinyint(1) NOT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`userId`,`personal`),
  KEY `idx_user_contactinfo` (`userId`,`tenantid`,`personal`),
  CONSTRAINT `fk_contact_user` FOREIGN KEY (`tenantid`, `userId`) REFERENCES `user_` (`tenantid`, `id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_contactinfo`
--

LOCK TABLES `user_contactinfo` WRITE;
/*!40000 ALTER TABLE `user_contactinfo` DISABLE KEYS */;
INSERT INTO `user_contactinfo` VALUES (1,1,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,2,2,'william.jobs@acme.com','484-302-5229',NULL,'484-302-0229','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,3,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,4,3,'april.sanchez@acme.com','484-302-5909',NULL,'484-302-0909','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,5,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,6,4,'helen.kelly@acme.com','484-302-5909',NULL,'484-302-0909','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,7,5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,8,5,'walter.bates@acme.com','484-302-5909',NULL,'484-302-0909','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,9,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,10,6,'zachary.williamson@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,11,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,12,7,'patrick.gardenier@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,13,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,14,8,'virginie.jomphe@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,15,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,16,9,'thorsten.hartmann@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,17,10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,18,10,'jan.fisher@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,19,11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,20,11,'isabel.bleasdale@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,21,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,22,12,'favio.riviera@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,23,13,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,24,13,'michael.morrison@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,25,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,26,14,'marc.marseau@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,27,15,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,28,15,'joseph.hovell@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,29,16,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,30,16,'mauro.zetticci@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,31,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,32,17,'thomas.wallis@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,33,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,34,18,'daniela.angelo@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,35,19,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,36,19,'anthony.nichols@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,37,20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,38,20,'misa.kumagai@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,39,21,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,40,21,'norio.yamazaki@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0),(1,41,22,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(1,42,22,'giovanna.almeida@acme.com','484-302-5485',NULL,'484-302-0485','70',NULL,'Renwick Drive','19108','Philadelphia','PA','United States',NULL,0);
/*!40000 ALTER TABLE `user_contactinfo` ENABLE KEYS */;
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
