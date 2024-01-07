-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2024 at 10:47 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `signup`
--

-- --------------------------------------------------------

--
-- Table structure for table `blog`
--

CREATE TABLE `blog` (
  `id` int(12) NOT NULL,
  `title` varchar(255) NOT NULL,
  `news` mediumtext DEFAULT NULL,
  `blogs` mediumtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `blog`
--

INSERT INTO `blog` (`id`, `title`, `news`, `blogs`) VALUES
(52, 'A change in blockchain industry', 'China has adopted Crypto currencys', ''),
(57, 'On 21/6/2023', 'A project submission is held', 'At Dypsomca'),
(58, 'A new seminar held on thursday', 'On chat gpt', 'At Dypsomca');

-- --------------------------------------------------------

--
-- Table structure for table `mentor`
--

CREATE TABLE `mentor` (
  `id` int(12) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `cpassword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mentor`
--

INSERT INTO `mentor` (`id`, `username`, `email`, `password`, `cpassword`) VALUES
(6, 'Avni Kulkarni', 'avnimam123@gmail.com', '$2y$10$dZxSz2UVhNkD29j9YxSpheORkoriZbt86dcE/WBXoZTnMw8cp69t.', '$2y$10$nVo3QtDz8OCJTawTPoWwDOHCsv4AL1Fu3uaoY7.T0TUgGp//Jz10K'),
(5, 'Yash Thakkar', 'yashthakkar7595@gmail.com', '$2y$10$/E4XxMrV.AGb9BZzZOhKmu1fzQ0UR1moibLF2RGwjFdfHeg7ks21i', '$2y$10$XWJy4sYJCw.NYhXhuPOYo.RcI3PNFWeevXe1RsQlZMrSbOcHySwJG');

-- --------------------------------------------------------

--
-- Table structure for table `profilem`
--

CREATE TABLE `profilem` (
  `user_id` int(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile` varchar(11) NOT NULL,
  `education` varchar(255) NOT NULL,
  `post` varchar(255) NOT NULL,
  `special` varchar(255) NOT NULL,
  `branch` varchar(255) NOT NULL,
  `img` varchar(10000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profilem`
--

INSERT INTO `profilem` (`user_id`, `name`, `email`, `mobile`, `education`, `post`, `special`, `branch`, `img`) VALUES
(23, 'Avni Kulkarni', 'avnimam123@gmail.com', '9821214125', 'MCA', 'H.O.D of BCA dept', 'DBMS', ' Computer science', 'Avnimam.png'),
(28, 'Yash', 'yashthakkar7595@gmail.com', '09834464841', 'MCA', 'H.O.D of MCA', 'ADBMS', ' Computer science', 'BeautyPlus_20190815125146013_save.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `profiles`
--

CREATE TABLE `profiles` (
  `id` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Mobile` varchar(255) NOT NULL,
  `Roll_no` int(255) NOT NULL,
  `Project` varchar(255) NOT NULL,
  `Education` varchar(255) NOT NULL,
  `stream` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profiles`
--

INSERT INTO `profiles` (`id`, `Name`, `Email`, `Mobile`, `Roll_no`, `Project`, `Education`, `stream`, `image`) VALUES
(0, 'Yash', 'yashthakkar7595@gmail.com', '09834464841', 124, '2', 'MCA', 'Science ', 'BeautyPlus_20190815125146013_save.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `user_id` int(12) NOT NULL,
  `title` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `stream` varchar(255) NOT NULL,
  `team` int(12) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Mobile` varchar(12) NOT NULL,
  `requirement` mediumtext NOT NULL,
  `skills` mediumtext NOT NULL,
  `exp` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`user_id`, `title`, `category`, `description`, `stream`, `team`, `Name`, `Email`, `Mobile`, `requirement`, `skills`, `exp`) VALUES
(44, 'Uprise', 'Web Application', 'A platform of ideas', 'Science ', 2, 'Yash', 'yashthakkar7595@gmail.com', '09834464841', 'Developer', 'JS,java,python', '3 Years');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` int(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `cpassword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `username`, `email`, `password`, `cpassword`) VALUES
(12, 'Shreyatlee dukari', 'mitanshikedia5@gmail.com', '$2y$10$pWx/FovCyGqID06lOtfsg.PwGq9fnDih6CSvXfXrW83QDG1v81O3i', '$2y$10$fAkft42oNMrUqB8YXXDLZOWiK7347LYGJ5yx1C/1/I60gg1W/oHui'),
(9, 'Mitali Panjawani', 'mitu715@gmail.com', '$2y$10$M4tRPNemAy8TeTBrktJnw.fP.m3r/RpZ6t1B.5E4JsizgdvE4I6Li', '$2y$10$MjOo/akZyFcRPJpjczFIQ.qcFLBdAcUTxWgypkht9vGFqMph3zhEK'),
(10, 'Uh nagarkar', 'nagarkar@gmail.com', '$2y$10$fQJ82WvZODmVo8gRVAAeieB2kOhiy6SZynlzcskpOjtqP3aXV.PKy', '$2y$10$DFCvthcjMnHagtCw/42bDOnOCUMqDQuFh/.C9eWvVBEuBmgaAFGWC'),
(8, 'Yash Thakkar', 'yashthakkar7595@gmail.com', '$2y$10$TSbLGwVS.uZbYgePikb7OehJfoNPBnekSqaMBGf3ve5w5s9vhn8ce', '$2y$10$AIx6bXlLscdwk81gkCNS4u.Q3SNTUWPmSCbhvpPSUgCjvWIqB/wh.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mentor`
--
ALTER TABLE `mentor`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `profilem`
--
ALTER TABLE `profilem`
  ADD PRIMARY KEY (`email`),
  ADD KEY `id` (`user_id`),
  ADD KEY `id_2` (`user_id`);

--
-- Indexes for table `profiles`
--
ALTER TABLE `profiles`
  ADD PRIMARY KEY (`Email`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`email`),
  ADD KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blog`
--
ALTER TABLE `blog`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `mentor`
--
ALTER TABLE `mentor`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `profilem`
--
ALTER TABLE `profilem`
  MODIFY `user_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `user_id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
