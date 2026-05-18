-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 23, 2026 at 12:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shoestore`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `brandid` int(11) NOT NULL,
  `brandname` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`brandid`, `brandname`, `country`) VALUES
(2, 'Nike', 'United States'),
(3, 'Adidas', 'Germany'),
(4, 'puma', 'Germany'),
(5, 'paragon ', 'india');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cartid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `shoeid` int(11) NOT NULL,
  `selected_size` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `selected_color` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cartid`, `userid`, `shoeid`, `selected_size`, `quantity`, `selected_color`) VALUES
(10, 1, 16, '8', 1, 'white'),
(11, 1, 16, '12', 9, 'yellow '),
(12, 1, 18, '12', 2, 'blue'),
(13, 1, 13, '11', 2, 'blue'),
(14, 1, 13, '8', 1, ''),
(15, 10, 13, '8', 1, ''),
(16, 15, 17, '8', 1, 'white'),
(17, 15, 14, '8', 1, 'white');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `categoryid` int(11) NOT NULL,
  `categoryname` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`categoryid`, `categoryname`, `description`) VALUES
(1, 'man', 'this is for man'),
(2, 'female', NULL),
(3, 'Athletic Shoes', 'Designed for sports '),
(4, 'Casual Shoes', 'Everyday wear shoes that are comfortable');

-- --------------------------------------------------------

--
-- Table structure for table `color`
--

CREATE TABLE `color` (
  `colorid` int(11) NOT NULL,
  `colorname` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `color`
--

INSERT INTO `color` (`colorid`, `colorname`) VALUES
(4, 'white'),
(6, 'grey'),
(7, 'yellow '),
(10, 'blue'),
(11, 'black');

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

CREATE TABLE `orderdetails` (
  `orderdetailid` int(11) NOT NULL,
  `orderid` int(11) NOT NULL,
  `shoeid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `Price` int(11) NOT NULL,
  `subtotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `orderid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `orderdate` date NOT NULL,
  `totalamount` int(11) NOT NULL,
  `paymentmethod` enum('Cash','Card','Upi','Netbanking') NOT NULL,
  `orderstatus` enum('Placed','Shipped','Delivered','Cancelled') NOT NULL,
  `address_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentid` int(11) NOT NULL,
  `orderid` int(11) NOT NULL,
  `paymentmethod` enum('Card','Upi','Netbanking','Wallet','Cod') NOT NULL,
  `transactionstatus` enum('Pending','Success','Failed','Cancelled','Refunded') NOT NULL,
  `paymentdate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `reviewid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `shoeid` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shoecolor`
--

CREATE TABLE `shoecolor` (
  `shoecolorid` int(11) NOT NULL,
  `shoeid` int(11) NOT NULL,
  `colorid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shoes`
--

CREATE TABLE `shoes` (
  `shoesid` int(11) NOT NULL,
  `shoename` varchar(100) NOT NULL,
  `brandid` int(11) NOT NULL,
  `categoryid` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `image` varchar(500) NOT NULL,
  `description` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `colorid` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shoes`
--

INSERT INTO `shoes` (`shoesid`, `shoename`, `brandid`, `categoryid`, `price`, `image`, `description`, `status`, `colorid`) VALUES
(13, 'RS-X3 Puzzle', 4, 1, 500, 'e42075e5-d248-497d-96b9-e158eaf3919b_download (7).jpg', 'demo', 'Limited', ''),
(14, 'kug', 3, 2, 899, '898706a0-fecc-4d80-b84d-ba9fedbf0cde_images (2).jpg', 'kgjggjgjgjg', 'Available', '1,2,4'),
(16, 'ssa', 2, 3, 900, '3c8ea253-1704-4e0f-9aed-9696991fcd88_download (10).jpg', 'khkjikjhjkj', 'Available', '4,6,7'),
(17, 'congras', 4, 3, 900, '122e270a-3c4c-477e-8861-e3e4c1559b29_download (12).jpg', '', 'Available', '4,7,10'),
(18, 'sd', 5, 1, 354, '1c91b656-563a-44d1-9967-93a78d80ac79_download (11).jpg', '', 'Available', '6,7,10');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userid` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL,
  `phone` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `name`, `email`, `password`, `role`, `phone`) VALUES
(1, 'Krunal Rana', 'hardik@gmail.com', '123', 'Admin', '9601892520'),
(3, 'Krunal', 'krunal@gmail.com', 'krunal123', 'Admin', '9876543210'),
(4, 'krp', 'krp@gmail.com', '123', 'Client', '89898989'),
(5, 'jay', 'kunalrana35463546@gmail.com', '111', 'Admin', '9408978353'),
(10, 'abc', 'c@gmail.com', '123', 'Client', '89898989'),
(13, 'krunal rana', 'krunalrana@gmail.com', '123', 'Admin', '9898989898'),
(14, 'PARTHIK', 'parthik@gmail.com', 'hardik123', 'Admin', '9090909909'),
(15, 'XYZ  ', 'xyz@gmail.com', '123', 'Client', '1235456789');

-- --------------------------------------------------------

--
-- Table structure for table `user_addresses`
--

CREATE TABLE `user_addresses` (
  `addressid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `house_no` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `landmark` varchar(255) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_addresses`
--

INSERT INTO `user_addresses` (`addressid`, `userid`, `fullname`, `mobile`, `pincode`, `house_no`, `area`, `landmark`, `city`, `state`) VALUES
(1, 15, 'XYZ  ', '1235456789', '3223', '34', 'ergerg', 'drgh', 'rg', 'ere'),
(2, 15, 'XYZ  ', '1235456789', '3223', '34', 'ergerg', 'drgh', 'rg', 'ere'),
(3, 15, 'XYZ  ', '1235456789', '3223', '34', 'ergerg', 'drgh', 'rg', 'ere');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`brandid`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cartid`),
  ADD KEY `shoeid` (`shoeid`),
  ADD KEY `userid` (`userid`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`categoryid`);

--
-- Indexes for table `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`colorid`);

--
-- Indexes for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`orderdetailid`),
  ADD KEY `orderid` (`orderid`),
  ADD KEY `shoeid` (`shoeid`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`orderid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `orders_address_fk` (`address_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentid`),
  ADD KEY `orderid` (`orderid`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`reviewid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `shoeid` (`shoeid`);

--
-- Indexes for table `shoecolor`
--
ALTER TABLE `shoecolor`
  ADD PRIMARY KEY (`shoecolorid`),
  ADD KEY `shoeid` (`shoeid`),
  ADD KEY `colorid` (`colorid`);

--
-- Indexes for table `shoes`
--
ALTER TABLE `shoes`
  ADD PRIMARY KEY (`shoesid`),
  ADD KEY `brandid` (`brandid`),
  ADD KEY `categoryid` (`categoryid`),
  ADD KEY `shoes_ibfk_3` (`colorid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `user_addresses`
--
ALTER TABLE `user_addresses`
  ADD PRIMARY KEY (`addressid`),
  ADD KEY `userid` (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brand`
--
ALTER TABLE `brand`
  MODIFY `brandid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cartid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `categoryid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `color`
--
ALTER TABLE `color`
  MODIFY `colorid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `orderdetails`
--
ALTER TABLE `orderdetails`
  MODIFY `orderdetailid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `orderid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shoes`
--
ALTER TABLE `shoes`
  MODIFY `shoesid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `user_addresses`
--
ALTER TABLE `user_addresses`
  MODIFY `addressid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`shoeid`) REFERENCES `shoes` (`shoesid`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `orders` (`orderid`),
  ADD CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`shoeid`) REFERENCES `shoes` (`shoesid`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_address_fk` FOREIGN KEY (`address_id`) REFERENCES `user_addresses` (`addressid`),
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `orders` (`orderid`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`shoeid`) REFERENCES `shoes` (`shoesid`);

--
-- Constraints for table `shoecolor`
--
ALTER TABLE `shoecolor`
  ADD CONSTRAINT `shoecolor_ibfk_1` FOREIGN KEY (`shoeid`) REFERENCES `shoes` (`shoesid`),
  ADD CONSTRAINT `shoecolor_ibfk_2` FOREIGN KEY (`colorid`) REFERENCES `color` (`colorid`);

--
-- Constraints for table `shoes`
--
ALTER TABLE `shoes`
  ADD CONSTRAINT `shoes_ibfk_1` FOREIGN KEY (`brandid`) REFERENCES `brand` (`brandid`),
  ADD CONSTRAINT `shoes_ibfk_2` FOREIGN KEY (`categoryid`) REFERENCES `categories` (`categoryid`);

--
-- Constraints for table `user_addresses`
--
ALTER TABLE `user_addresses`
  ADD CONSTRAINT `address_user_fk` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
