/*
Navicat MySQL Data Transfer

Source Server         : demo
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-03 17:26:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `title` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `href` varchar(200) DEFAULT NULL COMMENT '链接的相对地址',
  `menuCode` varchar(100) DEFAULT NULL COMMENT '菜单编号',
  `pid` int(11) DEFAULT NULL COMMENT '菜单上层Id',
  `pname` varchar(100) DEFAULT NULL,
  `seq` varchar(100) DEFAULT NULL COMMENT '排序',
  `level` varchar(10) DEFAULT NULL COMMENT '第几层',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '系统管理', '#', null, '0', null, '1', '1', null);
INSERT INTO `menu` VALUES ('2', '用户管理', '/manage/sysUserList', null, '1', '系统管理', '1', '2', null);
INSERT INTO `menu` VALUES ('3', '角色管理', '/manage/roleList', null, '1', '系统管理', '2', '2', null);
INSERT INTO `menu` VALUES ('4', '菜单管理', '/manage/menuList', null, '1', '系统管理', '4', '2', null);
INSERT INTO `menu` VALUES ('5', '访问权限', '/manage/visitList', null, '1', '系统管理', '3', '2', null);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_name` varchar(32) DEFAULT NULL COMMENT '权限名',
  `permission_sign` varchar(128) DEFAULT NULL COMMENT '权限标识,程序中判断使用,如"user:create"',
  `description` varchar(256) DEFAULT NULL COMMENT '权限描述,UI界面显示使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '用户新增', 'user:create', null);

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', 'huangkewei', '1', '453');
INSERT INTO `person` VALUES ('2', 'huang', '4', 'test');
INSERT INTO `person` VALUES ('3', 'huang', '4', 'test');
INSERT INTO `person` VALUES ('4', 'huang', '4', 'test');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名',
  `role_sign` varchar(128) DEFAULT NULL COMMENT '角色标识,程序中判断使用,如"admin"',
  `description` varchar(256) DEFAULT NULL COMMENT '角色描述,UI界面显示使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', 'admin', '管理员');
INSERT INTO `role` VALUES ('2', 'test', 'test', '测试');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `roleId` int(11) DEFAULT NULL COMMENT '角色Id',
  `menuId` int(11) DEFAULT NULL COMMENT '菜单Id',
  `remark` varchar(300) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '1', null);
INSERT INTO `role_menu` VALUES ('2', '1', '2', null);
INSERT INTO `role_menu` VALUES ('3', '1', '3', null);
INSERT INTO `role_menu` VALUES ('4', '1', '4', null);
INSERT INTO `role_menu` VALUES ('5', '1', '5', null);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `role_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(20) unsigned DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色与权限关联表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '2', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(30) NOT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `state` int(1) DEFAULT NULL COMMENT '状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('000000', 'admin', '123456', '1', '2017-06-15 17:15:40');
INSERT INTO `user` VALUES ('1', 'hkw', '123456', '1', '2014-07-17 12:59:08');
INSERT INTO `user` VALUES ('2', 'hkw1', '123456', '1', '2014-07-17 12:59:08');
INSERT INTO `user` VALUES ('3', 'hkw2', '123456', '1', '2014-07-17 12:59:08');
INSERT INTO `user` VALUES ('4', 'hkw3', '123456', '1', '2014-07-17 12:59:08');
INSERT INTO `user` VALUES ('5', 'hkw4', '123456', '1', '2014-07-17 12:59:08');
INSERT INTO `user` VALUES ('6', 'hkw5', '123456', '1', '2014-07-17 12:59:08');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('7', '1', '1');
INSERT INTO `user_role` VALUES ('8', '2', '1');
INSERT INTO `user_role` VALUES ('9', '3', '1');
INSERT INTO `user_role` VALUES ('10', '6', '2');

DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `fileId` int(11) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(100) DEFAULT NULL,
  `filePath` varchar(400) DEFAULT NULL,
  `fileType` varchar(10) DEFAULT NULL,
  `classify` varchar(20) DEFAULT NULL,
  `appId` varchar(36) DEFAULT NULL,
  `fileSize` int(11) DEFAULT NULL,
  `upTime` datetime DEFAULT NULL,
  `lastModify` datetime DEFAULT NULL,
  `lastModifier` varchar(36) DEFAULT NULL,
  `ext` varchar(200) DEFAULT NULL,
  `seqno` int(2) DEFAULT '0',
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB AUTO_INCREMENT=16256 DEFAULT CHARSET=utf8;

