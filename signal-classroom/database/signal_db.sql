-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `signal_db`
--

-- --------------------------------------------------------

--
-- โครงสร้างตาราง `feedbacks`
--

CREATE TABLE IF NOT EXISTS `feedbacks` (
  `id` varchar(32) NOT NULL,
  `notification` varchar(32) NOT NULL,
  `token` varchar(32) NOT NULL,
  `session` varchar(32) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- โครงสร้างตาราง `fvars`
--

CREATE TABLE IF NOT EXISTS `fvars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `feedback` varchar(32) NOT NULL,
  `variable` varchar(16) NOT NULL,
  `value` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- โครงสร้างตาราง `notifications`
--

CREATE TABLE IF NOT EXISTS `notifications` (
  `id` varchar(32) NOT NULL,
  `app_id` varchar(16) NOT NULL,
  `type` varchar(16) NOT NULL,
  `domain` varchar(16) NOT NULL,
  `platform` varchar(16) NOT NULL,
  `notifytime` varchar(32) NOT NULL,
  `restrict` varchar(8) NOT NULL,
  `topic` varchar(32) NOT NULL,
  `message` varchar(256) NOT NULL,
  `link` varchar(256) NOT NULL,
  `push_data` text NOT NULL,
  `feedback_vars` varchar(100) NOT NULL,
  `token` varchar(32) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pushed_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- โครงสร้างตาราง `sessions`
--

CREATE TABLE IF NOT EXISTS `sessions` (
  `session_id` varchar(40) NOT NULL DEFAULT '0',
  `ip_address` varchar(45) NOT NULL DEFAULT '0',
  `user_agent` varchar(120) NOT NULL,
  `last_activity` int(10) unsigned NOT NULL DEFAULT '0',
  `user_data` text NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `last_activity_idx` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- โครงสร้างตาราง `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(16) NOT NULL,
  `name` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(32) NOT NULL,
  `token` varchar(32) NOT NULL,
  `app_title` varchar(64) NOT NULL,
  `app_excerpt` varchar(256) NOT NULL,
  `app_id` varchar(16) NOT NULL,
  `app_secret` varchar(32) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- dump ตาราง `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `token`, `app_title`, `app_excerpt`, `app_id`, `app_secret`, `created_at`) VALUES
('5835102629050699', 'Classroom Project SIT', 'classroom@sit.kmutt.ac.th', 'c756662595bb0948d9bbe352fef1c53a', '68cbe8dedd0e6b89339e0a0125c6f1ca', 'Push To Student', 'Push questions to students in the class via HTML5', '3422841103384567', 'cef9d37913b8449d9610f4f653f0ef01', '2014-09-08 08:12:14');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
