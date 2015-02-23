-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 28, 2014 at 06:30 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `crawler`
--

-- --------------------------------------------------------

--
-- Table structure for table `links`
--

CREATE TABLE IF NOT EXISTS `links` (
`id` bigint(20) NOT NULL,
  `url` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `status` longtext COLLATE utf8_unicode_ci,
  `touched` int(11) NOT NULL,
  `processed_time` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=349 ;

-- --------------------------------------------------------

--
-- Table structure for table `link_content`
--

CREATE TABLE IF NOT EXISTS `link_content` (
`id` bigint(20) NOT NULL,
  `link_id` bigint(20) NOT NULL,
  `content` longtext COLLATE utf8_unicode_ci NOT NULL,
  `title` mediumtext COLLATE utf8_unicode_ci
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=113 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `links`
--
ALTER TABLE `links`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `link_content`
--
ALTER TABLE `link_content`
 ADD PRIMARY KEY (`id`), ADD KEY `link_id` (`link_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `links`
--
ALTER TABLE `links`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=349;
--
-- AUTO_INCREMENT for table `link_content`
--
ALTER TABLE `link_content`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=113;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `link_content`
--
ALTER TABLE `link_content`
ADD CONSTRAINT `fk_link_link_content` FOREIGN KEY (`link_id`) REFERENCES `links` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
