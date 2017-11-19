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
-- Table structure for table `actormember`
--

DROP TABLE IF EXISTS `actormember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actormember` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `actorId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`actorId`,`userId`,`groupId`,`roleId`),
  CONSTRAINT `fk_actormember_actorId` FOREIGN KEY (`tenantid`, `actorId`) REFERENCES `actor` (`tenantid`, `id`),
  CONSTRAINT `fk_actormember_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actormember`
--

LOCK TABLES `actormember` WRITE;
/*!40000 ALTER TABLE `actormember` DISABLE KEYS */;
INSERT INTO `actormember` VALUES (1,14,1,-1,-1,1),(1,1,1,-1,1,-1),(1,2,1,-1,2,-1),(1,3,1,-1,3,-1),(1,4,1,-1,4,-1),(1,5,1,-1,5,-1),(1,6,1,-1,6,-1),(1,9,1,-1,7,-1),(1,10,1,-1,8,-1),(1,11,1,-1,9,-1),(1,12,1,-1,10,-1),(1,13,1,-1,11,-1),(1,7,1,-1,12,-1),(1,8,1,-1,13,-1),(1,35,1,2,-1,-1),(1,16,1,3,-1,-1),(1,22,1,4,-1,-1),(1,33,1,5,-1,-1),(1,36,1,6,-1,-1),(1,30,1,7,-1,-1),(1,34,1,8,-1,-1),(1,32,1,9,-1,-1),(1,21,1,10,-1,-1),(1,23,1,11,-1,-1),(1,20,1,12,-1,-1),(1,28,1,13,-1,-1),(1,25,1,14,-1,-1),(1,24,1,15,-1,-1),(1,26,1,16,-1,-1),(1,31,1,17,-1,-1),(1,17,1,18,-1,-1),(1,15,1,19,-1,-1),(1,27,1,20,-1,-1),(1,29,1,21,-1,-1),(1,18,1,22,-1,-1),(1,59,2,-1,-1,1),(1,64,2,-1,2,-1),(1,63,2,-1,3,-1),(1,65,2,-1,4,-1),(1,67,2,-1,5,-1),(1,61,2,-1,8,-1),(1,62,2,-1,9,-1),(1,68,2,-1,10,-1),(1,66,2,-1,11,-1),(1,72,2,-1,12,-1),(1,73,2,-1,13,-1),(1,40,2,1,-1,-1),(1,57,2,2,-1,-1),(1,38,2,3,-1,-1),(1,45,2,4,-1,-1),(1,55,2,5,-1,-1),(1,58,2,6,-1,-1),(1,52,2,7,-1,-1),(1,56,2,8,-1,-1),(1,54,2,9,-1,-1),(1,43,2,10,-1,-1),(1,42,2,11,-1,-1),(1,41,2,12,-1,-1),(1,49,2,13,-1,-1),(1,47,2,14,-1,-1),(1,46,2,15,-1,-1),(1,48,2,16,-1,-1),(1,53,2,17,-1,-1),(1,37,2,18,-1,-1),(1,39,2,19,-1,-1),(1,51,2,20,-1,-1),(1,50,2,21,-1,-1),(1,44,2,22,-1,-1);
/*!40000 ALTER TABLE `actormember` ENABLE KEYS */;
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
