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
-- Table structure for table `user_membership`
--

DROP TABLE IF EXISTS `user_membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_membership` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `assignedBy` bigint(20) DEFAULT NULL,
  `assignedDate` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`userId`,`roleId`,`groupId`),
  CONSTRAINT `fk_user_membership_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_membership`
--

LOCK TABLES `user_membership` WRITE;
/*!40000 ALTER TABLE `user_membership` DISABLE KEYS */;
INSERT INTO `user_membership` VALUES (1,1,2,1,1,-1,0),(1,2,3,1,2,-1,0),(1,3,4,1,2,-1,0),(1,4,5,1,2,-1,0),(1,5,6,1,3,-1,0),(1,6,7,1,3,-1,0),(1,7,8,1,3,-1,0),(1,8,9,1,3,-1,0),(1,9,10,1,4,-1,0),(1,10,11,1,5,-1,0),(1,11,12,1,5,-1,0),(1,12,13,1,6,-1,0),(1,13,14,1,12,-1,0),(1,14,15,1,12,-1,0),(1,15,16,1,13,-1,0),(1,16,17,1,13,-1,0),(1,17,18,1,8,-1,0),(1,18,20,1,9,-1,0),(1,19,21,1,9,-1,0),(1,20,22,1,10,-1,0),(1,21,19,1,11,-1,0);
/*!40000 ALTER TABLE `user_membership` ENABLE KEYS */;
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
