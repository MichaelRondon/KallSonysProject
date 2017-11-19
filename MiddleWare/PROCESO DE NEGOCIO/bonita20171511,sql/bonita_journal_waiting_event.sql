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
-- Table structure for table `waiting_event`
--

DROP TABLE IF EXISTS `waiting_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `waiting_event` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `kind` varchar(15) NOT NULL,
  `eventType` varchar(50) DEFAULT NULL,
  `messageName` varchar(255) DEFAULT NULL,
  `signalName` varchar(255) DEFAULT NULL,
  `errorCode` varchar(255) DEFAULT NULL,
  `processName` varchar(150) DEFAULT NULL,
  `flowNodeName` varchar(50) DEFAULT NULL,
  `flowNodeDefinitionId` bigint(20) DEFAULT NULL,
  `subProcessId` bigint(20) DEFAULT NULL,
  `processDefinitionId` bigint(20) DEFAULT NULL,
  `rootProcessInstanceId` bigint(20) DEFAULT NULL,
  `parentProcessInstanceId` bigint(20) DEFAULT NULL,
  `flowNodeInstanceId` bigint(20) DEFAULT NULL,
  `relatedActivityInstanceId` bigint(20) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `progress` tinyint(4) DEFAULT NULL,
  `correlation1` varchar(128) DEFAULT NULL,
  `correlation2` varchar(128) DEFAULT NULL,
  `correlation3` varchar(128) DEFAULT NULL,
  `correlation4` varchar(128) DEFAULT NULL,
  `correlation5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  KEY `idx_waiting_event` (`progress`,`tenantid`,`kind`,`locked`,`active`),
  CONSTRAINT `fk_waiting_event_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waiting_event`
--

LOCK TABLES `waiting_event` WRITE;
/*!40000 ALTER TABLE `waiting_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `waiting_event` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:07
