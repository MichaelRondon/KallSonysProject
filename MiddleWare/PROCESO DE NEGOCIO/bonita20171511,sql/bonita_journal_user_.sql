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
-- Table structure for table `user_`
--

DROP TABLE IF EXISTS `user_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `jobTitle` varchar(255) DEFAULT NULL,
  `managerUserId` bigint(20) DEFAULT NULL,
  `createdBy` bigint(20) DEFAULT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `lastUpdate` bigint(20) DEFAULT NULL,
  `iconid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`userName`),
  KEY `idx_user_name` (`tenantid`,`userName`),
  CONSTRAINT `fk_user__tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_`
--

LOCK TABLES `user_` WRITE;
/*!40000 ALTER TABLE `user_` DISABLE KEYS */;
INSERT INTO `user_` VALUES (1,1,1,'david','5E+LhTGkKkQZWt/okDqMaA==','David','Camacho',NULL,NULL,0,-1,1510771871187,1510771871187,NULL),(1,2,1,'william.jobs','LcTkpvvquKf4KO+prsfXrQ==','William','Jobs','Mr','Chief Executive Officer',0,-1,1510771899527,1510771899527,NULL),(1,3,1,'april.sanchez','LcTkpvvquKf4KO+prsfXrQ==','April','Sanchez','Mrs','Compensation specialist',4,-1,1510771899565,1510771899565,NULL),(1,4,1,'helen.kelly','LcTkpvvquKf4KO+prsfXrQ==','Helen','Kelly','Mrs','Human resource manager',2,-1,1510771899580,1510771899580,NULL),(1,5,1,'walter.bates','LcTkpvvquKf4KO+prsfXrQ==','Walter','Bates','Mr','Human resources benefits',4,-1,1510771899592,1510771899592,NULL),(1,6,1,'zachary.williamson','LcTkpvvquKf4KO+prsfXrQ==','Zachary','Williamson','Mr','Chief Financial Officer',2,-1,1510771899605,1510771899605,NULL),(1,7,1,'patrick.gardenier','LcTkpvvquKf4KO+prsfXrQ==','Patrick','Gardenier','Mr','Financial controller',6,-1,1510771899619,1510771899619,NULL),(1,8,1,'virginie.jomphe','LcTkpvvquKf4KO+prsfXrQ==','Virgine','Jomphe','Mrs','Accountant',6,-1,1510771899631,1510771899631,NULL),(1,9,1,'thorsten.hartmann','LcTkpvvquKf4KO+prsfXrQ==','Thorsten','Hartmann','Mr','Financial planning manager',6,-1,1510771899644,1510771899644,NULL),(1,10,1,'jan.fisher','LcTkpvvquKf4KO+prsfXrQ==','Jan','Fisher','Mr','Infrastucture specialist',12,-1,1510771899656,1510771899656,NULL),(1,11,1,'isabel.bleasdale','LcTkpvvquKf4KO+prsfXrQ==','Isabel','Bleasdale','Mrs','Product marketing manager',12,-1,1510771899670,1510771899670,NULL),(1,12,1,'favio.riviera','LcTkpvvquKf4KO+prsfXrQ==','Favio','Riviera','Mr','Vice President of Marketing',2,-1,1510771899684,1510771899684,NULL),(1,13,1,'michael.morrison','LcTkpvvquKf4KO+prsfXrQ==','Michael','Morrison','Mr','Chief Technical Officer',2,-1,1510771899697,1510771899697,NULL),(1,14,1,'marc.marseau','LcTkpvvquKf4KO+prsfXrQ==','Marc','Marseau','Mr','Engineer',13,-1,1510771899707,1510771899707,NULL),(1,15,1,'joseph.hovell','LcTkpvvquKf4KO+prsfXrQ==','Joseph','Hovell','Mr','Engineer',13,-1,1510771899718,1510771899718,NULL),(1,16,1,'mauro.zetticci','LcTkpvvquKf4KO+prsfXrQ==','Mauro','Zetticci','Mr','Consultant',13,-1,1510771899730,1510771899730,NULL),(1,17,1,'thomas.wallis','LcTkpvvquKf4KO+prsfXrQ==','Thomas','Wallis','Mr','Consultant',13,-1,1510771899742,1510771899742,NULL),(1,18,1,'daniela.angelo','LcTkpvvquKf4KO+prsfXrQ==','Daniela','Angelo','Mrs','Vice President of Sales',2,-1,1510771899753,1510771899753,NULL),(1,19,1,'anthony.nichols','LcTkpvvquKf4KO+prsfXrQ==','Anthony','Nichols','Mr','Account manager',18,-1,1510771899766,1510771899766,NULL),(1,20,1,'misa.kumagai','LcTkpvvquKf4KO+prsfXrQ==','Misa','Kumagai','Mrs','Account manager',18,-1,1510771899778,1510771899778,NULL),(1,21,1,'norio.yamazaki','LcTkpvvquKf4KO+prsfXrQ==','Norio','Yamazaki','Mr','Account manager',18,-1,1510771899791,1510771899791,NULL),(1,22,1,'giovanna.almeida','LcTkpvvquKf4KO+prsfXrQ==','Giovanna','Almeida','Mrs','Account manager',18,-1,1510771899803,1510771899803,NULL);
/*!40000 ALTER TABLE `user_` ENABLE KEYS */;
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
