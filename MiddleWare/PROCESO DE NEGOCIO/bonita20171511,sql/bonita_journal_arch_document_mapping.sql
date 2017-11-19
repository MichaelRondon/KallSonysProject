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
-- Table structure for table `arch_document_mapping`
--

DROP TABLE IF EXISTS `arch_document_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arch_document_mapping` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `sourceObjectId` bigint(20) DEFAULT NULL,
  `processinstanceid` bigint(20) NOT NULL,
  `documentid` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text,
  `version` varchar(50) NOT NULL,
  `index_` int(11) NOT NULL,
  `archiveDate` bigint(20) NOT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  KEY `idx_a_doc_mp_pr_id` (`processinstanceid`,`tenantid`),
  KEY `fk_archdocmap_docid` (`tenantid`,`documentid`),
  CONSTRAINT `fk_arch_document_mapping_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`),
  CONSTRAINT `fk_archdocmap_docid` FOREIGN KEY (`tenantid`, `documentid`) REFERENCES `document` (`tenantid`, `id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arch_document_mapping`
--

LOCK TABLES `arch_document_mapping` WRITE;
/*!40000 ALTER TABLE `arch_document_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `arch_document_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:18
