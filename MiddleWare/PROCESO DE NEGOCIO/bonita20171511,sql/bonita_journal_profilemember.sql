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
-- Table structure for table `profilemember`
--

DROP TABLE IF EXISTS `profilemember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profilemember` (
  `tenantId` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `profileId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`tenantId`,`id`),
  UNIQUE KEY `tenantId` (`tenantId`,`profileId`,`userId`,`groupId`,`roleId`),
  CONSTRAINT `fk_profilemember_profileId` FOREIGN KEY (`tenantId`, `profileId`) REFERENCES `profile` (`tenantId`, `id`),
  CONSTRAINT `fk_profilemember_tenantId` FOREIGN KEY (`tenantId`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profilemember`
--

LOCK TABLES `profilemember` WRITE;
/*!40000 ALTER TABLE `profilemember` DISABLE KEYS */;
INSERT INTO `profilemember` VALUES (1,22,1,-1,-1,1),(1,12,1,-1,1,-1),(1,15,1,-1,2,-1),(1,16,1,-1,3,-1),(1,20,1,-1,4,-1),(1,17,1,-1,5,-1),(1,21,1,-1,6,-1),(1,19,1,-1,8,-1),(1,13,1,-1,9,-1),(1,14,1,-1,10,-1),(1,18,1,-1,11,-1),(1,11,1,2,-1,-1),(1,6,1,5,-1,-1),(1,9,1,7,-1,-1),(1,5,1,9,-1,-1),(1,7,1,10,-1,-1),(1,2,1,11,-1,-1),(1,10,1,15,-1,-1),(1,8,1,18,-1,-1),(1,3,1,22,-1,-1),(1,1,2,1,-1,-1);
/*!40000 ALTER TABLE `profilemember` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:13
