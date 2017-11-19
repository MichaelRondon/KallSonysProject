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
-- Table structure for table `command`
--

DROP TABLE IF EXISTS `command`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `command` (
  `tenantid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text,
  `IMPLEMENTATION` varchar(100) NOT NULL,
  `system` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`tenantid`,`id`),
  UNIQUE KEY `tenantid` (`tenantid`,`name`),
  CONSTRAINT `fk_command_tenantId` FOREIGN KEY (`tenantid`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `command`
--

LOCK TABLES `command` WRITE;
/*!40000 ALTER TABLE `command` DISABLE KEYS */;
INSERT INTO `command` VALUES (1,1,'getBusinessDataByQueryCommand','Execute a named query in the BDM, and returns its Json representation. Use parameter keys :\n                \"queryName\" the name of the query in the bdm, \"returnType\" the query expected return type, \"returnsList\"\n                if result is a List or a single value, \"queryParameters\" a Map to value query parameters\n            ','org.bonitasoft.engine.command.GetBusinessDataByQueryCommand',1),(1,2,'isAllowedToSeeOverviewForm','Check if one of many actors can be allowed to read overview form.','org.bonitasoft.engine.external.permission.IsAllowedToSeeOverviewForm',1),(1,3,'canStartProcessDefinition','Return true if the user can start the process, false otherwise. Use parameter key USER_ID_KEY and\n                PROCESS_DEFINITION_ID_KEY\n            ','org.bonitasoft.engine.external.permission.CanStartProcessDefinition',1),(1,4,'isAllowedToStartProcesses','Check if the user can be allowed to start all of processes.','org.bonitasoft.engine.external.permission.IsAllowedToStartProcesses',1),(1,5,'getSupervisor','Get process supervisor. Use parameter key SUPERVISOR_ID_KEY','org.bonitasoft.engine.external.process.GetSupervisor',1),(1,6,'isAllowedToStartProcess','Check if one of many actors can be allowed to start process.(who\'s the process Initiator)','org.bonitasoft.engine.external.permission.IsAllowedToStartProcess',1),(1,7,'executeBDMQuery','Execute a named query in the BDM. Use parameter keys : \"queryName\" the name of the query in the bdm,\n                \"returnType\" the query expected return type, \"returnsList\" if result is a List or a single value,\n                \"queryParameters\" a Map to value query parameters\n            ','org.bonitasoft.engine.command.ExecuteBDMQueryCommand',1),(1,8,'isUserProcessSupervisor','Get if the user a supervisor of processDefinition. Use parameter keys USER_ID_KEY,\n                PROCESS_DEFINITION_ID_KEY\n            ','org.bonitasoft.engine.external.process.IsUserProcessSupervisor',1),(1,9,'isInvolvedInHumanTask','Return true if a user is involved in a specific human task, false otherwise. Use parameter key\n                USER_ID_KEY and HUMAN_TASK_INSTANCE_ID_KEY\n            ','org.bonitasoft.engine.external.permission.IsInvolvedInHumanTask',1),(1,10,'searchEntityMembersCommand','Searches Entity member mappings: Parameter keys: DISCRIMINATOR_ID_KEY: the discriminator to isolate\n                the different functional notions,\n                SEARCH_OPTIONS_KEY: the Search options to filter and sort the results, MEMBER_TYPE_KEY: Member Type to\n                search for (USER, GROUP, ROLE, or MEMBERSHIP).\n            ','org.bonitasoft.engine.external.identitymapping.SearchEntityMembersCommand',1),(1,11,'searchProcessSupervisors','Search process supervisor for process definition.','org.bonitasoft.engine.external.process.SearchProcessSupervisors',1),(1,12,'deleteEntityMembersCommand','Delete all Entity member mappings associated with provided externalId, for specific Discriminator.\n                Parameter keys: EXTERNAL_ID_KEY: external id\n                provided as is by the external system, DISCRIMINATOR_ID_KEY: the discriminator to isolate the different\n                functional notions.\n            ','org.bonitasoft.engine.external.identitymapping.DeleteEntityMembersCommand',1),(1,13,'searchArchivedTasksSupervisedBy','Returns the list of archived user tasks of process definition supervised by a user. Use parameter\n                keys SUPERVISOR_ID_KEY: id of the supervisor,\n                SEARCH_OPTIONS_KEY: the object containing all the search parameters\n            ','org.bonitasoft.engine.external.task.supervisor.SearchArchivedTasksSupervisedBy',1),(1,14,'removeEntityMemberCommand','Removes a Entity member mapping: ENTITY_MEMBER_ID_KEY: Entity member id to remove','org.bonitasoft.engine.external.identitymapping.RemoveEntityMemberCommand',1),(1,15,'deleteSessionCommand','Deletes a tenant session based on its sessionId','org.bonitasoft.engine.command.DeleteSessionCommand',1),(1,16,'searchAssignedTasksSupervisedBy','Returns the list of assigned user tasks of process definition supervised by a user.','org.bonitasoft.engine.external.task.supervisor.SearchAssignedTasksSupervisedBy',1),(1,17,'deleteSupervisor','Delete process supervisor. Use parameter key SUPERVISOR_ID_KEY','org.bonitasoft.engine.external.process.DeleteSupervisor',1),(1,18,'searchWaitingEventsCommand','Search waiting events. Parameter keys: searchOptions.','org.bonitasoft.engine.command.system.SearchWaitingEventsCommand',1),(1,19,'advancedStartProcessCommand','Advanced start process.','org.bonitasoft.engine.command.AdvancedStartProcessCommand',1),(1,20,'createSupervisor','Create process supervisor.','org.bonitasoft.engine.external.process.CreateSupervisor',1),(1,21,'addEntityMemberCommand','Adds a Entity member to the provided identity notions. Parameter keys: EXTERNAL_ID_KEY: Entity member\n                id provided as is by the external system,\n                userId: -1 is not needed, roleId: -1 is not needed, groupId: -1 is not needed\n            ','org.bonitasoft.engine.external.identitymapping.AddEntityMemberCommand',1),(1,22,'executeActionsAndTerminate','execute actions and terminate a user task instance','org.bonitasoft.engine.external.web.forms.ExecuteActionsAndTerminateTask',1),(1,23,'getBusinessDataById','Get the business data via its identifier and class name, and returns its Json representation.','org.bonitasoft.engine.command.GetBusinessDataByIdCommand',1),(1,24,'multipleStartPointsProcessCommand','Advanced start process using multiple start points.','org.bonitasoft.engine.command.MultipleStartPointsProcessCommand',1),(1,25,'searchSCommentSupervisedBy','Returns the list of comments for processes supervised by a user. One mandatory parameter:\n                supervisorId: ID of the user supervisor of the processes on\n                which comments are.\n            ','org.bonitasoft.engine.external.comment.SearchCommentsSupervisedBy',1),(1,26,'isAllowedToSeeInstanciationForm','Check if one of many actors can be allowed to read Instanciation form.','org.bonitasoft.engine.external.permission.IsAllowedToSeeInstanciationForm',1),(1,27,'exportDefaultProfilesCommand','Export default profiles','org.bonitasoft.engine.external.web.profile.command.ExportDefaultProfilesCommand',1),(1,28,'searchEntityMembersForUserCommand','Searches Entity member mappings: Parameter keys: USER_ID_KEY: the ID of the user to search Entity\n                members for (directly associated, or through role or\n                group), DISCRIMINATOR_ID_KEY: the discriminator to isolate the different functional notions,\n                SEARCH_OPTIONS_KEY: the Search options to filter and sort the\n                results.\n            ','org.bonitasoft.engine.external.identitymapping.SearchEntityMembersForUserCommand',1),(1,29,'importProfilesCommand','Import profiles, profileEntries and profile mappings','org.bonitasoft.engine.external.web.profile.command.ImportProfilesCommand',1),(1,30,'searchProcessDefinitionsSupervisedBy','Searching processeDefinitions whose supervisor is the specified user.','org.bonitasoft.engine.external.supervisor.SearchProcessDefinitionsSupervisedBy',1),(1,31,'getBusinessDataByIds','Get the business data via its identifiers and class name, and returns its Json representation.\n            ','org.bonitasoft.engine.command.GetBusinessDataByIdsCommand',1);
/*!40000 ALTER TABLE `command` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 18:33:19
