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
-- Table structure for table `business_app`
--

DROP TABLE IF EXISTS `business_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_app` (
  `tenantId` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `token` varchar(50) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` text,
  `iconPath` varchar(255) DEFAULT NULL,
  `creationDate` bigint(20) NOT NULL,
  `createdBy` bigint(20) NOT NULL,
  `lastUpdateDate` bigint(20) NOT NULL,
  `updatedBy` bigint(20) NOT NULL,
  `state` varchar(30) NOT NULL,
  `homePageId` bigint(20) DEFAULT NULL,
  `profileId` bigint(20) DEFAULT NULL,
  `layoutId` bigint(20) DEFAULT NULL,
  `themeId` bigint(20) DEFAULT NULL,
  `displayName` varchar(255) NOT NULL,
  PRIMARY KEY (`tenantId`,`id`),
  UNIQUE KEY `uk_app_token_version` (`tenantId`,`token`,`version`),
  KEY `idx_app_token` (`token`,`tenantId`),
  KEY `idx_app_profile` (`profileId`,`tenantId`),
  KEY `idx_app_homepage` (`homePageId`,`tenantId`),
  KEY `fk_app_profileId` (`tenantId`,`profileId`),
  KEY `fk_app_layoutId` (`tenantId`,`layoutId`),
  KEY `fk_app_themeId` (`tenantId`,`themeId`),
  CONSTRAINT `fk_app_layoutId` FOREIGN KEY (`tenantId`, `layoutId`) REFERENCES `page` (`tenantId`, `id`),
  CONSTRAINT `fk_app_profileId` FOREIGN KEY (`tenantId`, `profileId`) REFERENCES `profile` (`tenantId`, `id`),
  CONSTRAINT `fk_app_tenantId` FOREIGN KEY (`tenantId`) REFERENCES `tenant` (`id`),
  CONSTRAINT `fk_app_themeId` FOREIGN KEY (`tenantId`, `themeId`) REFERENCES `page` (`tenantId`, `id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_app`
--

LOCK TABLES `business_app` WRITE;
/*!40000 ALTER TABLE `business_app` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_app` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:08
