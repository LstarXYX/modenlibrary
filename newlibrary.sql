/*
Navicat MySQL Data Transfer

Source Server         : Lstar
Source Server Version : 80020
Source Host           : localhost:3306
Source Database       : newlibrary

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-05-23 21:55:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blacklist
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist` (
  `uid` int NOT NULL COMMENT '用户的id',
  `uname` varchar(255) NOT NULL COMMENT '用户名',
  `cdate` datetime NOT NULL COMMENT '拉入黑名单的日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `isbn` varchar(10) NOT NULL COMMENT '图书的isbn号 总共11位',
  `title` varchar(255) NOT NULL COMMENT '书名 不能为空',
  `author` varchar(255) NOT NULL COMMENT '作者名 不能为空',
  `publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出版社',
  `publish_date` datetime NOT NULL COMMENT '图书的出版日期',
  `category_id` int DEFAULT NULL COMMENT '图书类型的id',
  `price` decimal(5,2) DEFAULT NULL COMMENT '该图书的价格',
  `description` text COMMENT '图书的简介',
  `book_num` int DEFAULT '1' COMMENT '书本的数量 默认1',
  `img_path` varchar(255) DEFAULT NULL COMMENT '图书图片的地址',
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1111111111', '有话说出来！（彻底颠覆社会人脉的固有方式，社交电池帮你搞定社交。社交恐惧症患者必须拥有的一本实用社交指南，初入大学和职场的必备“攻略”，拿起这本书，你也是“魏璎珞”）纤阅出品', '帕特里克・金', null, '2018-12-13 00:00:00', '12', '31.30', 'test1', '0', 'http://img3m8.ddimg.cn/8/26/25345988-1_l_1.jpg');
INSERT INTO `book` VALUES ('1111111112', '乌合之众：群体心理研究（精装珍藏全译本）', '古斯塔夫・勒庞', null, '2018-12-20 00:00:00', '15', '39.20', 'test2', '0', 'http://img3m6.ddimg.cn/66/12/28540776-1_l_3.jpg');
INSERT INTO `book` VALUES ('1111111113', '尤尔小屋的猫', '莉莉・海沃德', null, '2018-10-08 00:00:00', '22', '33.90', 'test3', '1', 'http://img3m0.ddimg.cn/33/33/25197810-1_l_3.jpg');
INSERT INTO `book` VALUES ('1111111114', '谋杀鉴赏（《读者的选择》最佳小说奖、国际推理小说“安东尼”奖提名。）', '莉比・菲舍尔・赫尔曼', null, '2019-01-01 00:00:00', '9', '28.30', 'test4', '1', 'http://img3m6.ddimg.cn/2/27/24157586-1_l_7.jpg');
INSERT INTO `book` VALUES ('1111111115', '谜案鉴赏', '莉比・菲舍尔・赫尔曼', null, '2018-06-23 00:00:00', '3', '37.10', 'test5', '1', 'http://img3m1.ddimg.cn/66/36/28470981-1_l_3.jpg');
INSERT INTO `book` VALUES ('1111111116', '空间简史(教育部推荐小学5、6年级必读书目，与《时间简史》《人类简史》《未来简史》并称“四大简史”)', '托马斯・马卡卡罗', null, '2018-05-18 00:00:00', '17', '34.70', 'test6', '1', 'http://img3m1.ddimg.cn/86/2/25546541-1_l_9.jpg');
INSERT INTO `book` VALUES ('1111111118', '程俊锋的熬夜心得', 'lstar', null, '2020-12-05 00:00:00', '13', null, null, '3', 'http://lstar.com/upload/imgs/程俊锋的熬夜心得6cf226e4d2524cfdac86093522b6643d.jpeg');
INSERT INTO `book` VALUES ('1111111119', '程俊锋的熬夜心得2', '程俊锋', null, '2020-12-06 00:00:00', '11', null, null, '3', null);
INSERT INTO `book` VALUES ('1111111120', 'ss', 'L.star', null, '2020-12-05 00:00:00', '11', null, null, '3', 'http://lstar.com/upload/imgs/ss0323eb2cb5184ccf90cab1f1671d84bc.jpeg');
INSERT INTO `book` VALUES ('1111111141', 'demoData', 'demoData', null, '2020-12-12 00:00:00', '1', null, null, null, null);
INSERT INTO `book` VALUES ('1111111142', 'demoData', 'demoData', null, '2020-12-12 00:00:00', '1', null, null, null, null);
INSERT INTO `book` VALUES ('1129374012', 'excel', 'excel', 'aaa', '2020-12-12 00:00:00', '1', '22.00', '描述', '11', null);
INSERT INTO `book` VALUES ('1234561234', 'excel2', 'excel', 'bbb', '2021-12-30 00:00:00', '2', '23.00', 'sdadasdasdasd', '22', null);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '马克思主义、列宁主义、毛泽东思想、邓小平理论');
INSERT INTO `category` VALUES ('2', '哲学、宗教');
INSERT INTO `category` VALUES ('3', '社会科学总论');
INSERT INTO `category` VALUES ('4', '政治、法律');
INSERT INTO `category` VALUES ('5', '军事');
INSERT INTO `category` VALUES ('6', '经济');
INSERT INTO `category` VALUES ('7', '文化、科学、教育、体育');
INSERT INTO `category` VALUES ('8', '语言、文字');
INSERT INTO `category` VALUES ('9', '文学');
INSERT INTO `category` VALUES ('10', '艺术');
INSERT INTO `category` VALUES ('11', '历史、地理');
INSERT INTO `category` VALUES ('12', '自然科学总论');
INSERT INTO `category` VALUES ('13', '数理科学和化学');
INSERT INTO `category` VALUES ('14', '天文学、地球科学');
INSERT INTO `category` VALUES ('15', '生物科学');
INSERT INTO `category` VALUES ('16', '医药、卫生');
INSERT INTO `category` VALUES ('17', '农业科学');
INSERT INTO `category` VALUES ('18', '工业技术');
INSERT INTO `category` VALUES ('19', '交通运输');
INSERT INTO `category` VALUES ('20', '航空、航天');
INSERT INTO `category` VALUES ('21', '环境科学、劳动保护科学');
INSERT INTO `category` VALUES ('22', '综合性图书');

-- ----------------------------
-- Table structure for lendhistory
-- ----------------------------
DROP TABLE IF EXISTS `lendhistory`;
CREATE TABLE `lendhistory` (
  `cdate` date DEFAULT NULL COMMENT '记录日期',
  `people` int DEFAULT NULL COMMENT '借阅人数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lendhistory
-- ----------------------------
INSERT INTO `lendhistory` VALUES ('2020-12-10', '11');
INSERT INTO `lendhistory` VALUES ('2020-12-06', '13');
INSERT INTO `lendhistory` VALUES ('2020-11-07', '13');
INSERT INTO `lendhistory` VALUES ('2020-11-08', '22');
INSERT INTO `lendhistory` VALUES ('2021-01-13', '0');

-- ----------------------------
-- Table structure for lend_list
-- ----------------------------
DROP TABLE IF EXISTS `lend_list`;
CREATE TABLE `lend_list` (
  `isbn` varchar(10) NOT NULL COMMENT '图书isbn',
  `title` varchar(255) NOT NULL COMMENT '书名',
  `uid` int NOT NULL COMMENT '借书人id',
  `lend_date` datetime DEFAULT NULL COMMENT '借出日期',
  `return_date` datetime DEFAULT NULL COMMENT '归还日期',
  `status` varchar(255) DEFAULT '归还' COMMENT '书本状态 借出 归还或其他'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lend_list
-- ----------------------------
INSERT INTO `lend_list` VALUES ('1111111112', '有话说出来！（彻底颠覆社会人脉的固有方式，社交电池帮你搞定社交。社交恐惧症患者必须拥有的一本实用社交指南，初入大学和职场的必备“攻略”，拿起这本书，你也是“魏璎珞”）纤阅出品', '112', '2020-12-25 21:56:03', '2020-12-24 13:17:14', '借出');
INSERT INTO `lend_list` VALUES ('1111111113', '有话说出来！（彻底颠覆社会人脉的固有方式，社交电池帮你搞定社交。社交恐惧症患者必须拥有的一本实用社交指南，初入大学和职场的必备“攻略”，拿起这本书，你也是“魏璎珞”）纤阅出品', '113', '2020-12-25 21:56:03', '2020-12-24 13:17:14', '借出');
INSERT INTO `lend_list` VALUES ('1111111111', '有话说出来！（彻底颠覆社会人脉的固有方式，社交电池帮你搞定社交。社交恐惧症患者必须拥有的一本实用社交指南，初入大学和职场的必备“攻略”，拿起这本书，你也是“魏璎珞”）纤阅出品', '111', '2021-05-09 11:02:25', '2021-05-09 11:02:24', '借出');
INSERT INTO `lend_list` VALUES ('1111111112', '乌合之众：群体心理研究（精装珍藏全译本）', '111', '2021-05-09 11:01:11', '2021-05-09 10:55:09', '借出');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) DEFAULT NULL COMMENT 'like:employee:list',
  `permission_desc` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `permission_url` varchar(255) DEFAULT NULL COMMENT '资源的url',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('100', 'lend', '借书', '/book/lend');
INSERT INTO `permission` VALUES ('101', 'return', '还书', '/book/return');
INSERT INTO `permission` VALUES ('102', 'addAdmin', '增加管理员', '/admin/addAdmin');
INSERT INTO `permission` VALUES ('103', 'delAdmin', '删除管理员', '/admin/delAdmin');
INSERT INTO `permission` VALUES ('104', 'addUser', '增加用户', '/admin/adduser');
INSERT INTO `permission` VALUES ('105', 'delUser', '删除用户', '/admin/deluser');
INSERT INTO `permission` VALUES ('106', 'updateUser', '修改用户信息', '/admin/updateuser');
INSERT INTO `permission` VALUES ('107', 'queryUser', '查询用户', '/admin/queryuser');
INSERT INTO `permission` VALUES ('108', 'queryUserLendInfo', '查询所有用户借阅信息', '/lendlist/queryAll');
INSERT INTO `permission` VALUES ('109', 'updateUserLendInfo', '修改用户借阅信息', '/reader/updatelendinfo');
INSERT INTO `permission` VALUES ('110', 'addblack', '添加黑名单', '/admin/addblack');
INSERT INTO `permission` VALUES ('111', 'delblack', '移除黑名单', '/admin/delblack');
INSERT INTO `permission` VALUES ('112', 'lendInfo', '读者借书信息查询', '/user/lendlist');
INSERT INTO `permission` VALUES ('113', 'querybook', '查询图书信息', '/book/querybook');
INSERT INTO `permission` VALUES ('114', 'updatebook', '更新图书信息', '/book/updatebook');
INSERT INTO `permission` VALUES ('115', 'delbook', '删除图书', '/book/delbook');
INSERT INTO `permission` VALUES ('116', 'listblack', '查看黑名单', '/black/list');
INSERT INTO `permission` VALUES ('117', 'info', '用户个人信息', '/user/info');
INSERT INTO `permission` VALUES ('118', 'addbook', '添加图书', '/book/add');
INSERT INTO `permission` VALUES ('119', 'sendAll', '群发消息', '/im/sendall');

-- ----------------------------
-- Table structure for quartzjobs
-- ----------------------------
DROP TABLE IF EXISTS `quartzjobs`;
CREATE TABLE `quartzjobs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `jobName` varchar(255) NOT NULL,
  `jobClass` varchar(255) NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `cronExpression` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of quartzjobs
-- ----------------------------
INSERT INTO `quartzjobs` VALUES ('1', 'saveTask', 'modenlibrary.scheduled.task.saveTask', '1', '0 50 23 * * ?');
INSERT INTO `quartzjobs` VALUES ('2', 'updateTask', 'modenlibrary.scheduled.task.updateTask', '0', '0 0 0/1 * * ?');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '拥有全部权限');
INSERT INTO `role` VALUES ('2', '普通管理员', '日常管理图书馆');
INSERT INTO `role` VALUES ('3', '读者', '借书的人');
INSERT INTO `role` VALUES ('4', '黑名单用户', '被拉入黑名单的用户 ');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `r_id` int NOT NULL,
  `p_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '100');
INSERT INTO `role_permission` VALUES ('2', '1', '101');
INSERT INTO `role_permission` VALUES ('3', '1', '102');
INSERT INTO `role_permission` VALUES ('4', '1', '103');
INSERT INTO `role_permission` VALUES ('5', '1', '104');
INSERT INTO `role_permission` VALUES ('6', '1', '105');
INSERT INTO `role_permission` VALUES ('7', '1', '106');
INSERT INTO `role_permission` VALUES ('8', '1', '107');
INSERT INTO `role_permission` VALUES ('9', '1', '108');
INSERT INTO `role_permission` VALUES ('10', '1', '109');
INSERT INTO `role_permission` VALUES ('11', '1', '110');
INSERT INTO `role_permission` VALUES ('12', '1', '111');
INSERT INTO `role_permission` VALUES ('13', '1', '112');
INSERT INTO `role_permission` VALUES ('14', '1', '113');
INSERT INTO `role_permission` VALUES ('15', '1', '114');
INSERT INTO `role_permission` VALUES ('16', '1', '115');
INSERT INTO `role_permission` VALUES ('17', '1', '116');
INSERT INTO `role_permission` VALUES ('18', '2', '100');
INSERT INTO `role_permission` VALUES ('19', '2', '101');
INSERT INTO `role_permission` VALUES ('20', '2', '104');
INSERT INTO `role_permission` VALUES ('21', '2', '105');
INSERT INTO `role_permission` VALUES ('22', '2', '106');
INSERT INTO `role_permission` VALUES ('23', '2', '107');
INSERT INTO `role_permission` VALUES ('24', '2', '108');
INSERT INTO `role_permission` VALUES ('25', '2', '109');
INSERT INTO `role_permission` VALUES ('26', '2', '110');
INSERT INTO `role_permission` VALUES ('27', '2', '111');
INSERT INTO `role_permission` VALUES ('28', '2', '112');
INSERT INTO `role_permission` VALUES ('29', '2', '113');
INSERT INTO `role_permission` VALUES ('30', '2', '114');
INSERT INTO `role_permission` VALUES ('31', '2', '115');
INSERT INTO `role_permission` VALUES ('32', '2', '116');
INSERT INTO `role_permission` VALUES ('33', '3', '100');
INSERT INTO `role_permission` VALUES ('34', '3', '101');
INSERT INTO `role_permission` VALUES ('35', '3', '112');
INSERT INTO `role_permission` VALUES ('36', '3', '113');
INSERT INTO `role_permission` VALUES ('37', '3', '116');
INSERT INTO `role_permission` VALUES ('38', '1', '118');
INSERT INTO `role_permission` VALUES ('39', '2', '118');
INSERT INTO `role_permission` VALUES ('40', '1', '119');
INSERT INTO `role_permission` VALUES ('41', '2', '119');
INSERT INTO `role_permission` VALUES ('42', '4', '101');
INSERT INTO `role_permission` VALUES ('43', '4', '106');
INSERT INTO `role_permission` VALUES ('44', '4', '112');
INSERT INTO `role_permission` VALUES ('45', '4', '116');
INSERT INTO `role_permission` VALUES ('46', '1', '117');
INSERT INTO `role_permission` VALUES ('47', '2', '117');
INSERT INTO `role_permission` VALUES ('48', '3', '117');
INSERT INTO `role_permission` VALUES ('49', '4', '117');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL COMMENT '用户id',
  `oper` varchar(255) DEFAULT NULL COMMENT '操作',
  `method` varchar(255) DEFAULT NULL COMMENT '方法名',
  `params` varchar(255) DEFAULT NULL COMMENT '参数',
  `createDate` date DEFAULT NULL COMMENT '操作时间',
  `ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', '111', '/添加用户', 'modenlibrary.controller.UserController.add', '[\"anno\",\"test\",1]', '2020-12-26', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('2', '111', '/添加用户', 'modenlibrary.controller.UserController.del', '[\"anno\",\"test\",1]', '2020-12-26', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('3', '112', '/借阅图书', 'modenlibrary.controller.BookController.lend', '[\"1111111112\",{\"servletContext\":{},\"session\":{}}]', '2020-12-27', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('4', '111', '/添加用户', 'modenlibrary.controller.UserController.add', '[\"testadd\",\"testadd\",0]', '2021-01-04', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('5', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('6', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('7', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('8', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('9', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('10', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('11', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('12', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('13', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-07', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('14', '111', '/更新用户信息', 'modenlibrary.controller.UserController.update', '[{\"password\":\"698d51a19d8a121ce581499d7b701668\",\"id\":112},{\"servletContext\":{},\"session\":{}}]', '2021-01-07', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('15', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-07', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('16', '111', '/更新用户信息', 'modenlibrary.controller.UserController.update', '[{\"password\":\"698d51a19d8a121ce581499d7b701668\",\"id\":112,\"registerDate\":1607702400000},{\"servletContext\":{},\"session\":{}}]', '2021-01-07', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('17', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-08', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('18', '111', '/借阅图书', 'modenlibrary.controller.BookController.lend', '[\"1111111112\",{\"servletContext\":{},\"session\":{}}]', '2021-01-08', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('19', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('20', '111', '/添加图书', 'modenlibrary.controller.BookController.add', '[{\"author\":\"demoData\",\"isbn\":\"1111111141\",\"publishDate\":1607702400000,\"title\":\"demoData\",\"categoryId\":1}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('21', '111', '/添加图书', 'modenlibrary.controller.BookController.add', '[{\"author\":\"demoData\",\"isbn\":\"1111111142\",\"publishDate\":1607702400000,\"title\":\"demoData\",\"categoryId\":1}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('22', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('23', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('24', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('25', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('26', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-01-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('27', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-10', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('28', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-10', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('29', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-10', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('30', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-10', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('31', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-10', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('32', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-10', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('33', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-11', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('34', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[\"admin\",\"111\",{\"servletContext\":{},\"session\":{}}]', '2021-01-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('35', '111', '/添加用户', 'modenlibrary.controller.UserController.add', '[\"testTransaction\",\"111\",1]', '2021-01-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('36', '111', '/添加用户', 'modenlibrary.controller.UserController.add', '[\"testTransaction\",\"111\"]', '2021-01-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('37', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"4qbwu\",\"uuid\":\"50631c0c5ae74b4980be6c6a5f5b8818\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-06', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('38', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"m7eh7\",\"uuid\":\"80f7f51c704f4945ba606d6088ee460c\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('39', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"nin4r\",\"uuid\":\"96ddf53bebdd4362b51f883b5412d8d7\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('40', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"zzjix\",\"uuid\":\"99c51d5d726d4b12abd1b411788cc24a\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('41', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"mdz8z\",\"uuid\":\"4cba42dd155345409af331c91ce0bc3e\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('42', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"mp2ba\",\"uuid\":\"ff507885f2b6432ebd25e45632bb3071\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('43', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"yoqpe\",\"uuid\":\"d52c7b56c199484880d1b86a0a56d879\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('44', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"y3xu4\",\"uuid\":\"bd52ce4771aa4e5a927a675af3e07037\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('45', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('46', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[]', '2021-03-14', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('47', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"0ovpe\",\"uuid\":\"5e95b3bd6b774d7f97de67d5c54a2b31\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('48', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('49', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('50', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('51', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"5t92u\",\"uuid\":\"3b843323b90f416b926442bbf3f96a65\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('52', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('53', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('54', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"3pvx4\",\"uuid\":\"aa863116dd03497fab784175a769cdf2\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('55', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('56', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"mj26i\",\"uuid\":\"4b9c17ee27b74e32aa1efc0cd8a43dae\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('57', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('58', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"617hx\",\"uuid\":\"5be8471e19424cc1a39be3b58814d05f\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('59', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"nlsgz\",\"uuid\":\"f4ac8ebcc51742f3a2239b465cba7f47\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('60', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('61', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"ydf9c\",\"uuid\":\"8f38e6183abc45f2b42eb8ba9e08f768\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('62', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('63', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"9tap9\",\"uuid\":\"0d31288347cd42a8b0afc0545a1c9f8b\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('64', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('65', '123', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"fe7dn\",\"uuid\":\"fee5eadcba1d435f88e02acd32373191\",\"password\":\"aaaa\",\"username\":\"aaaa\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('66', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"8ratm\",\"uuid\":\"c195690f3e714fae99d0abba93b22230\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('67', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"uws9y\",\"uuid\":\"27fd481e84864bc0b57e3dc6880e8e1d\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('68', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('69', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"iderr\",\"uuid\":\"c69e924865fb43bfb1b40fe3460ba848\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('70', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('71', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('72', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('73', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"ptd9g\",\"uuid\":\"3f1dad8f5517444d8a98655c50ea1d77\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('74', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('75', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('76', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"4fo0v\",\"uuid\":\"a5779271c1394365a3af216166521a38\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('77', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('78', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('79', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"xmcd8\",\"uuid\":\"598d3f837b9c4b8d998f3332416fa48a\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('80', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('81', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('82', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('83', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('84', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('85', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"nt2er\",\"uuid\":\"aab3282ed901444c9e39f01a4611648b\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('86', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('87', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('88', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('89', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('90', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"jpod0\",\"uuid\":\"993c7131bc834316975e724de2373d77\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('91', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('92', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('93', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('94', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"1d258\",\"uuid\":\"eb10011c5a8c4d5ca8765cd444b5a701\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('95', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('96', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"oa8ro\",\"uuid\":\"d02933ea2afc40f79cd026afcfe2465d\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('97', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"tj47b\",\"uuid\":\"9d7e3ae5a5cd449bbdf39140243f8369\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('98', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"49miy\",\"uuid\":\"41c0c8a954e640e5881c9c5435d7caa1\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('99', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('100', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('101', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"x4bpq\",\"uuid\":\"587602df548148dbbc9081aa8a2a7d71\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('102', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"bnb1l\",\"uuid\":\"cdb8b998ea9b4849ba12814b0e8e1078\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('103', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"bnb1l\",\"uuid\":\"cdb8b998ea9b4849ba12814b0e8e1078\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('104', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"ul2fe\",\"uuid\":\"b162e0f61de2476584e3d052f7a7677d\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('105', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"r46zj\",\"uuid\":\"bedf59e79f21497893c36ed338f8bf95\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('106', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"ii4tg\",\"uuid\":\"da164796562049acb48f27d6254e751a\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('107', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('108', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"5pqt8\",\"uuid\":\"8fb2d85e061149028284de0ce1b0584f\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('109', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('110', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"cle37\",\"uuid\":\"711601dace55423dac19616849ae5146\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('111', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('112', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-18', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('113', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"rai3m\",\"uuid\":\"127c6bc44527463d83a6ec68816d5963\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('114', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('115', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('116', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"yebg2\",\"uuid\":\"ee8e7d2a5f9c4c9cb6225c4e80eb4196\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('117', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('118', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"1mqdp\",\"uuid\":\"aacd626e7749451686ad1c9538965c5d\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('119', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('120', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"6zctf\",\"uuid\":\"b26c7b73676848e7a0d257e6f450b705\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('121', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('122', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"1vcah\",\"uuid\":\"4b7dc4a0eefd4d2f9f203ee4f8882ba0\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('123', '111', '上传了图书Excel表', 'modenlibrary.controller.BookController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('124', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"tqbqe\",\"uuid\":\"a65ea064abf547bcbf3652168bc05e11\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('125', '111', '导入用户Excel文件', 'modenlibrary.controller.UserController.uploadExcel', '[{}]', '2021-03-19', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('126', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test push\"]', '2021-04-17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES ('127', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test push\"]', '2021-04-17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES ('128', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test push\"]', '2021-04-17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES ('129', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test push\"]', '2021-04-17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES ('130', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test push\"]', '2021-04-17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES ('131', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test push\"]', '2021-04-17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES ('132', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"86su6\",\"uuid\":\"d82d0fe188064fb1a326112703ebf3a9\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('133', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"sofpj\",\"uuid\":\"972aa50c94f0483bbb64fe6ed47a25c5\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('134', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"h8lb2\",\"uuid\":\"d5a9e1c6473541a084af4f4f95d5aa31\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('135', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"goac4\",\"uuid\":\"e22c6b0ad75e45a2ab9f46b6cca47cb8\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('136', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"3n2g8\",\"uuid\":\"017a94895e64408f8c5518075dd697d0\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('137', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"3meeh\",\"uuid\":\"41f4fbdf0e924679ae64b2d00b88e40b\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('138', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test\"]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('139', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test\"]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('140', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test\"]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('141', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"h4c4j\",\"uuid\":\"a83ab162ea354b338bceaa3532d39124\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('142', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"2b7ei\",\"uuid\":\"bf247dac063f42519000275a6ea34477\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('143', '113', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"3984a\",\"uuid\":\"ca2aec778d8b42b9ab4291c9b54e58d2\",\"password\":\"test2\",\"username\":\"test2\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('144', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"yqdxc\",\"uuid\":\"17bb1cc4b23f476b89fffda0214c819c\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('145', '112', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"qydui\",\"uuid\":\"f5b7fb7719a5433ca790c0c2e18093d3\",\"password\":\"111\",\"username\":\"test1\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('146', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"anpbc\",\"uuid\":\"34cf992101314c058a4f66b724b06a11\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('147', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"bdt6x\",\"uuid\":\"aeef45beafee48c58b3fc102dca0c53b\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('148', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test\"]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('149', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test\"]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('150', '111', '/推送信息', 'modenlibrary.controller.AdminController.pushMsg', '[\"test\"]', '2021-04-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('151', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"x1z8v\",\"uuid\":\"842275b5184845738ca0ea00416f61a3\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('152', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"ao7t3\",\"uuid\":\"9ce9ef1b2920465880467f7190d46a3e\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('153', '111', '/更新用户信息', 'modenlibrary.controller.UserController.update', '[{\"gender\":1,\"counts\":5,\"allowLend\":98,\"isblack\":0,\"id\":111,\"isdel\":0,\"username\":\"admin\",\"registerDate\":1608480000000},{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('154', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('155', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('156', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('157', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111111\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('158', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('159', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('160', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('161', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('162', '111', '重置用户违规次数', 'modenlibrary.controller.UserController.resetCounts', '[111]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('163', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"iqumt\",\"uuid\":\"70d8fd8be0704a9aaf064614b075a1c7\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('164', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111111\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('165', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('166', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('167', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111111\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('168', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"xhgk1\",\"uuid\":\"8163b73209904b17a37b3260e4cebb96\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('169', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111111\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('170', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111112\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('171', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"yyihj\",\"uuid\":\"fcddab4696f84ab8b674a6982a646e64\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('172', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111111\",111,\"归还\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('173', '111', '/改变借阅的状态', 'modenlibrary.controller.BookController.returned', '[\"1111111111\",111,\"借出\",{\"servletContext\":{},\"session\":{}}]', '2021-05-09', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('174', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"8ea5j\",\"uuid\":\"3fbc34543bea4124803771b16067b3ac\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('175', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"vakzw\",\"uuid\":\"f7b71a1a2fc14b7db843c16b17bcfde7\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('176', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"gndmi\",\"uuid\":\"52f7a0ea9c244bc8ab4d6d23efd56b7f\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('177', '111', '/移除管理员', 'modenlibrary.controller.AdminController.removeAdmin', '[112]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('178', '111', '/添加管理员', 'modenlibrary.controller.AdminController.addAdmin', '[112]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('179', '111', '/添加用户进黑名单', 'modenlibrary.controller.UserController.addBlack', '[113]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('180', '111', '/移除用户出黑名单', 'modenlibrary.controller.UserController.delBlack', '[113]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('181', '111', '/更新用户信息', 'modenlibrary.controller.UserController.update', '[{\"gender\":0,\"counts\":0,\"allowLend\":10,\"password\":\"5a105e8b9d40e1329780d62ea2265d8a\",\"isblack\":0,\"id\":112,\"isdel\":0,\"username\":\"test1\",\"registerDate\":1607702400000},{\"servletContext\":{},\"session\":{}}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('182', '112', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"x0xet\",\"uuid\":\"72ac757c3aba4fa8b45f786aaa0a9877\",\"password\":\"test1\",\"username\":\"test1\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('183', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"dddwz\",\"uuid\":\"40f794bb3f57467c839eacc65c8361a5\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('184', '111', '/添加管理员', 'modenlibrary.controller.AdminController.addAdmin', '[113]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('185', '112', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"rd4oe\",\"uuid\":\"be5fb83366e542e2b071652b0694313f\",\"password\":\"test1\",\"username\":\"test1\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('186', '112', '/更新用户信息', 'modenlibrary.controller.UserController.update', '[{\"gender\":0,\"counts\":0,\"allowLend\":10,\"password\":\"5a105e8b9d40e1329780d62ea2265d8a\",\"isblack\":0,\"id\":112,\"isdel\":0,\"username\":\"test1\",\"registerDate\":1607702400000},{\"servletContext\":{},\"session\":{}}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('187', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"2cdqo\",\"uuid\":\"450eb669086a4c6885ba8d980c4b7837\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('188', '111', '/移除管理员', 'modenlibrary.controller.AdminController.removeAdmin', '[113]', '2021-05-12', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('189', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"yuzre\",\"uuid\":\"5280798dcba64330a4ee0449b3c2fa30\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('190', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"z2t52\",\"uuid\":\"df852dfa632745a5929fe9e6a21a30e9\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('191', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"0ser6\",\"uuid\":\"84a863b1fbb9472082d35a7aa930ecaf\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('192', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"s2vyy\",\"uuid\":\"eeab504ae8fa4928a22f6c96b02de2d2\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('193', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"cucig\",\"uuid\":\"b0f635a23b354a728a649e254ac83dfe\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('194', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"7od7g\",\"uuid\":\"c60fd01933bd4c25a813293cd7b5f0f2\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('195', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"enneg\",\"uuid\":\"15e877e60620476cbeea739b81b71089\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('196', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"4kq1p\",\"uuid\":\"5cc5e1feff9142259ac6d7e8c790b5dc\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('197', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"c7ipa\",\"uuid\":\"90d8b330ad5047ed9a7f25e1bdfb3757\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('198', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"50xv5\",\"uuid\":\"a8d362bc240544c697705865da13391b\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('199', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"mdosr\",\"uuid\":\"a6e04172440b4596844d506ee7a0cf42\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('200', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"9o0se\",\"uuid\":\"59e186e875714e7ab0b7609167a4653b\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('201', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"0sely\",\"uuid\":\"44c31daa68d34ea7af0b4d5e7fbf0fbf\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('202', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"lccm6\",\"uuid\":\"c893ca4ad08a4a8f81b986057f3edf5a\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('203', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"e6d3h\",\"uuid\":\"d59866c6a0144e8a8ba311e4e64b0503\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('204', '111', '创建定时任务', 'modenlibrary.controller.SchedulerController.create', '[{\"jobName\":\"test\",\"cronExpression\":\"0/2 * * * * ? \",\"jobClass\":\"/F:/modenlibrary/target/classes/modenlibrary.scheduled.task.testTask\",\"status\":1}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('205', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"ey07t\",\"uuid\":\"a2cf4eb95f6d4ccf936cdb46ed033495\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');
INSERT INTO `sys_log` VALUES ('206', '111', '管理员登录', 'modenlibrary.controller.LoginController.adminlogin', '[{\"code\":\"0hz4r\",\"uuid\":\"99034a51435749ce83a2fb8cd928a391\",\"password\":\"111\",\"username\":\"admin\"}]', '2021-05-17', '127.0.0.1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `gender` tinyint DEFAULT '1' COMMENT '性别 0表示女 1表示男',
  `isblack` tinyint DEFAULT '0' COMMENT '是否是黑名单用户 默认0为否',
  `allow_lend` int DEFAULT '10' COMMENT '允许借出的书的数目 默认10本',
  `isdel` tinyint DEFAULT '0' COMMENT '是否注销该用户',
  `register_date` datetime DEFAULT NULL COMMENT '用户注册日期',
  `counts` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('111', 'admin', '698d51a19d8a121ce581499d7b701668', '1', '0', '98', '0', '2020-12-21 00:00:00', '0');
INSERT INTO `user` VALUES ('112', 'test1', '5a105e8b9d40e1329780d62ea2265d8a', '0', '0', '10', '0', '2020-12-12 00:00:00', '0');
INSERT INTO `user` VALUES ('113', 'test2', 'ad0234829205b9033196ba818f7a872b', '1', '0', '10', '0', '2020-12-21 15:02:03', '0');
INSERT INTO `user` VALUES ('114', '测试添加', '098f6bcd4621d373cade4e832627b4f6', '0', '0', '10', '0', '2020-12-24 10:07:23', '0');
INSERT INTO `user` VALUES ('115', '测试添加', '098f6bcd4621d373cade4e832627b4f6', '0', '0', '10', '0', '2020-12-24 10:12:08', '0');
INSERT INTO `user` VALUES ('116', '测试添加', '098f6bcd4621d373cade4e832627b4f6', '1', '0', '10', '0', '2020-12-24 10:14:41', '0');
INSERT INTO `user` VALUES ('117', 'testanno', '098f6bcd4621d373cade4e832627b4f6', '1', '0', '10', '0', '2020-12-26 16:45:22', '0');
INSERT INTO `user` VALUES ('118', 'anno', '098f6bcd4621d373cade4e832627b4f6', '1', '0', '10', '0', '2020-12-26 16:50:33', '0');
INSERT INTO `user` VALUES ('119', 'testadd', 'c099ca4b61b8845d7ba362c855e0fcc0', '0', '0', '10', '0', '2021-01-04 11:46:36', '0');
INSERT INTO `user` VALUES ('120', 'testTransaction', '698d51a19d8a121ce581499d7b701668', '1', '0', '10', '0', '2021-01-18 11:13:47', '0');
INSERT INTO `user` VALUES ('123', 'aaaa', '74b87337454200d4d33f80c4663dc5e5', '1', '0', '10', '0', '2021-03-17 21:43:48', '0');
INSERT INTO `user` VALUES ('124', 'bbbb', '65ba841e01d6db7733e90a5b7f9e6f80', '0', '0', '10', '0', '2021-03-17 21:43:48', '0');
INSERT INTO `user` VALUES ('125', null, null, '0', '0', '10', '0', '2021-03-18 22:51:52', '0');
INSERT INTO `user` VALUES ('126', '1129374012', '77545f43839a113fb8d12e2c76ba22dc', '0', '0', '10', '0', '2021-03-19 22:38:00', '0');
INSERT INTO `user` VALUES ('127', '1234561234', '8d421e892a47dff539f46142eb09e56b', '0', '0', '10', '0', '2021-03-19 22:38:00', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户的id',
  `role_id` int NOT NULL COMMENT '角色的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '111', '1');
INSERT INTO `user_role` VALUES ('2', '112', '2');
INSERT INTO `user_role` VALUES ('3', '113', '3');
INSERT INTO `user_role` VALUES ('4', '119', '3');
INSERT INTO `user_role` VALUES ('5', '120', '3');
INSERT INTO `user_role` VALUES ('6', '123', '3');
INSERT INTO `user_role` VALUES ('7', '124', '3');
INSERT INTO `user_role` VALUES ('8', '125', '3');
INSERT INTO `user_role` VALUES ('9', '126', '3');
INSERT INTO `user_role` VALUES ('10', '127', '3');
