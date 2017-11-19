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
-- Table structure for table `profileentry`
--

DROP TABLE IF EXISTS `profileentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profileentry` (
  `tenantId` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `profileId` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` text,
  `parentId` bigint(20) DEFAULT NULL,
  `index_` bigint(20) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `page` varchar(255) DEFAULT NULL,
  `custom` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`tenantId`,`id`),
  KEY `indexProfileEntry` (`tenantId`,`parentId`,`profileId`),
  KEY `fk_profileentry_profileId` (`tenantId`,`profileId`),
  CONSTRAINT `fk_profileentry_profileId` FOREIGN KEY (`tenantId`, `profileId`) REFERENCES `profile` (`tenantId`, `id`),
  CONSTRAINT `fk_profileentry_tenantId` FOREIGN KEY (`tenantId`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profileentry`
--

LOCK TABLES `profileentry` WRITE;
/*!40000 ALTER TABLE `profileentry` DISABLE KEYS */;
INSERT INTO `profileentry` VALUES (1,1,1,'Tasks','Manage tasks',0,0,'link','tasklistinguser',0),(1,2,1,'Cases','Manage cases',0,2,'link','caselistinguser',0),(1,3,1,'Processes','Manage processes',0,4,'link','processlistinguser',0),(1,4,2,'BPM','BPM',0,0,'folder',NULL,0),(1,5,2,'Tasks','All tasks',4,0,'link','tasklistingadmin',0),(1,6,2,'Cases','All cases',4,2,'link','caselistingadmin',0),(1,7,2,'Processes','All processes',4,4,'link','processlistingadmin',0),(1,8,2,'Organization','Organization',0,2,'folder',NULL,0),(1,9,2,'Users','All users',8,0,'link','userlistingadmin',0),(1,10,2,'Groups','All groups',8,2,'link','grouplistingadmin',0),(1,11,2,'Roles','All roles',8,4,'link','rolelistingadmin',0),(1,12,2,'Import / Export','Import / Export an final organization',8,6,'link','importexportorganization',0),(1,13,2,'Profiles','All profiles',8,8,'link','profilelisting',0),(1,14,2,'Resources','Resources',0,4,'link','pagelisting',0),(1,15,2,'Applications','Applications',0,6,'link','applicationslistingadmin',0);
/*!40000 ALTER TABLE `profileentry` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:10
