-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 06, 2018 at 02:17 PM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parish`
--

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `dates` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`id`, `name`, `dates`) VALUES
(1, 'adawd', ''),
(2, 'adawd', ''),
(3, 'awdaw', ''),
(4, 'wda', ''),
(5, 'awdaw', ''),
(6, 'awdaw', '2018-07-12'),
(7, '45435', '2018-07-20'),
(8, '5345', '2018-07-20'),
(9, '4654654', '2018-11-16'),
(10, 'awdawd', '2018-08-09'),
(11, 'awdaw', '2018-08-07'),
(12, 'awdawd', '2018-08-10'),
(13, 'awdawd', '2018-08-10'),
(14, 'awdawd', '2018-08-09'),
(15, '453534', '2018-08-10'),
(16, 'awdaw', '2018-08-23'),
(17, 'ssadasd', '2018-09-21'),
(18, 'kererrere', '2018-09-21'),
(19, '123456', '2018-09-18'),
(20, '', '2018-09-30');

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE `requests` (
  `id` int(11) NOT NULL,
  `document_id` int(11) NOT NULL,
  `requestorId` int(11) NOT NULL,
  `rName` varchar(100) NOT NULL,
  `rDate` varchar(25) NOT NULL,
  `nDate` varchar(25) NOT NULL,
  `approved` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`id`, `document_id`, `requestorId`, `rName`, `rDate`, `nDate`, `approved`) VALUES
(1, 0, 0, '', '2018-08-07', '2018-08-02', 0),
(2, 0, 0, '', '2018-08-07', '2018-08-24', 0),
(3, 0, 0, 'John Doe', '2018-08-07', '2018-08-17', 0),
(4, 0, 0, 'John Doe', '2018-08-16', '2018-08-23', 0),
(5, 8, 0, 'John Doe', '2018-08-16', '2018-08-02', 1),
(6, 8, 0, 'John Doe', '2018-08-16', '2018-08-31', 0),
(7, 8, 0, 'John Doe', '2018-08-16', '2018-08-31', 0),
(8, 8, 0, 'John Doe', '2018-08-16', '2018-08-31', 1),
(9, 0, 0, 'John Doe', '2018-08-16', '2018-08-25', 0),
(10, 12, 0, 'John Doe', '2018-08-16', '2018-08-16', 1),
(11, 12, 0, 'John Doe', '2018-08-17', '2018-08-18', 1),
(12, 16, 0, 'John Doe', '2018-08-17', '2018-08-31', 1),
(13, 0, 1, 'awd aawdaw', '2018-08-17', '2018-08-22', 0),
(14, 16, 1, 'awd aawdaw', '2018-08-17', '2018-08-18', 0),
(15, 16, 1, 'awd aawdaw', '2018-08-17', '2018-08-23', 1),
(16, 10, 1, 'awd aawdaw', '2018-08-17', '2018-08-18', 1),
(17, 19, 8, 'gwapo gwapa', '2018-10-02', '2018-10-23', 0),
(18, 19, 8, 'gwapo gwapa', '2018-10-02', '2018-10-23', 0),
(19, 19, 8, 'gwapo gwapa', '2018-10-02', '2018-10-23', 0),
(20, 19, 8, 'gwapo gwapa', '2018-10-02', '2018-10-23', 0),
(21, 19, 8, 'gwapo gwapa', '2018-10-02', '2018-10-23', 1),
(22, 19, 8, 'gwapo gwapa', '2018-10-02', '2018-10-23', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fname` varchar(55) NOT NULL,
  `lname` varchar(55) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fname`, `lname`, `username`, `password`, `role`) VALUES
(2, '44353', '346534', '636', '5764674', 'user'),
(3, 'awdawd', '65474', '7987987', '956464', 'user'),
(4, 'awda7wd7', '9 9789w7a98w7d', '7fef98e7sf98', '7 7ef98sef789es7', 'user'),
(5, 'admin', 'admin', 'admin', 'admin123', 'admin'),
(6, 'kerrrrrrrrrr', 'kerrrrrrrr', '12345', '12345', 'user'),
(7, '121111111', '12222222222', '343434343', '1212212', 'user'),
(8, 'gwapo', 'gwapa', 'gwapa', '12345', 'user'),
(9, 'okapeogij', 'irjgiou', '12335', '1234', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `requests`
--
ALTER TABLE `requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
