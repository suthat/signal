-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 12, 2013 at 07:30 AM
-- Server version: 5.5.25
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `signaldb`
--

-- --------------------------------------------------------

--
-- Table structure for table `appapns`
--

DROP TABLE IF EXISTS `appapns`;
CREATE TABLE IF NOT EXISTS `appapns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `certificate` varchar(64) NOT NULL,
  `p12key` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `appapns`
--

INSERT INTO `appapns` (`id`, `uuid`, `certificate`, `p12key`, `password`) VALUES
(1, '596b65af-61b3-4b8d-9e58-a84f62c00c89', '596b65af.1379485835.cer', '596b65af.1379485839.p12', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `appgcm`
--

DROP TABLE IF EXISTS `appgcm`;
CREATE TABLE IF NOT EXISTS `appgcm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `googleapiskey` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `appgcm`
--

INSERT INTO `appgcm` (`id`, `uuid`, `googleapiskey`) VALUES
(1, '596b65af-61b3-4b8d-9e58-a84f62c00c89', 'AIzaSyAz_24XXx_HO7kpAIpn5mJjDRVIxTcJBVk');

-- --------------------------------------------------------

--
-- Table structure for table `apps`
--

DROP TABLE IF EXISTS `apps`;
CREATE TABLE IF NOT EXISTS `apps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `uuid` varchar(64) NOT NULL,
  `secret` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(512) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  `ios` varchar(4) NOT NULL,
  `android` varchar(4) NOT NULL,
  `cli` varchar(4) NOT NULL,
  `html5` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `apps`
--

INSERT INTO `apps` (`id`, `user`, `uuid`, `secret`, `name`, `description`, `created`, `status`, `ios`, `android`, `cli`, `html5`) VALUES
(1, 2, '596b65af-61b3-4b8d-9e58-a84f62c00c89', 'eb63ae7e7415a27a8553ae4f7021f314', 'Hello', 'My first provider', '2013-09-10 12:45:23', 1, 'use', 'use', 'use', 'use');

-- --------------------------------------------------------

--
-- Table structure for table `fbotp`
--

DROP TABLE IF EXISTS `fbotp`;
CREATE TABLE IF NOT EXISTS `fbotp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fdata`
--

DROP TABLE IF EXISTS `fdata`;
CREATE TABLE IF NOT EXISTS `fdata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  `platform` varchar(8) NOT NULL,
  `receiver` varchar(256) NOT NULL,
  `referrer` varchar(256) NOT NULL,
  `deliveredon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fbref` (`fbref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fvararray`
--

DROP TABLE IF EXISTS `fvararray`;
CREATE TABLE IF NOT EXISTS `fvararray` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  `fvname` text NOT NULL,
  `fvvalue` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fbref` (`fbref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fvarboolean`
--

DROP TABLE IF EXISTS `fvarboolean`;
CREATE TABLE IF NOT EXISTS `fvarboolean` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  `fvname` varchar(32) NOT NULL,
  `fvvalue` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fbref` (`fbref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fvarfloat`
--

DROP TABLE IF EXISTS `fvarfloat`;
CREATE TABLE IF NOT EXISTS `fvarfloat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  `fvname` varchar(32) NOT NULL,
  `fvvalue` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fbref` (`fbref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fvarinteger`
--

DROP TABLE IF EXISTS `fvarinteger`;
CREATE TABLE IF NOT EXISTS `fvarinteger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  `fvname` varchar(32) NOT NULL,
  `fvvalue` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fbref` (`fbref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fvarstring`
--

DROP TABLE IF EXISTS `fvarstring`;
CREATE TABLE IF NOT EXISTS `fvarstring` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `fbref` varchar(32) NOT NULL,
  `fvname` varchar(32) NOT NULL,
  `fvvalue` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fbref` (`fbref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `mapper`
--

DROP TABLE IF EXISTS `mapper`;
CREATE TABLE IF NOT EXISTS `mapper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` int(11) NOT NULL,
  `accesstoken` varchar(32) NOT NULL,
  `reqapproach` varchar(8) NOT NULL,
  `requrl` varchar(256) NOT NULL,
  `reqtimeinterval` int(11) NOT NULL,
  `maps` text NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `mapper`
--

INSERT INTO `mapper` (`id`, `app`, `accesstoken`, `reqapproach`, `requrl`, `reqtimeinterval`, `maps`, `createdon`, `status`) VALUES
(1, 1, 'dd1d8d4bc5bf53b1cade147c245c902f', 'push', 'ignore', 0, 'dfk-sock1 AS NOTIFICATION.DATA.TOPIC,dfk-inter AS NOTIFICATION.DATA.MESSAGE,pk-image AS NOTIFICATION.DATA.CUSTOM.images,pk-sound AS NOTIFICATION.DATA.CUSTOM.sound', '2013-10-09 06:07:15', 1);

-- --------------------------------------------------------

--
-- Table structure for table `mapperfeedback`
--

DROP TABLE IF EXISTS `mapperfeedback`;
CREATE TABLE IF NOT EXISTS `mapperfeedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` int(11) NOT NULL,
  `cond` varchar(256) NOT NULL,
  `checker` varchar(8) NOT NULL,
  `pointer` varchar(32) NOT NULL,
  `stmreference` varchar(32) NOT NULL,
  `doesiton` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `mapperfeedback`
--

INSERT INTO `mapperfeedback` (`id`, `app`, `cond`, `checker`, `pointer`, `stmreference`, `doesiton`) VALUES
(1, 1, 'IF FEEDBACK.userACK GOTO STATEMENT $acknowledge NOW', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `mapperstatement`
--

DROP TABLE IF EXISTS `mapperstatement`;
CREATE TABLE IF NOT EXISTS `mapperstatement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` int(11) NOT NULL,
  `reference` varchar(32) NOT NULL,
  `requrl` varchar(256) NOT NULL,
  `reqdata` text NOT NULL,
  `reqtimeout` int(11) NOT NULL,
  `reqheader` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `mapperstatement`
--

INSERT INTO `mapperstatement` (`id`, `app`, `reference`, `requrl`, `reqdata`, `reqtimeout`, `reqheader`) VALUES
(1, 1, 'acknowledge', 'http://202.12.32:1:8010/command/apis/', 'token:dGVzdHRlc3R0ZXN0dGVzdGhlbGxvd29ybGRtZWFuaWU=,command:ack_acc_3885', 30, 'authen:Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==');

-- --------------------------------------------------------

--
-- Table structure for table `ncond`
--

DROP TABLE IF EXISTS `ncond`;
CREATE TABLE IF NOT EXISTS `ncond` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `repeat_cond` varchar(64) NOT NULL,
  `checker` varchar(8) DEFAULT NULL,
  `pointer` varchar(32) DEFAULT NULL,
  `within` varchar(16) DEFAULT NULL,
  `ndatareference` varchar(32) DEFAULT NULL,
  `doesiton` varchar(32) DEFAULT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ndata`
--

DROP TABLE IF EXISTS `ndata`;
CREATE TABLE IF NOT EXISTS `ndata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notification` varchar(32) NOT NULL,
  `token` varchar(32) NOT NULL,
  `notifytrigger` timestamp NULL DEFAULT NULL,
  `trigger_reference` varchar(32) DEFAULT NULL,
  `trigger_time` varchar(16) DEFAULT NULL,
  `trigger_repeat` varchar(64) DEFAULT NULL,
  `topic` varchar(64) NOT NULL,
  `message` varchar(256) NOT NULL,
  `link` varchar(128) NOT NULL,
  `customios` text NOT NULL,
  `customandroid` text NOT NULL,
  `customcli` text NOT NULL,
  `customhtml5` text NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` int(11) NOT NULL,
  `uid` varchar(32) NOT NULL,
  `type` varchar(8) NOT NULL,
  `domain` varchar(64) NOT NULL,
  `timezone` varchar(32) NOT NULL,
  `platform` varchar(64) NOT NULL,
  `language` varchar(64) NOT NULL,
  `notifytime` varchar(16) NOT NULL,
  `notifyrestrict` varchar(8) NOT NULL,
  `starttime` timestamp NULL DEFAULT NULL,
  `expiretime` timestamp NULL DEFAULT NULL,
  `iosdevice` text NOT NULL,
  `androiddevice` text NOT NULL,
  `keytopic` text NOT NULL,
  `createdby` varchar(8) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fbactivate` tinyint(4) NOT NULL,
  `fbvars` varchar(256) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `nqlsignage`
--

DROP TABLE IF EXISTS `nqlsignage`;
CREATE TABLE IF NOT EXISTS `nqlsignage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `signage` varchar(32) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `signage` (`signage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(32) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `created`, `status`) VALUES
(2, 'My Notifier', 'suthat@do.in.th', '81dc9bdb52d04dc20036dbd8313ed055', '2013-09-10 12:36:19', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
