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
-- Table structure for table `arch_process_instance`
--

DROP TABLE IF EXISTS `arch_process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arch_process_instance` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(75) NOT NULL,
  `processDefinitionId` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `startDate` bigint(20) NOT NULL,
  `startedBy` bigint(20) NOT NULL,
  `startedBySubstitute` bigint(20) NOT NULL,
  `endDate` bigint(20) NOT NULL,
  `archiveDate` bigint(20) NOT NULL,
  `stateId` int(11) NOT NULL,
  `lastUpdate` bigint(20) NOT NULL,
  `rootProcessInstanceId` bigint(20) DEFAULT NULL,
  `callerId` bigint(20) DEFAULT NULL,
  `sourceObjectId` bigint(20) NOT NULL,
  `stringIndex1` varchar(255) DEFAULT NULL,
  `stringIndex2` varchar(255) DEFAULT NULL,
  `stringIndex3` varchar(255) DEFAULT NULL,
  `stringIndex4` varchar(255) DEFAULT NULL,
  `stringIndex5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  KEY `idx1_arch_process_instance` (`tenantid`,`sourceObjectId`,`rootProcessInstanceId`,`callerId`),
  KEY `idx2_arch_process_instance` (`tenantid`,`processDefinitionId`,`archiveDate`),
  KEY `idx3_arch_process_instance` (`tenantid`,`sourceObjectId`,`callerId`,`stateId`),
  CONSTRAINT `fk_arch_process_instance_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arch_process_instance`
--

LOCK TABLES `arch_process_instance` WRITE;
/*!40000 ALTER TABLE `arch_process_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `arch_process_instance` ENABLE KEYS */;
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
