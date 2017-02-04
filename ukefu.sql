/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50527
Source Host           : 127.0.0.1:3306
Source Database       : ukefu

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-02-03 20:04:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `uk_agentstatus`
-- ----------------------------
DROP TABLE IF EXISTS `uk_agentstatus`;
CREATE TABLE `uk_agentstatus` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `agentno` varchar(100) DEFAULT '',
  `logindate` datetime DEFAULT NULL,
  `status` varchar(100) DEFAULT '',
  `orgi` varchar(100) DEFAULT '',
  `agentserviceid` varchar(100) DEFAULT '',
  `serusernum` int(11) DEFAULT '0',
  `skill` varchar(100) DEFAULT '',
  `skillname` varchar(100) DEFAULT '',
  `users` int(11) DEFAULT '0',
  `maxusers` int(11) DEFAULT '0',
  `username` varchar(100) DEFAULT '',
  `name` varchar(100) DEFAULT '',
  `updatetime` datetime DEFAULT NULL,
  `userid` varchar(100) DEFAULT '',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `assignedto` varchar(255) DEFAULT NULL COMMENT '分配目标用户',
  `wfstatus` varchar(255) DEFAULT NULL COMMENT '流程状态',
  `shares` varchar(255) DEFAULT NULL COMMENT '分享给',
  `owner` varchar(255) DEFAULT NULL COMMENT '所属人',
  `datadept` varchar(255) DEFAULT NULL COMMENT '创建人部门',
  `batid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_agentstatus
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_agentuser`
-- ----------------------------
DROP TABLE IF EXISTS `uk_agentuser`;
CREATE TABLE `uk_agentuser` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `username` varchar(100) DEFAULT '',
  `agentno` varchar(100) DEFAULT '',
  `userid` varchar(100) DEFAULT '',
  `channel` varchar(100) DEFAULT '',
  `logindate` datetime DEFAULT NULL,
  `source` varchar(100) DEFAULT '',
  `endtime` datetime DEFAULT NULL,
  `nickname` varchar(100) DEFAULT '',
  `city` varchar(100) DEFAULT '',
  `province` varchar(100) DEFAULT '',
  `country` varchar(100) DEFAULT '',
  `headImgUrl` varchar(100) DEFAULT '',
  `waittingtime` int(11) DEFAULT '0',
  `tokenum` int(11) DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `status` varchar(100) DEFAULT '',
  `appid` varchar(100) DEFAULT '',
  `sessiontype` varchar(100) DEFAULT '',
  `contextid` varchar(100) DEFAULT '',
  `agentserviceid` varchar(100) DEFAULT '',
  `orgi` varchar(100) DEFAULT '',
  `snsuser` varchar(100) DEFAULT '',
  `lastmessage` datetime DEFAULT NULL,
  `waittingtimestart` datetime DEFAULT NULL,
  `lastgetmessage` datetime DEFAULT NULL,
  `lastmsg` varchar(100) DEFAULT '',
  `agentskill` varchar(100) DEFAULT '',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `assignedto` varchar(255) DEFAULT NULL COMMENT '分配目标用户',
  `wfstatus` varchar(255) DEFAULT NULL COMMENT '流程状态',
  `shares` varchar(255) DEFAULT NULL COMMENT '分享给',
  `owner` varchar(255) DEFAULT NULL COMMENT '所属人',
  `datadept` varchar(255) DEFAULT NULL COMMENT '创建人部门',
  `intime` int(32) DEFAULT NULL,
  `batid` varchar(32) DEFAULT NULL,
  `ipaddr` varchar(50) DEFAULT NULL,
  `osname` varchar(100) DEFAULT NULL,
  `browser` varchar(100) DEFAULT NULL,
  `sessiontimes` int(20) DEFAULT NULL,
  `servicetime` datetime DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_agentuser
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_chat_message`
-- ----------------------------
DROP TABLE IF EXISTS `uk_chat_message`;
CREATE TABLE `uk_chat_message` (
  `type` varchar(100) DEFAULT '',
  `id` varchar(100) NOT NULL DEFAULT '',
  `calltype` varchar(32) DEFAULT NULL,
  `contextid` varchar(50) DEFAULT NULL,
  `session` varchar(100) DEFAULT NULL,
  `touser` varchar(50) DEFAULT NULL,
  `channel` varchar(32) DEFAULT NULL,
  `tousername` varchar(100) DEFAULT NULL,
  `appid` varchar(50) DEFAULT NULL,
  `userid` varchar(100) DEFAULT '',
  `nickname` varchar(100) DEFAULT '',
  `message` text,
  `msgtype` varchar(100) DEFAULT '',
  `orgi` varchar(100) DEFAULT '',
  `msgid` varchar(100) DEFAULT '',
  `expmsg` varchar(100) DEFAULT '',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `createtime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `assignedto` varchar(255) DEFAULT NULL COMMENT '分配目标用户',
  `wfstatus` varchar(255) DEFAULT NULL COMMENT '流程状态',
  `shares` varchar(255) DEFAULT NULL COMMENT '分享给',
  `owner` varchar(255) DEFAULT NULL COMMENT '所属人',
  `datadept` varchar(255) DEFAULT NULL COMMENT '创建人部门',
  `batid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_chat_message
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_consult_invite`
-- ----------------------------
DROP TABLE IF EXISTS `uk_consult_invite`;
CREATE TABLE `uk_consult_invite` (
  `id` varchar(255) NOT NULL,
  `impid` varchar(255) DEFAULT NULL,
  `orgi` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `processid` varchar(255) DEFAULT NULL,
  `shares` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `wfstatus` varchar(255) DEFAULT NULL,
  `consult_invite_model` varchar(32) DEFAULT NULL,
  `consult_invite_content` varchar(255) DEFAULT NULL,
  `consult_invite_position` varchar(32) DEFAULT NULL,
  `consult_invite_color` varchar(32) DEFAULT NULL,
  `consult_invite_right` int(11) DEFAULT NULL,
  `consult_invite_left` int(11) DEFAULT NULL,
  `consult_invite_bottom` int(11) DEFAULT NULL,
  `consult_invite_top` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `consult_invite_width` int(32) DEFAULT NULL,
  `consult_invite_poptype` varchar(32) DEFAULT NULL,
  `consult_invite_fontsize` int(32) DEFAULT NULL,
  `consult_invite_fontstyle` varchar(32) DEFAULT NULL,
  `consult_invite_fontcolor` varchar(32) DEFAULT NULL,
  `consult_invite_interval` int(32) DEFAULT NULL,
  `consult_invite_repeat` varchar(32) DEFAULT NULL,
  `consult_invite_hight` int(32) DEFAULT NULL,
  `snsaccountid` varchar(56) DEFAULT NULL,
  `consult_vsitorbtn_position` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_content` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_right` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_left` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_top` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_color` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_model` varchar(32) DEFAULT NULL,
  `consult_vsitorbtn_bottom` varchar(32) DEFAULT NULL,
  `consult_invite_backimg` varchar(32) DEFAULT NULL,
  `datadept` varchar(255) DEFAULT NULL,
  `agent_online` varchar(32) DEFAULT NULL,
  `consult_dialog_color` varchar(32) DEFAULT NULL,
  `consult_dialog_logo` varchar(100) DEFAULT NULL,
  `consult_dialog_headimg` varchar(100) DEFAULT NULL,
  `consult_vsitorbtn_display` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_consult_invite
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_onlineuser`
-- ----------------------------
DROP TABLE IF EXISTS `uk_onlineuser`;
CREATE TABLE `uk_onlineuser` (
  `assignedto` varchar(255) DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `datastatus` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `impid` varchar(255) DEFAULT NULL,
  `ipcode` varchar(255) DEFAULT NULL,
  `orgi` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `processid` varchar(255) DEFAULT NULL,
  `shares` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `wfstatus` varchar(255) DEFAULT NULL,
  `resolution` varchar(255) DEFAULT NULL,
  `oper_system` varchar(32) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `hostname` varchar(32) DEFAULT NULL,
  `browser` varchar(32) DEFAULT NULL,
  `status` varchar(11) DEFAULT NULL,
  `user_id` varchar(52) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `sessionid` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `usertype` varchar(52) DEFAULT NULL,
  `optype` varchar(52) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `olduser` varchar(10) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `region` varchar(200) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `isp` varchar(100) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `between_time` int(20) DEFAULT NULL,
  `datestr` varchar(20) DEFAULT NULL,
  `keyword` varchar(100) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `useragent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_onlineuser
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_onlineuser_his`
-- ----------------------------
DROP TABLE IF EXISTS `uk_onlineuser_his`;
CREATE TABLE `uk_onlineuser_his` (
  `assignedto` varchar(255) DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `datastatus` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `impid` varchar(255) DEFAULT NULL,
  `ipcode` varchar(255) DEFAULT NULL,
  `orgi` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `processid` varchar(255) DEFAULT NULL,
  `shares` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `wfstatus` varchar(255) DEFAULT NULL,
  `resolution` varchar(255) DEFAULT NULL,
  `oper_system` varchar(32) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `hostname` varchar(32) DEFAULT NULL,
  `browser` varchar(32) DEFAULT NULL,
  `status` varchar(11) DEFAULT NULL,
  `user_id` varchar(52) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `sessionid` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `usertype` varchar(52) DEFAULT NULL,
  `optype` varchar(52) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `olduser` varchar(10) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `region` varchar(200) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `isp` varchar(100) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `between_time` int(20) DEFAULT NULL,
  `datestr` varchar(20) DEFAULT NULL,
  `keyword` varchar(100) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `useragent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_onlineuser_his
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_role`
-- ----------------------------
DROP TABLE IF EXISTS `uk_role`;
CREATE TABLE `uk_role` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `CODE` varchar(50) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATER` varchar(32) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `ORGI` varchar(32) DEFAULT NULL,
  `USERNAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_role
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_skill`
-- ----------------------------
DROP TABLE IF EXISTS `uk_skill`;
CREATE TABLE `uk_skill` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `CODE` varchar(50) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATER` varchar(32) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `ORGI` varchar(32) DEFAULT NULL,
  `USERNAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_skill
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_snsaccount`
-- ----------------------------
DROP TABLE IF EXISTS `uk_snsaccount`;
CREATE TABLE `uk_snsaccount` (
  `authorizeURL` varchar(255) DEFAULT NULL,
  `accessTokenURL` varchar(255) DEFAULT NULL,
  `baseURL` varchar(255) DEFAULT NULL,
  `redirectURI` varchar(255) DEFAULT NULL,
  `clientSERCRET` varchar(192) DEFAULT NULL,
  `clientID` varchar(96) DEFAULT NULL,
  `ID` varchar(96) NOT NULL,
  `states` varchar(32) DEFAULT NULL,
  `region` varchar(32) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `APIPOINT` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `SNSTYPE` varchar(255) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `ACCOUNT` varchar(255) DEFAULT NULL,
  `ALLOWREMOTE` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `USERNO` varchar(255) DEFAULT NULL,
  `TOKEN` varchar(255) DEFAULT NULL,
  `APPKEY` varchar(255) DEFAULT NULL,
  `SECRET` varchar(255) DEFAULT NULL,
  `AESKEY` varchar(255) DEFAULT NULL,
  `APPTOKEN` varchar(255) DEFAULT NULL,
  `SESSIONKEY` varchar(255) DEFAULT NULL,
  `MOREPARAM` varchar(255) DEFAULT NULL,
  `ORGI` varchar(255) DEFAULT NULL,
  `DEFAULTACCOUNT` smallint(6) DEFAULT NULL,
  `lastatupdate` varchar(96) DEFAULT NULL,
  `lastprimsgupdate` varchar(96) DEFAULT NULL,
  `ACCTYPE` varchar(32) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `shares` varchar(255) DEFAULT NULL COMMENT '分享给',
  `owner` varchar(255) DEFAULT NULL COMMENT '所属人',
  `assignedto` varchar(255) DEFAULT NULL COMMENT '分配目标用户',
  `wfstatus` varchar(255) DEFAULT NULL COMMENT '流程状态',
  `datadept` varchar(255) DEFAULT NULL COMMENT '创建人部门',
  `batid` varchar(32) DEFAULT NULL,
  `alias` varchar(50) DEFAULT NULL,
  `authaccesstoken` varchar(255) DEFAULT NULL,
  `expirestime` int(11) DEFAULT NULL,
  `headimg` varchar(255) DEFAULT NULL,
  `oepnscan` varchar(100) DEFAULT NULL,
  `opencard` varchar(100) DEFAULT NULL,
  `openstore` varchar(100) DEFAULT NULL,
  `openpay` varchar(100) DEFAULT NULL,
  `openshake` varchar(100) DEFAULT NULL,
  `qrcode` varchar(100) DEFAULT NULL,
  `refreshtoken` varchar(255) DEFAULT NULL,
  `verify` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SQL121227155530370` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_snsaccount
-- ----------------------------

-- ----------------------------
-- Table structure for `uk_user`
-- ----------------------------
DROP TABLE IF EXISTS `uk_user`;
CREATE TABLE `uk_user` (
  `ID` varchar(32) NOT NULL,
  `LANGUAGE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `SECURECONF` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `MIDNAME` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `JOBTITLE` varchar(255) DEFAULT NULL,
  `DEPARTMENT` varchar(255) DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `BIRTHDAY` varchar(255) DEFAULT NULL,
  `NICKNAME` varchar(255) DEFAULT NULL,
  `USERTYPE` varchar(255) DEFAULT NULL,
  `RULENAME` varchar(255) DEFAULT NULL,
  `SEARCHPROJECTID` varchar(255) DEFAULT NULL,
  `ORGI` varchar(32) DEFAULT NULL,
  `CREATER` varchar(32) DEFAULT NULL,
  `CREATETIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MEMO` varchar(255) DEFAULT NULL,
  `UPDATETIME` timestamp NULL DEFAULT NULL,
  `ORGAN` varchar(32) DEFAULT NULL,
  `MOBILE` varchar(32) DEFAULT NULL,
  `passupdatetime` timestamp NULL DEFAULT NULL,
  `sign` text,
  `del` tinyint(4) DEFAULT '0',
  `uname` varchar(100) DEFAULT NULL,
  `musteditpassword` tinyint(4) DEFAULT NULL,
  `AGENT` tinyint(4) DEFAULT NULL,
  `SKILL` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uk_user
-- ----------------------------
INSERT INTO `uk_user` VALUES ('297e8c7b455798280145579c73e501c1', null, 'admin', '123456', '5', 'admin@ukewo.com', null, null, null, null, null, null, null, null, null, null, null, 'ukewo', null, '2017-01-22 17:43:08', null, '2017-01-24 23:36:34', null, '185102433xxxx', null, null, '0', '关云长', '0', '1', null);
