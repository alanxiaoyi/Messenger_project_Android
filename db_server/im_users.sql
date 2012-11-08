-- phpMyAdmin SQL Dump
-- version 3.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 29, 2012 at 02:13 AM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `im_users`
--

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` varchar(23) NOT NULL,
  `FriendID` varchar(23) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `UserID` (`UserID`),
  KEY `FriendID` (`FriendID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=42 ;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`uid`, `UserID`, `FriendID`) VALUES
(1, '5010c79d57d0b8.49510466', '5010cd76558717.00853361'),
(32, '5010c79d57d0b8.49510466', '5010cec6c04ea7.35566960'),
(41, '5010c79d57d0b8.49510466', '508b61c28b72d9.35881386');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_id` (`unique_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `email_2` (`email`),
  UNIQUE KEY `unique_id_3` (`unique_id`),
  KEY `unique_id_2` (`unique_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES
(4, '5010c79d57d0b8.49510466', '', 'a', 'kiqf99NJd0c2jBTfYsSEli1qdlg4YWI4Mjg1MmUy', '8ab82852e2', '2012-07-26 00:29:17', NULL),
(9, '5010cd76558717.00853361', 'name', 'b', 'rZ1hIQS6cafDVestbebNnI44Z5VlYzI2MmY2YjZh', 'ec262f6b6a', '2012-07-26 00:54:14', NULL),
(10, '5010cd80519c07.67016607', 'name', 'c', '1Ar4mVfRg/BPDL1V/Q6S/lhfd1wzNGQyMjBkZDEz', '34d220dd13', '2012-07-26 00:54:24', NULL),
(11, '5010cec6c04ea7.35566960', 'name', 'd', 'Eb1Ka3Oh49JhjcMJVnP7K+yGxd0wM2IwZDU4YzRm', '03b0d58c4f', '2012-07-26 00:59:50', NULL),
(12, '508b61c28b72d9.35881386', 'name', 'alan', '0CoHMLXzeMc6YpRvaF3HTC+0a8I3NzIxY2FjZGVi', '7721cacdeb', '2012-10-27 00:23:30', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`unique_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`FriendID`) REFERENCES `users` (`unique_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
