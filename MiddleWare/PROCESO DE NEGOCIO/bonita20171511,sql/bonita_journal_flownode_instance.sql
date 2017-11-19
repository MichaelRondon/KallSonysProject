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
-- Table structure for table `flownode_instance`
--

DROP TABLE IF EXISTS `flownode_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flownode_instance` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `flownodeDefinitionId` bigint(20) NOT NULL,
  `kind` varchar(25) NOT NULL,
  `rootContainerId` bigint(20) NOT NULL,
  `parentContainerId` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `displayDescription` varchar(255) DEFAULT NULL,
  `stateId` int(11) NOT NULL,
  `stateName` varchar(50) DEFAULT NULL,
  `prev_state_id` int(11) NOT NULL,
  `terminal` tinyint(1) NOT NULL,
  `stable` tinyint(1) DEFAULT NULL,
  `actorId` bigint(20) DEFAULT NULL,
  `assigneeId` bigint(20) NOT NULL DEFAULT '0',
  `reachedStateDate` bigint(20) DEFAULT NULL,
  `lastUpdateDate` bigint(20) DEFAULT NULL,
  `expectedEndDate` bigint(20) DEFAULT NULL,
  `claimedDate` bigint(20) DEFAULT NULL,
  `priority` tinyint(4) DEFAULT NULL,
  `gatewayType` varchar(50) DEFAULT NULL,
  `hitBys` varchar(255) DEFAULT NULL,
  `stateCategory` varchar(50) NOT NULL,
  `logicalGroup1` bigint(20) NOT NULL,
  `logicalGroup2` bigint(20) NOT NULL,
  `logicalGroup3` bigint(20) DEFAULT NULL,
  `logicalGroup4` bigint(20) NOT NULL,
  `loop_counter` int(11) DEFAULT NULL,
  `loop_max` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `sequential` tinyint(1) DEFAULT NULL,
  `loopDataInputRef` varchar(255) DEFAULT NULL,
  `loopDataOutputRef` varchar(255) DEFAULT NULL,
  `dataInputItemRef` varchar(255) DEFAULT NULL,
  `dataOutputItemRef` varchar(255) DEFAULT NULL,
  `loopCardinality` int(11) DEFAULT NULL,
  `nbActiveInst` int(11) DEFAULT NULL,
  `nbCompletedInst` int(11) DEFAULT NULL,
  `nbTerminatedInst` int(11) DEFAULT NULL,
  `executedBy` bigint(20) DEFAULT NULL,
  `executedBySubstitute` bigint(20) DEFAULT NULL,
  `activityInstanceId` bigint(20) DEFAULT NULL,
  `state_executing` tinyint(1) DEFAULT '0',
  `abortedByBoundary` bigint(20) DEFAULT NULL,
  `triggeredByEvent` tinyint(1) DEFAULT NULL,
  `interrupting` tinyint(1) DEFAULT NULL,
  `tokenCount` int(11) NOT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  KEY `idx_fni_rootcontid` (`rootContainerId`),
  KEY `idx_fni_loggroup4` (`logicalGroup4`),
  KEY `idx_fn_lg2_state_tenant_del` (`logicalGroup2`,`stateName`,`tenantid`),
  CONSTRAINT `fk_flownode_instance_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flownode_instance`
--

LOCK TABLES `flownode_instance` WRITE;
/*!40000 ALTER TABLE `flownode_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `flownode_instance` ENABLE KEYS */;
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
