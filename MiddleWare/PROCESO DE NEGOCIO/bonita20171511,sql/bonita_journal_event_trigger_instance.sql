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
-- Table structure for table `event_trigger_instance`
--

DROP TABLE IF EXISTS `event_trigger_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_trigger_instance` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `kind` varchar(15) NOT NULL,
  `eventInstanceId` bigint(20) NOT NULL,
  `eventInstanceName` varchar(50) DEFAULT NULL,
  `messageName` varchar(255) DEFAULT NULL,
  `targetProcess` varchar(255) DEFAULT NULL,
  `targetFlowNode` varchar(255) DEFAULT NULL,
  `signalName` varchar(255) DEFAULT NULL,
  `errorCode` varchar(255) DEFAULT NULL,
  `executionDate` bigint(20) DEFAULT NULL,
  `jobTriggerName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  CONSTRAINT `fk_event_trigger_instance_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_trigger_instance`
--

LOCK TABLES `event_trigger_instance` WRITE;
/*!40000 ALTER TABLE `event_trigger_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_trigger_instance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:15
