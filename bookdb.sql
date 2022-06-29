-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2022-06-29 18:03:09
-- 服务器版本： 8.0.24
-- PHP 版本： 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `bookdb`
--

-- --------------------------------------------------------

--
-- 表的结构 `adminlogin`
--

CREATE TABLE `adminlogin` (
  `ID` int NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `UserPass` varchar(18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `adminlogin`
--

INSERT INTO `adminlogin` (`ID`, `UserName`, `UserPass`) VALUES
(1, '123', '123');

-- --------------------------------------------------------

--
-- 表的结构 `bookinfo`
--

CREATE TABLE `bookinfo` (
  `Book_ID` int NOT NULL COMMENT '编号',
  `Book_Name` varchar(50) NOT NULL COMMENT '图书名称 ',
  `Book_Num` bigint NOT NULL COMMENT '图书编码(ISBN)',
  `Book_Author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书作者',
  `Book_Press` varchar(10) NOT NULL COMMENT '图书出版社',
  `Book_Price` decimal(5,2) NOT NULL COMMENT '图书价格',
  `Book_Page` int DEFAULT NULL COMMENT '图书页码',
  `ReturnBook` int DEFAULT '0' COMMENT '归还/租借识别 0为未借 1为已借',
  `LendUser` varchar(50) DEFAULT NULL COMMENT '租借用户',
  `LendTime` datetime DEFAULT NULL COMMENT '借书时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `bookinfo`
--

INSERT INTO `bookinfo` (`Book_ID`, `Book_Name`, `Book_Num`, `Book_Author`, `Book_Press`, `Book_Price`, `Book_Page`, `ReturnBook`, `LendUser`, `LendTime`) VALUES
(32, 'Java核心技术 卷I 基础知识', 9787115504920, '凯·S.,霍斯特曼[美]', '人民邮电出版社', '149.00', NULL, 0, NULL, NULL),
(33, 'Java核心技术 卷II 高级特性 ', 9787115526410, '凯·S.,霍斯特曼[美]', '人民邮电出版社', '159.00', NULL, 0, NULL, NULL),
(35, 'Java代码审计.入门篇', 9787115565549, '徐焱', '人民邮电出版社', '129.00', NULL, 0, NULL, NULL),
(36, '第一行代码 Java', 9787115448156, '李兴华,马云涛', '人民邮电出版社', '89.00', NULL, 0, NULL, NULL),
(37, '深入Java虚拟机', 9787115554529, '中村成洋[日]', '人民邮电出版社', '59.00', NULL, 0, NULL, NULL),
(38, '明解Java', 9787115471857, '柴田望洋[日]', '人民邮电出版社', '99.00', NULL, 0, NULL, NULL),
(39, '网络是怎样连接的', 9787115441249, '户根勤[日]', '人民邮电出版社', '49.00', NULL, 0, NULL, NULL),
(40, 'C#图解教程', 9787115519184, '丹尼尔·索利斯[美]', '人民邮电出版社', '129.00', NULL, 0, NULL, NULL),
(41, 'Go语言高级编程', 9787115510365, '柴树杉,曹春晖', '人民邮电出版社', '89.00', NULL, 0, NULL, NULL),
(42, '黑客攻防技术宝典', 9787115283924, '斯图塔德[英]', '人民邮电出版社', '119.80', NULL, 0, NULL, NULL),
(43, 'Kali Linux2 网络渗透测试', 9787115555410, '李华峰', '人民邮电出版社', '89.00', NULL, 0, NULL, NULL),
(44, '黑客之道-漏洞发掘的艺术', 9787115535559, '乔恩·埃里克森[美]', '人民邮电出版社', '119.00', NULL, 0, NULL, NULL),
(45, 'Wireshark网络分析从入门到实践', 9787115505224, '李华峰,陈虹', '人民邮电出版社', '59.00', NULL, 0, NULL, NULL),
(46, 'DirectX 12 3D 游戏开发实战', 9787115479211, '弗兰克·D.卢娜[美]', '人民邮电出版社', '148.00', NULL, 0, NULL, NULL),
(47, 'Python编程快速上手', 9787115422699, 'Al Sweigart 斯维加特[美]', '人民邮电出版社', '55.20', NULL, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `userlogin`
--

CREATE TABLE `userlogin` (
  `ID` int NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `UserPass` varchar(18) NOT NULL,
  `UserMail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `userlogin`
--

INSERT INTO `userlogin` (`ID`, `UserName`, `UserPass`, `UserMail`) VALUES
(1, '123123', '123123', '');

--
-- 转储表的索引
--

--
-- 表的索引 `adminlogin`
--
ALTER TABLE `adminlogin`
  ADD PRIMARY KEY (`ID`);

--
-- 表的索引 `bookinfo`
--
ALTER TABLE `bookinfo`
  ADD PRIMARY KEY (`Book_ID`),
  ADD UNIQUE KEY `Book_Num` (`Book_Num`);

--
-- 表的索引 `userlogin`
--
ALTER TABLE `userlogin`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `UserName` (`UserName`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `adminlogin`
--
ALTER TABLE `adminlogin`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `bookinfo`
--
ALTER TABLE `bookinfo`
  MODIFY `Book_ID` int NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=54;

--
-- 使用表AUTO_INCREMENT `userlogin`
--
ALTER TABLE `userlogin`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
