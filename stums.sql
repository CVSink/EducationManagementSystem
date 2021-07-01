/*
Navicat MySQL Data Transfer

Source Server         : DataBaseAssignment
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : stums

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2021-07-01 18:59:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adminuser
-- ----------------------------
DROP TABLE IF EXISTS `adminuser`;
CREATE TABLE `adminuser` (
  `ID` int NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminuser
-- ----------------------------
INSERT INTO `adminuser` VALUES ('12345', '12345');
INSERT INTO `adminuser` VALUES ('67890', '123456');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cID` int NOT NULL,
  `cMajor` varchar(255) DEFAULT NULL,
  `cName` varchar(255) DEFAULT NULL,
  `cType` varchar(255) DEFAULT NULL,
  `cStartTerm` varchar(255) DEFAULT NULL,
  `cPeriod` varchar(255) DEFAULT NULL,
  `cCredit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '计算机科学与技术', '数据结构', '必修', '第三学期', '65', '5');
INSERT INTO `course` VALUES ('2', '计算机科学与技术', '离散数学', '必修', '第三学期', '48', '3.5');
INSERT INTO `course` VALUES ('3', '计算机科学与技术', '微积分A1', '必修', '第一学期', '64', '3.5');
INSERT INTO `course` VALUES ('4', '计算机科学与技术', '计算机网络', '必修', '第五学期', '48', '3');
INSERT INTO `course` VALUES ('5', '软件工程', '操作系统', '必修', '第五学期', '48', '4');
INSERT INTO `course` VALUES ('6', '软件工程', '计算机组成原理', '必修', '第二学期', '48', '4');
INSERT INTO `course` VALUES ('7', '软件工程', '线性代数', '必修', '第一学期', '64', '3');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `stuID` int DEFAULT NULL,
  `cID` int DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `gpa` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('21180101', '2', '70 ', '2.0');
INSERT INTO `score` VALUES ('21180101', '3', '80', '3.0');
INSERT INTO `score` VALUES ('21180101', '4', '60', '1.0');
INSERT INTO `score` VALUES ('21180102', '1', '94', '4.0');
INSERT INTO `score` VALUES ('21180102', '2', '85', '3.3');
INSERT INTO `score` VALUES ('21180102', '3', '59', '0');
INSERT INTO `score` VALUES ('21180102', '4', '59', '0');
INSERT INTO `score` VALUES ('21180103', '1', '80', '3.0');
INSERT INTO `score` VALUES ('21180103', '2', '100', '4.0');
INSERT INTO `score` VALUES ('21180103', '3', '60', '1.0');
INSERT INTO `score` VALUES ('21180103', '4', '82', '3.0');
INSERT INTO `score` VALUES ('21180104', '1', '90', '4.0');
INSERT INTO `score` VALUES ('21180105', '1', '100', '4.0');
INSERT INTO `score` VALUES ('21180106', '1', '0', '0');
INSERT INTO `score` VALUES ('21180107', '1', '90', '4.0');
INSERT INTO `score` VALUES ('21180108', '1', '84', '3.3');
INSERT INTO `score` VALUES ('21180109', '1', '90', '4.0');
INSERT INTO `score` VALUES ('21180110', '1', '90', '4.0');
INSERT INTO `score` VALUES ('21180101', '5', '80', '3.0');
INSERT INTO `score` VALUES ('21180101', '6', '85', '3.3');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stuID` int NOT NULL,
  `stuClass` varchar(255) DEFAULT NULL,
  `stuName` varchar(255) DEFAULT NULL,
  `stuSex` varchar(255) DEFAULT NULL,
  `stuBirth` varchar(255) DEFAULT NULL,
  `stuMajor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('21180101', '3', '张三', '女', '2010.1.10', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180102', '1', '李四', '女', '2000.3.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180103', '1', '王五', '女', '2000.1.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180104', '1', '李五', '女', '1999.3.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180105', '1', '李六', '女', '2000.1.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180106', '1', '李七', '女', '2000.4.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180107', '1', '小王', '女', '2001.1.2', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180108', '1', '小张', '女', '1999.4.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180109', '1', '小李', '男', '1999.2.2', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180110', '1', '小林', '男', '1999.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180111', '2', '吴云鹏', '男', '2001.9.9', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180112', '2', '李云鹏', '男', '2000.8.6', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180113', '2', '张云鹏', '男', '2002.7.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180114', '2', '胡云鹏', '男', '2002.6.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180115', '2', '郭云鹏', '男', '1999.8.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180116', '3', '小郭', '男', '1999.5.13', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180117', '3', '小杨', '男', '2002.8.29', '计算机科学与技术');
INSERT INTO `student` VALUES ('21180118', '3', '小胡', '男', '2000.3.3', '软件工程');
INSERT INTO `student` VALUES ('21180119', '3', '小温', '男', '2000.11.11', '软件工程');
INSERT INTO `student` VALUES ('21180120', '3', '小伟', '男', '2000.12.12', '软件工程');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teaID` int NOT NULL,
  `teaName` varchar(255) DEFAULT NULL,
  `teaSex` varchar(255) DEFAULT NULL,
  `teaBirth` varchar(255) DEFAULT NULL,
  `teaMajor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`teaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('123', '李老师', '男', '2000.1.3', '计算机');
INSERT INTO `teacher` VALUES ('1234', '吴老师', '男', '1949.1.1', '计算机');
INSERT INTO `teacher` VALUES ('12345', '张教授', '男', '1950.1.1', '软件');
