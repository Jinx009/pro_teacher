/*
 Navicat Premium Data Transfer

 Source Server         : 115.28.203.238
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : 115.28.203.238
 Source Database       : pengchang

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 03/23/2016 21:50:40 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `za_apply`
-- ----------------------------
DROP TABLE IF EXISTS `za_apply`;
CREATE TABLE `za_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applyTitle` varchar(32) NOT NULL,
  `needPhoneNumber` bit(1) NOT NULL,
  `needEmail` bit(1) NOT NULL,
  `needRealName` bit(1) NOT NULL,
  `needAddress` bit(1) NOT NULL,
  `applyDesc` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_article`
-- ----------------------------
DROP TABLE IF EXISTS `za_article`;
CREATE TABLE `za_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleTitle` varchar(64) NOT NULL,
  `articleDesc` varchar(128) DEFAULT NULL,
  `articleContent` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_core_event_msg`
-- ----------------------------
DROP TABLE IF EXISTS `za_core_event_msg`;
CREATE TABLE `za_core_event_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) DEFAULT NULL,
  `wx_user_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `message_date` datetime NOT NULL,
  `message` text NOT NULL,
  `owner_reply_date` datetime DEFAULT NULL,
  `owner_reply` text,
  `owner_replier` varchar(32) DEFAULT NULL,
  `owner_award` int(11) DEFAULT NULL COMMENT '填写发放的红包金额，如果为null的话表示没有发放，分为单位',
  `wx_config_id` int(11) DEFAULT NULL COMMENT '如果eventId为NULL，wx_config_id不为null，那么该条记录是针对公司的，而不是某个活动的',
  `wx_send_award_log_id` int(11) DEFAULT NULL COMMENT '对应za_core_send_award_log的id',
  PRIMARY KEY (`id`),
  KEY `FK_r69qdtj9u7qqe3g6n8lkty1ho` (`event_id`),
  KEY `FK_k5iiesxki502crnllwgbrwm8x` (`wx_send_award_log_id`),
  KEY `FK_qqshrkmyt4qumw5sihbc42v8a` (`wx_user_id`),
  KEY `FK_exnwmjs70ua816fk80297mjb3` (`wx_config_id`),
  CONSTRAINT `FK_exnwmjs70ua816fk80297mjb3` FOREIGN KEY (`wx_config_id`) REFERENCES `za_front_wx_config` (`id`),
  CONSTRAINT `FK_k5iiesxki502crnllwgbrwm8x` FOREIGN KEY (`wx_send_award_log_id`) REFERENCES `za_core_send_award_log` (`id`),
  CONSTRAINT `FK_qqshrkmyt4qumw5sihbc42v8a` FOREIGN KEY (`wx_user_id`) REFERENCES `za_front_user_wx` (`id`),
  CONSTRAINT `FK_r69qdtj9u7qqe3g6n8lkty1ho` FOREIGN KEY (`event_id`) REFERENCES `za_core_events` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_core_event_msg`
-- ----------------------------
BEGIN;
INSERT INTO `za_core_event_msg` VALUES ('1', '1', '1', null, '2016-03-22 18:58:24', '我是宇航 我是开发者', null, null, null, null, '1', null), ('2', '1', '1', null, '2016-03-22 19:05:48', '哈哈哈', null, null, null, null, '1', null), ('3', '1', '1', null, '2016-03-22 19:09:49', '我是开发者 我是宇航', null, null, null, null, '1', null), ('4', '1', '1', null, '2016-03-22 22:29:19', '时光机', null, null, null, null, '1', null), ('5', '1', '1', null, '2016-03-23 21:44:11', '我是开发者 我是宇航', null, null, null, null, '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `za_core_event_msg_img`
-- ----------------------------
DROP TABLE IF EXISTS `za_core_event_msg_img`;
CREATE TABLE `za_core_event_msg_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_msg_id` int(11) NOT NULL COMMENT '对应的某个消息',
  `img_url` varchar(128) NOT NULL,
  `thum_url` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8r3j36ov1k05rqbav5sy1mdul` (`event_msg_id`),
  CONSTRAINT `FK_8r3j36ov1k05rqbav5sy1mdul` FOREIGN KEY (`event_msg_id`) REFERENCES `za_core_event_msg` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_core_event_msg_img`
-- ----------------------------
BEGIN;
INSERT INTO `za_core_event_msg_img` VALUES ('1', '1', 'http://pc-res.0angle.com/wxImg/PH3lcRtR4y_2ey8qR2ej4naFovALtUzKM5bhBOHE_QG_XoKlAIOOxzs7k-e5S8W5.jpeg', 'http://pc-res.0angle.com/wxImg/PH3lcRtR4y_2ey8qR2ej4naFovALtUzKM5bhBOHE_QG_XoKlAIOOxzs7k-e5S8W5_thum.jpeg'), ('2', '1', 'http://pc-res.0angle.com/wxImg/0m9R4pAzHjTYnhMLoNVi5LPP8Yy3DC1AazMqR76wk44mv3ua0AViGC6Dk80puL5V.jpeg', 'http://pc-res.0angle.com/wxImg/0m9R4pAzHjTYnhMLoNVi5LPP8Yy3DC1AazMqR76wk44mv3ua0AViGC6Dk80puL5V_thum.jpeg'), ('3', '2', 'http://pc-res.0angle.com/wxImg/xrDrOa6qTCLx7Hw7nicCqn6SKMOoAtiwkhPupEpiVubVPvZzmJUeilMrrLiV1PWP.jpeg', 'http://pc-res.0angle.com/wxImg/xrDrOa6qTCLx7Hw7nicCqn6SKMOoAtiwkhPupEpiVubVPvZzmJUeilMrrLiV1PWP_thum.jpeg'), ('4', '2', 'http://pc-res.0angle.com/wxImg/Nvg2YDOPsRqnoXEId0vgcopRhc53SdvkFlwO7e_80dfTJVZmeX5mwseHw3hNG5Og.jpeg', 'http://pc-res.0angle.com/wxImg/Nvg2YDOPsRqnoXEId0vgcopRhc53SdvkFlwO7e_80dfTJVZmeX5mwseHw3hNG5Og_thum.jpeg'), ('5', '3', 'http://pc-res.0angle.com/wxImg/1wx9alpdvJ0OW4fb47kJbrx6ExJgeORizKbqk9LepZmy_RM9D4pCOONm3CugMupr.jpeg', 'http://pc-res.0angle.com/wxImg/1wx9alpdvJ0OW4fb47kJbrx6ExJgeORizKbqk9LepZmy_RM9D4pCOONm3CugMupr_thum.jpeg'), ('6', '4', 'http://pc-res.0angle.com/wxImg/X9vkDKQZ2FjBGqOTnGiFlDBQhu9_igxBzojlMnY_bl-WZl3jNuoYsK9_CI3J94On.jpeg', 'http://pc-res.0angle.com/wxImg/X9vkDKQZ2FjBGqOTnGiFlDBQhu9_igxBzojlMnY_bl-WZl3jNuoYsK9_CI3J94On_thum.jpeg'), ('7', '4', 'http://pc-res.0angle.com/wxImg/0p8LCOd2MDG9GnfyvPjJ9QMr5BWiTkUWI2HVplq6L9v_8e4HnoogbwcTERRPTjTP.jpeg', 'http://pc-res.0angle.com/wxImg/0p8LCOd2MDG9GnfyvPjJ9QMr5BWiTkUWI2HVplq6L9v_8e4HnoogbwcTERRPTjTP_thum.jpeg'), ('8', '4', 'http://pc-res.0angle.com/wxImg/oLg4JbsmTJ_0u9ul7PcT_riH8QNCGUqrzyyz-hCzHWjmLXyLzKdFDq6cf_ZLkF5t.jpeg', 'http://pc-res.0angle.com/wxImg/oLg4JbsmTJ_0u9ul7PcT_riH8QNCGUqrzyyz-hCzHWjmLXyLzKdFDq6cf_ZLkF5t_thum.jpeg'), ('9', '4', 'http://pc-res.0angle.com/wxImg/Gyy1gNz_dL65gMOURP3GLUBz22wXNa_zy-3dtgqod9z_fgZe_eGHyYsO6riJ2aP5.jpeg', 'http://pc-res.0angle.com/wxImg/Gyy1gNz_dL65gMOURP3GLUBz22wXNa_zy-3dtgqod9z_fgZe_eGHyYsO6riJ2aP5_thum.jpeg'), ('10', '4', 'http://pc-res.0angle.com/wxImg/61KXWYi9gHfLX23dACRqNjhK8S6yiNfs1ndSGcs0yM3bcKt4utDu9Y2YB8YTl_p_.jpeg', 'http://pc-res.0angle.com/wxImg/61KXWYi9gHfLX23dACRqNjhK8S6yiNfs1ndSGcs0yM3bcKt4utDu9Y2YB8YTl_p__thum.jpeg'), ('11', '4', 'http://pc-res.0angle.com/wxImg/NubUgeNQs9R8ZWTISaas7jaKLuPejvXHe1LfiGSQxvVbvE6cYoFY_8DB8emrIZhX.jpeg', 'http://pc-res.0angle.com/wxImg/NubUgeNQs9R8ZWTISaas7jaKLuPejvXHe1LfiGSQxvVbvE6cYoFY_8DB8emrIZhX_thum.jpeg'), ('12', '5', 'http://pc-res.0angle.com/wxImg/5BHdDi1BfkUVSvb6vADZGoFGE-EN7ZDjNQt6NKAKfdrwkXWpMlc9YaaDTCaC62ET.jpeg', 'http://pc-res.0angle.com/wxImg/5BHdDi1BfkUVSvb6vADZGoFGE-EN7ZDjNQt6NKAKfdrwkXWpMlc9YaaDTCaC62ET_thum.jpeg'), ('13', '5', 'http://pc-res.0angle.com/wxImg/JuDCMKagQ_Tbkhp0hKZ3i4sbx1pruzQwDmXIq6B2mfd-5D50gSuTNtpCCwaOAYJy.jpeg', 'http://pc-res.0angle.com/wxImg/JuDCMKagQ_Tbkhp0hKZ3i4sbx1pruzQwDmXIq6B2mfd-5D50gSuTNtpCCwaOAYJy_thum.jpeg');
COMMIT;

-- ----------------------------
--  Table structure for `za_core_event_rule`
-- ----------------------------
DROP TABLE IF EXISTS `za_core_event_rule`;
CREATE TABLE `za_core_event_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL COMMENT '关联的event的id 看 za_core_events',
  `rule_unit_fee` int(11) NOT NULL DEFAULT '0' COMMENT '这个条投资规则的unit金额，为空的话表示自由金额',
  `rule_award_long_desc` text NOT NULL COMMENT '这条投资规则的完成说明',
  `rule_short_desc` varchar(16) NOT NULL COMMENT '呈现在按钮上的投资规则简单提示',
  `rule_is_need_address` bit(1) NOT NULL COMMENT '是否需要物理地址',
  `rule_is_need_barcode` bit(1) NOT NULL COMMENT '是否需要二维码',
  `rule_max_amount` int(11) NOT NULL DEFAULT '0' COMMENT '规则的投资最大金额',
  `rule_min_amount` int(11) NOT NULL DEFAULT '0' COMMENT '最小金额',
  `rule_max_member` int(11) NOT NULL DEFAULT '0',
  `rule_min_member` int(11) NOT NULL DEFAULT '0',
  `rule_is_can_many_copy` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否允许购买多份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_core_event_rule`
-- ----------------------------
BEGIN;
INSERT INTO `za_core_event_rule` VALUES ('1', '1', '0', '捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧捧', '自由奉献', b'1', b'1', '100000', '10', '0', '0', b'0'), ('2', '1', '100', '我是一个很长很长很长的文档', '奉献1块钱', b'1', b'0', '100000', '1', '0', '0', b'1'), ('3', '1', '1000', '我又是一个很长很长的文档', '奉献10块钱', b'0', b'1', '100000', '10', '0', '0', b'1'), ('4', '2', '0', '哈哈哈哈哈', '自由奉献', b'0', b'0', '100000', '10', '0', '0', b'1');
COMMIT;

-- ----------------------------
--  Table structure for `za_core_events`
-- ----------------------------
DROP TABLE IF EXISTS `za_core_events`;
CREATE TABLE `za_core_events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_deadline_date` datetime DEFAULT NULL COMMENT 'event的截止时间',
  `event_title` varchar(64) NOT NULL COMMENT 'event列表页里面的抬头',
  `event_short_desc` varchar(128) NOT NULL COMMENT 'event列表页里面的短介绍文',
  `event_head_img` text NOT NULL COMMENT 'event列表页里面的头图url',
  `event_create_date` datetime NOT NULL COMMENT 'event的创建时间',
  `event_detail_url` text NOT NULL COMMENT 'event说明的长文档',
  `target_amount` int(11) NOT NULL COMMENT '众筹对象金额，到达金额暨为成立，金额和人数先到的为成立',
  `target_member` int(11) NOT NULL COMMENT '众筹对象人数，到达人数暨为成立，金额和人数先到的为成立',
  `is_event_succeed` bit(1) DEFAULT NULL COMMENT '最终是否众筹成功',
  `is_event_sample` bit(1) DEFAULT NULL COMMENT '是否为演示用的活动，并非真实的',
  `wx_card_title` varchar(32) NOT NULL COMMENT '微信转发时候的卡片抬头',
  `wx_card_img_url` text NOT NULL COMMENT '微信转发时候用的头像',
  `wx_card_desc` varchar(64) NOT NULL COMMENT '微信转发时候用的卡片的说明',
  `event_status` int(8) NOT NULL DEFAULT '0' COMMENT '0 ：不是众筹项目，肯定执行； 1：众筹中； 2：众筹成功； 3：众筹失败； 4：活动方取消',
  `event_time` datetime NOT NULL DEFAULT '2016-03-18 18:18:18' COMMENT '活动时间',
  `event_address` text,
  `wx_config_id` int(11) NOT NULL DEFAULT '1' COMMENT '活动所对应的config 也是标明是谁家的活动 如果在config中有无效的字段，全部用默认的第一个config来替代',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_core_events`
-- ----------------------------
BEGIN;
INSERT INTO `za_core_events` VALUES ('1', '2016-03-21 00:39:10', '【带摸】活动众筹，手感之旅01', '带摸（英文：demo）项目是为了方便用户快速理解和使用 捧场-大家筹 功能的演示项目，支付是真实的，感激之情是真实，但是权益是假设的，敬请知晓！', 'http://cpc.people.com.cn/NMediaFile/2014/0218/MAIN201402181440396908425232572.jpg', '2016-03-15 12:40:23', 'https://mp.weixin.qq.com/s?__biz=MzIyMjE0MjMxNw==&mid=401786414&idx=1&sn=ae576c6218f1183da8f28b4863adce50&scene=1&srcid=0315MhnC3oB1c1W6LBikowgs&key=710a5d99946419d9eb20a255d5238e30a381164f3613fcc4bf598b7ee23e0f345279958661002e59e4dfec3b7723713c&ascene=0&uin=MjMyOTI2NzI2MQ%3D%3D&devicetype=iMac+MacBookPro11%2C1+OSX+OSX+10.10.5+build(14F1021)&version=11020201&pass_ticket=QWKfz0b44T2FjPkFCT8YR3C869RoQ9Jdz35%2BQylFFmJBP1a19qqYrsB2PT5yZmhQ', '1000000', '50', null, null, '我是EVENT001', 'http://cpc.people.com.cn/NMediaFile/2014/0218/MAIN201402181440396908425232572.jpg', '我是EVENT001的内容君', '3', '2016-03-18 18:18:18', '我是活动地址，活动地址我是活动地址，活动地址我是活动地址，活动地址', '1'), ('2', '2016-03-21 00:42:19', '我是第二个活动', '我是第二个活动的介绍', 'http://pic21.nipic.com/20120603/2090953_114831520118_2.jpg', '2016-03-17 12:43:29', 'https://mp.weixin.qq.com/s?__biz=MzA5NDMxNDQxMw==&mid=453062788&idx=1&sn=5b5d35a3322789d36cdf126fc9589c3f&scene=0&key=710a5d99946419d91c9b87edc1b368ddb457ec798974c497408fad29b781688043cfca50a725ca8868c39a9016005275&ascene=0&uin=MjMyOTI2NzI2MQ%3D%3D&devicetype=iMac+MacBookPro11%2C1+OSX+OSX+10.10.5+build(14F1021)&version=11020201&pass_ticket=QWKfz0b44T2FjPkFCT8YR3C869RoQ9Jdz35%2BQylFFmJBP1a19qqYrsB2PT5yZmhQ', '10000', '0', null, b'1', '我是event002', 'http://pic30.nipic.com/20130310/11033874_150349561000_2.jpg', '我是event002的说明', '3', '2016-03-18 18:18:18', '我是活动地址，活动地址2', '1');
COMMIT;

-- ----------------------------
--  Table structure for `za_core_orders`
-- ----------------------------
DROP TABLE IF EXISTS `za_core_orders`;
CREATE TABLE `za_core_orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` datetime NOT NULL COMMENT '下单时间',
  `wx_user_id` int(11) DEFAULT NULL COMMENT '微信openId的userId',
  `front_user_id` int(11) DEFAULT NULL COMMENT '一般登录注册用户的信息id',
  `event_id` int(11) NOT NULL COMMENT 'link to za_core_events',
  `event_rule_id` int(11) NOT NULL COMMENT 'link to za_core_event_rule table',
  `pay_wx_union_order_id` int(11) DEFAULT NULL,
  `is_pay_succeed` bit(1) NOT NULL DEFAULT b'0' COMMENT '后端是否得到call的支付成功消息',
  `pay_succeed_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `pay_method` int(11) NOT NULL COMMENT '1: wx js pay 2:wx barcode pay 3: alipay',
  `qr_code_url` text COMMENT 'QR_code的图片url',
  `bar_code_url` text COMMENT '二维码的图片url',
  `user_realname` varchar(16) DEFAULT NULL,
  `user_phone` varchar(32) DEFAULT NULL,
  `user_address` text,
  `ordered_copies` int(11) DEFAULT NULL COMMENT '用户要了unit price的多少份',
  `total_fee` int(11) NOT NULL COMMENT '这个order一共应该付款多少钱',
  `mch_order_code` varchar(64) NOT NULL,
  `is_front_succeed` bit(1) NOT NULL DEFAULT b'0' COMMENT '前端是否回答支付成功',
  `front_succeed_time` datetime DEFAULT NULL,
  `wx_pay_callback_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_core_orders`
-- ----------------------------
BEGIN;
INSERT INTO `za_core_orders` VALUES ('1', '2016-03-14 19:23:50', '1', null, '1', '1', '2', b'1', '2016-03-14 19:39:44', '1', null, null, '', '', null, '1', '1', '1295518101201603141923500369', b'0', null, null), ('2', '2016-03-14 19:25:31', '1', null, '1', '1', '3', b'1', '2016-03-08 19:39:49', '1', null, null, '', '', null, '1', '1', '1295518101201603141925318214', b'0', null, null), ('3', '2016-03-14 19:25:49', '0', null, '1', '1', '4', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603141925499402', b'0', null, null), ('4', '2016-03-14 19:29:59', '0', null, '1', '1', '5', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603141929592884', b'0', null, null), ('5', '2016-03-15 10:45:01', '1', null, '0', '1', '6', b'1', '2016-03-17 19:39:55', '1', null, null, '', '', null, '2', '1', '1295518101201603151045019343', b'0', null, null), ('6', '2016-03-15 10:50:30', '0', null, '0', '1', '7', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603151050303282', b'0', null, null), ('7', '2016-03-15 20:14:24', '0', null, '1', '2', '8', b'0', null, '1', null, null, '', '', null, '2', '1', '1295518101201603152014246665', b'0', null, null), ('8', '2016-03-15 20:14:53', '0', null, '1', '2', '9', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603152014537248', b'0', null, null), ('9', '2016-03-16 10:50:28', '0', null, '1', '3', '10', b'0', null, '1', null, null, '', '', null, '1', '100', '1295518101201603161050280999', b'0', null, null), ('10', '2016-03-16 10:56:22', '0', null, '1', '3', '11', b'0', null, '1', null, null, '', '', null, '2', '2000', '1295518101201603161056229985', b'0', null, null), ('11', '2016-03-16 10:56:57', '0', null, '1', '3', '12', b'0', null, '1', null, null, '', '', null, '2', '2000', '1295518101201603161056573096', b'0', null, null), ('12', '2016-03-16 11:04:10', '0', null, '2', '4', '13', b'0', null, '1', null, null, '', '', null, '1', '100', '1295518101201603161104101301', b'0', null, null), ('13', '2016-03-16 11:04:23', '0', null, '2', '4', '14', b'0', null, '1', null, null, '', '', null, '1', '4556', '1295518101201603161104232731', b'0', null, null), ('14', '2016-03-16 11:05:49', '0', null, '2', '4', '15', b'0', null, '1', null, null, '', '', null, '1', '565', '1295518101201603161105495826', b'0', null, null), ('15', '2016-03-16 11:05:57', '0', null, '2', '4', '16', b'0', null, '1', null, null, '', '', null, '1', '560', '1295518101201603161105576327', b'0', null, null), ('16', '2016-03-16 11:06:06', '0', null, '2', '4', '17', b'0', null, '1', null, null, '', '', null, '1', '500', '1295518101201603161106068018', b'0', null, null), ('17', '2016-03-16 12:12:52', '0', null, '1', '3', '19', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603161212522737', b'0', null, null), ('18', '2016-03-16 12:16:59', '0', null, '1', '1', '20', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603161216592649', b'0', null, null), ('19', '2016-03-16 12:19:45', '0', null, '1', '2', '21', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603161219450467', b'0', null, null), ('20', '2016-03-16 12:24:18', '0', null, '1', '2', '22', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603161224188744', b'0', null, null), ('21', '2016-03-16 12:46:42', '1', null, '1', '2', '23', b'1', '2016-03-16 14:51:09', '1', null, null, '', '', null, '1', '1', '1295518101201603161246422752', b'1', '2016-03-16 12:46:50', '4'), ('22', '2016-03-16 12:51:38', '1', null, '1', '2', '24', b'1', '2016-03-16 15:56:34', '1', null, null, '', '', null, '1', '1', '1295518101201603161251388274', b'1', '2016-03-16 12:51:46', '8'), ('23', '2016-03-16 13:05:25', '0', null, '1', '2', '25', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603161305258504', b'1', '2016-03-16 13:11:38', null), ('24', '2016-03-16 13:11:58', '1', null, '1', '2', '26', b'1', '2016-03-16 14:46:33', '1', null, null, '', '', null, '1', '1', '1295518101201603161311580119', b'1', '2016-03-16 13:12:07', '3'), ('25', '2016-03-16 14:41:49', '1', null, '1', '2', '27', b'1', '2016-03-16 14:46:08', '1', null, null, '', '', null, '1', '1', '1295518101201603161441494419', b'1', '2016-03-16 14:45:35', '2'), ('26', '2016-03-16 14:45:51', '1', null, '1', '3', '28', b'1', '2016-03-16 14:45:59', '1', null, null, '', '', null, '1', '1', '1295518101201603161445518228', b'1', '2016-03-16 14:46:02', '1'), ('27', '2016-03-16 15:47:43', '1', null, '1', '2', '29', b'1', '2016-03-16 15:52:00', '1', null, null, '', '', null, '1', '1', '1295518101201603161547433094', b'1', '2016-03-16 15:50:17', '6'), ('28', '2016-03-16 15:50:32', '1', null, '1', '2', '30', b'1', '2016-03-16 15:50:46', '1', null, null, '', '', null, '1', '1', '1295518101201603161550320013', b'1', '2016-03-16 15:50:51', '5'), ('29', '2016-03-16 15:53:12', '1', null, '1', '2', '31', b'1', '2016-03-16 15:57:37', '1', null, null, '', '', null, '1', '1', '1295518101201603161553125653', b'1', '2016-03-16 15:55:26', '7'), ('30', '2016-03-16 15:56:28', '1', null, '1', '2', '32', b'1', '2016-03-16 15:56:36', '1', null, null, '', '', null, '1', '1', '1295518101201603161556288715', b'1', '2016-03-16 15:56:38', '9'), ('31', '2016-03-16 15:58:48', '1', null, '1', '2', '33', b'1', '2016-03-16 16:03:27', '1', null, null, '', '', null, '1', '1', '1295518101201603161558481415', b'1', '2016-03-16 15:58:56', '10'), ('32', '2016-03-16 16:00:18', '1', null, '1', '2', '34', b'1', '2016-03-16 16:01:05', '1', null, null, '', '', null, '1', '1', '1295518101201603161600185716', b'1', '2016-03-16 16:01:14', '11'), ('33', '2016-03-16 16:01:40', '1', null, '1', '2', '35', b'1', '2016-03-16 16:01:46', '1', null, null, '', '', null, '1', '1', '1295518101201603161601407798', b'1', '2016-03-16 16:01:53', '12'), ('34', '2016-03-16 16:03:20', '1', null, '1', '2', '36', b'1', '2016-03-16 16:03:25', '1', null, null, '', '', null, '1', '1', '1295518101201603161603201292', b'1', '2016-03-16 16:03:27', '13'), ('35', '2016-03-16 21:55:17', '1', null, '1', '2', '37', b'1', '2016-03-16 21:59:44', '1', null, null, '', '', null, '1', '1', '1295518101201603162155179544', b'1', '2016-03-16 21:55:29', '14'), ('36', '2016-03-16 22:06:28', '1', null, '1', '3', '38', b'1', '2016-03-16 22:06:51', '1', null, null, '', '', null, '1', '1', '1295518101201603162206288961', b'1', '2016-03-16 22:06:39', '15'), ('37', '2016-03-16 22:10:27', '1', null, '1', '3', '39', b'1', '2016-03-16 22:10:51', '1', null, null, '', '', null, '1', '1', '1295518101201603162210274085', b'1', '2016-03-16 22:10:38', '16'), ('38', '2016-03-16 23:20:14', '1', null, '1', '2', '40', b'1', '2016-03-16 23:20:34', '1', null, null, '真实姓名', '13918765390', '这是地址地址地址', '1', '1', '1295518101201603162320145834', b'1', '2016-03-16 23:20:23', '17'), ('39', '2016-03-17 22:54:49', '1', null, '1', '2', '41', b'1', '2016-03-17 22:55:15', '1', null, null, '说的话', '13661543584', '我的地址', '1', '1', '1295518101201603172254492486', b'1', '2016-03-17 22:55:02', '18'), ('40', '2016-03-18 22:48:36', '1', null, '2', '4', '42', b'1', '2016-03-18 22:48:58', '1', null, null, '', '', null, '1', '1', '1295518101201603182248366416', b'0', null, '19'), ('41', '2016-03-19 21:22:10', '1', null, '1', '2', '43', b'1', '2016-03-19 21:22:36', '1', null, null, '', '', null, '2', '1', '1295518101201603192122100589', b'0', null, '20'), ('42', '2016-03-20 21:31:28', '1', null, '1', '1', '44', b'1', '2016-03-20 21:32:53', '1', null, null, '', '', null, '1', '1', '1295518101201603202131289401', b'1', '2016-03-20 21:31:43', '21'), ('43', '2016-03-22 12:08:00', '2', null, '1', '1', '45', b'0', null, '1', null, null, 'sb', '110', null, '1', '9999900', '1295518101201603221208005135', b'0', null, null), ('44', '2016-03-22 12:15:15', '3', null, '1', '2', '46', b'0', null, '1', null, null, '', '', null, '2', '200', '1295518101201603221215158743', b'0', null, null), ('45', '2016-03-22 13:53:20', '1', null, '1', '2', '47', b'0', null, '1', null, null, '', '', null, '1', '1', '1295518101201603221353204309', b'1', '2016-03-22 13:53:35', null), ('46', '2016-03-22 18:49:43', '1', null, '2', '4', '48', b'1', '2016-03-22 18:49:54', '1', 'http://pc-res.0angle.com/qrcodes/qrcode46.jpg', 'http://pc-res.0angle.com/barcodes/barcode46.jpg', '', '', null, '1', '1', '1295518101201603221849436039', b'1', '2016-03-22 18:49:56', '22'), ('47', '2016-03-22 18:53:13', '1', null, '1', '1', '49', b'1', '2016-03-22 18:53:19', '1', 'http://pc-res.0angle.com/qrcodes/qrcode47.jpg', 'http://pc-res.0angle.com/barcodes/barcode47.jpg', '', '', null, '1', '1', '1295518101201603221853137369', b'1', '2016-03-22 18:53:20', '23'), ('48', '2016-03-22 19:10:08', '1', null, '1', '1', '50', b'1', '2016-03-22 19:10:14', '1', 'http://pc-res.0angle.com/qrcodes/qrcode48.jpg', 'http://pc-res.0angle.com/barcodes/barcode48.jpg', '', '', null, '1', '1', '1295518101201603221910080323', b'1', '2016-03-22 19:10:16', '24'), ('49', '2016-03-22 22:27:26', '1', null, '2', '4', '51', b'1', '2016-03-22 22:27:37', '1', 'http://pc-res.0angle.com/qrcodes/qrcode49.jpg', 'http://pc-res.0angle.com/barcodes/barcode49.jpg', '', '', null, '1', '1', '1295518101201603222227265214', b'1', '2016-03-22 22:27:41', '25'), ('50', '2016-03-22 23:18:59', '1', null, '1', '2', '52', b'1', '2016-03-22 23:19:08', '1', 'http://pc-res.0angle.com/qrcodes/qrcode50.jpg', 'http://pc-res.0angle.com/barcodes/barcode50.jpg', '', '', null, '1', '1', '1295518101201603222318595572', b'1', '2016-03-22 23:19:11', '26'), ('51', '2016-03-23 10:41:11', '1', null, '1', '2', '53', b'1', '2016-03-23 10:41:26', '1', 'http://pc-res.0angle.com/qrcodes/qrcode51.jpg', 'http://pc-res.0angle.com/barcodes/barcode51.jpg', '', '', null, '1', '1', '1295518101201603231041119612', b'1', '2016-03-23 10:41:28', '27'), ('52', '2016-03-23 12:27:50', '1', null, '1', '2', '54', b'1', '2016-03-23 12:28:12', '1', 'http://pc-res.0angle.com/qrcodes/qrcode52.jpg', 'http://pc-res.0angle.com/barcodes/barcode52.jpg', '', '', null, '1', '1', '1295518101201603231227507456', b'1', '2016-03-23 12:28:14', '28'), ('53', '2016-03-23 21:42:05', '1', null, '1', '1', '55', b'1', '2016-03-23 21:42:16', '1', 'http://pc-res.0angle.com/qrcodes/qrcode53.jpg', 'http://pc-res.0angle.com/barcodes/barcode53.jpg', '', '', null, '1', '1', '1295518101201603232142059353', b'1', '2016-03-23 21:42:18', '29');
COMMIT;

-- ----------------------------
--  Table structure for `za_core_send_award_log`
-- ----------------------------
DROP TABLE IF EXISTS `za_core_send_award_log`;
CREATE TABLE `za_core_send_award_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mch_billno` varchar(64) DEFAULT NULL,
  `send_name` varchar(64) DEFAULT NULL,
  `re_openid` varchar(64) DEFAULT NULL,
  `total_amount` int(11) DEFAULT NULL,
  `wishing` varchar(11) DEFAULT NULL,
  `client_ip` varchar(32) DEFAULT NULL,
  `act_name` varchar(64) DEFAULT NULL,
  `remark` text,
  `nonce_str` varchar(64) DEFAULT NULL,
  `mch_id` varchar(64) DEFAULT NULL,
  `wxappid` varchar(64) DEFAULT NULL,
  `total_num` int(11) DEFAULT NULL,
  `send_result` int(8) DEFAULT NULL COMMENT '0 为为止， 1 为成功  2 为失败',
  `send_time` datetime DEFAULT NULL,
  `send_result_msg` varchar(128) DEFAULT NULL COMMENT '失败原因说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_core_send_award_log`
-- ----------------------------
BEGIN;
INSERT INTO `za_core_send_award_log` VALUES ('1', '1295518101201603211431044121', '我是商户名称：捧场', 'oecAVt4hSa4941GwbIWkLDhrTTwA', '100', '我是祝福语', '127.0.0.1', '我是活动名称', '我是备注', '2016032114310441339', '1295518101', 'wx9b3d1cb48fb48ff4', '1', '0', '2016-03-21 14:31:04', '帐号余额不足，请到商户平台充值后再重试'), ('2', '1295518101201603211502102202', '我是商户名称：捧场', 'oecAVt4hSa4941GwbIWkLDhrTTwA', '100', '我是祝福语', '127.0.0.1', '我是活动名称', '我是备注', '2016032115021022080', '1295518101', 'wx9b3d1cb48fb48ff4', '1', '0', '2016-03-21 15:02:10', '帐号余额不足，请到商户平台充值后再重试');
COMMIT;

-- ----------------------------
--  Table structure for `za_front_user_wx`
-- ----------------------------
DROP TABLE IF EXISTS `za_front_user_wx`;
CREATE TABLE `za_front_user_wx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_info_id` int(11) DEFAULT NULL COMMENT '对应以后的用户系统扩展',
  `wx_open_id` varchar(64) NOT NULL COMMENT 'wx中的openID，如果，没有的话可以创建一个以小写nb开头的字符串来代替',
  `wx_app_id` varchar(64) NOT NULL DEFAULT 'n/a' COMMENT 'openId所对饮的wx公众号app id',
  `wx_union_id` varchar(255) DEFAULT NULL COMMENT '微信的union id',
  `wx_nickname` varchar(32) DEFAULT NULL,
  `wx_sex` int(11) DEFAULT NULL COMMENT '1 是男的， 2是女的',
  `head_img_url` text COMMENT 'wx的头图URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_front_user_wx`
-- ----------------------------
BEGIN;
INSERT INTO `za_front_user_wx` VALUES ('1', '0', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'wx9b3d1cb48fb48ff4', null, 'Shen.Yuhang', '1', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM59M7lUOUYBIaXOgwCDRsk0SL5ic6micqE2CZeSRTr7ASibTgk5f1AJs921ycyZzcuRwhxfk0bZODu6tFoF8ecds32vAUiaicevtnQc/0'), ('2', '0', 'oecAVt9fzT2Rfk4hMgQgQPijg7Vk', 'wx9b3d1cb48fb48ff4', null, 'Wu Zheng', '1', 'http://wx.qlogo.cn/mmopen/qe067V2LHj8Cbibt3bmTf39p11ulbP3jiajwsDc3TCsvSKPfKiasKrtXbfMko7jqqlBeBcpxK3I1zW0hEV7HMBjrFvhyh6V9Twa/0'), ('3', '0', 'oecAVt-dKs3zNHabvVuCgST171Zc', 'wx9b3d1cb48fb48ff4', null, '树晓懒', '2', 'http://wx.qlogo.cn/mmopen/E5vsWhOQygMR9LHSAib0FkuibrqXIiab5xUegfXPL1YZqkNP79K2yLHz9SXqZO6ybJFX7o053YRt1JE8ic5L96aK4nibVjzS4HiaoX/0');
COMMIT;

-- ----------------------------
--  Table structure for `za_front_wx_config`
-- ----------------------------
DROP TABLE IF EXISTS `za_front_wx_config`;
CREATE TABLE `za_front_wx_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payKey` varchar(64) NOT NULL,
  `mchId` varchar(64) NOT NULL,
  `wxappid` varchar(64) NOT NULL,
  `certFile_p12` text NOT NULL,
  `server_default_ip` varchar(64) NOT NULL,
  `app_secret` varchar(64) NOT NULL,
  `order_default_expire_time` int(11) NOT NULL,
  `wx_pay_notify_url` text NOT NULL,
  `encoding_aes_key` varchar(64) NOT NULL,
  `config_name` varchar(64) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `is_default` bit(1) NOT NULL,
  `server_name` varchar(64) NOT NULL DEFAULT 'http://',
  `tplmsg_pay_success` varchar(64) NOT NULL DEFAULT 'na',
  `resource_path` varchar(256) NOT NULL DEFAULT '' COMMENT '存放资源文件以及用户上传文件的local路径',
  `resource_brows_path` varchar(128) NOT NULL COMMENT '从外部通过服务器访问的时候的访问路径',
  `cert_p12_bin` blob COMMENT 'certFilebinary',
  `company_name` varchar(64) DEFAULT NULL,
  `company_logo_url` varchar(128) DEFAULT NULL,
  `tplmsg_cf_result` varchar(64) NOT NULL DEFAULT '' COMMENT '众筹结果通知的微信模板编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_front_wx_config`
-- ----------------------------
BEGIN;
INSERT INTO `za_front_wx_config` VALUES ('1', '0anglePengChang2016PengChang2016', '1295518101', 'wx9b3d1cb48fb48ff4', '/usr/local/certs/apiclient_cert.p12', '127.0.0.1', '188be723d1bab5a44a7fdeeff06cc0f3', '7200', 'http://pc.0angle.com/wxPayCallback.html', '1iYFTRwrYWxAlOcxo9wdoWn38f7KGrMtegdvzV0gcqT', '捧场', b'1', b'1', 'http://pc.0angle.com', 'yLG3Xsi-h0iGgnnsrlvBqA5oa3R3wDhSoTxx2UlCdG4', '/usr/local/www/pc-res.0angle.com', 'http://pc-res.0angle.com', 0x30820b4002010330820b0a06092a864886f70d010701a0820afb04820af730820af33082058706092a864886f70d010706a0820578308205740201003082056d06092a864886f70d010701301c060a2a864886f70d010c0106300e0408543ba5c8aff70d6b02020800808205403ddf06be632eb29449bce5c0523189f23e999cc3221898769395f72fe6312c8ef0b428f706b6669b3aee9b15b79455d70b51920f750904e4808d1c4c8451482a42f81ad0e373990ee13cc7411e130f360c2b64a09d0990b36d5452b0bf47881ae58b779fa0cdde502e63547c8da49463c4713e829a66a6c063244e73684037ad301d2d44106053eda8df18d937524a26eaf51df0f25fd7666d9653224a3844a73bf409c7be946f9dc745e66cd52db801230af11befba4d60a00b4c324fb8f35a9323980a69e156557d29bdd1439df767fc5351bc447c7610368010b08934a6ef700c00983bfb47c0765b10a5602cf27ae5338b348a995be60c81a239c8135edab11fd96d5cf4f779cfcaf39fabfbada85be4229feac8451a5569096e0997bd7da8d64eef5f68d31df0613a9adbc171927f15383a4bd244b70140b18b68b5a32ee46f562105744ca928fae60cb214a1d3cc21ebb88fd159b9d9b0e258d6040320fa789da69dffbc0e3b5e15a35c50920d0d7534f67e58d7571f1bdefe5c8d97a76a85bf7b154d07be8b4413d87544872ae1a08970a181a4a850d02eb6c4840a52084e3660cbd6ed1fa9ab7f0fc8571194a1f353aa62898f0e6845c178b2d5db790520a37614fb0a1f88e386cdd16862c4bc5528173e2588b350feea8506c70d0c0ffd8b5bb0e61510c0a61d5c465c40a823ac638893a5714101731f25ba3454772e9008c09c13f428359352b5dd0478763d17a8cd21c20ebb126b7987614a549df37d7d16cddd133f2ac47f5e3dfa829625f031c9f117ceb8f09373c5f7b1e5e677bd24c5820d320f7dbd139d381d693f08a802e92a15f2419198d9e3b12362b26f79dfa20c6dfef46822562de8f29d4573a690942710633e95ee246207d3982d30b0559a177e1577e4bcb5fccbc5712bbda016484ad51f3e45d642007dbce8dc5ed26a82fab599f33bde3a5a14cc1ce9ed576291762d676c94cebbc2d697e5846c16922be84b1f9ee6282c9546c890ac6d3bee984c566195e7ad9cff53e53829ded87b346f4060373398a9021fa29f954cf2cd8c5924eee5dd67364ebcd2a16faef291167a77d7fd996ddf294ec9a1d74434c40ce53f0eced10bad5b806a3e52a12d0508bf3cbf8c9ddda76f0e48c776264686f46f3192a2085446da445a460b0230add132722b461c3761cd9e05d19aced0558c347e5f33574f31ec82a798e5fe4b3507b26dd8751959ef25cda466fd548c7540ff6d963b67928d48a101e5768f4c7b0a21d34bca7d3d9b7f13e75217eac50ef66b3346ae84a0a0641f5bf04626903a6c8719e59a9ea401646632294680f49e2c241306652cbf23531fb22ca4a06d11d0cedc4919be4eb9ac3b35b9420fa9f7a5309ae99ee9e1161c9ff5fd66a79c9187669a9e2a7b7625326221f0cc4ec49802df6a483275c0385789eb2ff4c988ba02dfe9d1a23132f12928804cb926b4d07e61d8bed4762b4e37de4b7dc9f1bbe5cde3c820c02c8abeb706f08b059fd845956a7d47def0ed95041e3fb47fe7fd03c8fd612ac6996187bcedf11814d510a325fed9835e94997b9db2c3fa37165fc3c8e6a098823105a8c706302f3e7b88d7d05c7f84b86771ff037fdfe9720f64bd18a0bf98b47712c5c7e70a87b0a31ad6291d220566142fc6dd2dfceb071b7674a2d8fcc202101e259a9b137f260c41c4af8821fbdb4aec4d104a63eeb98ef947e64a126285c28361078c822921ebc45f46269b051040192e41f0757cb9c8418011b138d450397c53a8036a87542113b91f58380e4f9f9328cd508ee9ff5f3938270660860a5c2bc44721f83bc0fdb612daa60b386a074f0d5225c84f5b25eb90b6faf664e4e27b6a17a50d0ca683d429de406c9be254948c691c2695a13082056406092a864886f70d010701a0820555048205513082054d30820549060b2a864886f70d010c0a0102a08204ee308204ea301c060a2a864886f70d010c0103300e0408f0cb7c25d2157f7402020800048204c89250f902234c6be42c49587094da625ff1bd872db05e84635ce527f98c0715c95ab3f827a590ce679968db566a255265d76ebe42c18fad20810bbd2cecfbc3987509e0dbe4fd946b306ea6040d080df851e17e8ad0a4c08ec0c0d35bbf78eeb14dd35dd5344a0a90931956ff980a8f8df9923c18b375c8322429ebe14dd9c8c8a4efa7665a177a127801d60d4383dd352a72398ad50cf8a6cbbebc065fbd4311dca85b2fddc398bebbcc11c3e2ad469fb1811df54e2d3e0f03ca2a493f26dfb6864f1bcd484d73f624f4a1f2b20b33f0034b51cd99021bf981e281afcad192a7c74527b7bfc05cc83ff27b77832c23f709280cb5719c3f8010d8c4dbcd9ca6b0028c7bfaf83bad237690f3b6d4f28b8e204e0a3198b84b3fbfafed1f6b03fe00afc35c2e2a95923637185745d78c3369a55710856f12d4f9a04c832a2b885849e122bc3e7db03f9353d8b1e459c4564bfeec1fcca9a193508278754c3ec540883fcc7e0a36e22c2b05149435ca702d05323504d2972c331e0f3deed9908fa07e52504ca7ecf1d70d1a1dbdd1856119bce901bd8608a882496c7053e798b5d506efd3f0f6f10bc1e68aa08aad60a3d17cc47acc6ae564c76b7a99d2c5361fd64992712b83b86bbe1f1e3ddf423d1ff18321f8b4be23b64d9cdbcda7813304b3e8b341ffdda0d819c3fed6027f18fcc78394d7ad1e352fde4a79d9ffc3e4eda98a929b2405b84dc8a558b26b3af4866d15721554296664b384528a671646aa4a449d98e967b9f0de5146c6fce274023b4813ce4365d1bc7abf80e02ec44f6536160cb49b8a89957b3e6e6b38597e419bc8c2efb04e43be657fe6e3ead6571633c7e053d1e1ad81c82ade1f43c5800e04581d3aa56b4031420724fcb33423bef7fe63e4d77872219ad686c4b70c064deafd6d0fdda006581a062f5d678e603550fa00315aa5be330200b0b971f24e75fc62e04ba8da3154e2c2dc5668da26a7d7341b90025192645c7a0b18ca3c13dd0e9ca7acfb5e6c34009526b766b19b03eeb4ff7a3f812d6ac868e4b9b7884a0dea0fe61cb0f8ded3dba5ffbf6302fd77cd8b5bf4ac3d802f449c0dc926c912dc4891dc9d2c2f379cb9ff94fe81cdc74bdd3575899ffb6a831d2425ce0acc52380d9b3cb60fe1c7c781e6f3eef6aa10b5230670fb84e36418f609e7331de6fe9b9f54270bef80ffe5aab2bca38a585cb273cbfbcbc2efbd52e72c935b383e616fcc18e5b9fe4253911592a7adcb21de4f314c25641fb19a9b9ebb0338a7abe60d936216fec0c24e360eaf26d2ee6d9f6105faa6b4a4702290b4678088d3040b17cee11284e2e38330d6483fc0c8989ec538b04cde87bf81c70455af34c6c908c9f19731d6ffb2572c77481a174bdc059de0ab7d787bff9a9c7f7a9493716690b586909f7e3980dca2ddbfa0861eb6a65d22372dcb85f3e69776c43f35905f645a9207c5b91a95107dd558e2b3efc805365240aea4f6874953a33a863825b7ed26f95bb335e46f3a7cee6cd314d400e5ea6994e35c272a4d339b2988ad1a47a877ba9dcea38eca9f5c79071266725cdae0806395e585981cbe376e18880e4878b115c8076707ab7b02248af54d875ca9cf4e6bd2bf0dd2cfd8c38dc973000eacc8e149cbf389d6b0dee1abfa995529a7ecb93045abe9627c51490108838c176000fb398858536737e403040da08c0e84fde4e6e490401f7e3e65733148302106092a864886f70d01091431141e12004d004d0050006100790043006500720074302306092a864886f70d0109153116041487f46fbbf31f120a6f1573e1b401ca9c33edc345302d3021300906052b0e03021a05000414391f32a1224448b419b4d8a70e13561e13824139040853a70e68bc9c0e17, '上海零角互联网信息服务有限公司', 'http://pc.0angle.com/sp/img/0angle_icon.png', 'EwXNP-7VAd0UfmoeD6P8VnQIDzD32s0rutfV6rcyijI'), ('2', 'CAIZHIDAO2016CAIZHIDAO2016CAIZHI', '1304560401', 'wxad7987ba40989a97', '/Users/yuhangs/Documents/WorkingStuff/zAnGle/职业规划师项目/wechatPayCert/apiclient_cert.p12', '127.0.0.1', 'b3937255dcc5e4953798146c547a4e92', '7200', 'http://t03.0angle.com/test/nbBase/wxPayCallback.html', 'n/a', '才知道', b'0', b'0', 'http://t03.0angle.com', 'na', '', '', '', null, null, '');
COMMIT;

-- ----------------------------
--  Table structure for `za_incoming_history`
-- ----------------------------
DROP TABLE IF EXISTS `za_incoming_history`;
CREATE TABLE `za_incoming_history` (
  `id` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  `incomingAmount` decimal(11,3) NOT NULL,
  `incomingAmountDate` date NOT NULL,
  `incomingMethod` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4h6046no21uom9kvqdq7uqxvi` (`incomingMethod`),
  KEY `FK_duoai0h4wl9x0s38umekm7s9b` (`projectId`),
  CONSTRAINT `FK_4h6046no21uom9kvqdq7uqxvi` FOREIGN KEY (`incomingMethod`) REFERENCES `za_pay_method` (`id`),
  CONSTRAINT `FK_duoai0h4wl9x0s38umekm7s9b` FOREIGN KEY (`projectId`) REFERENCES `za_projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_movie`
-- ----------------------------
DROP TABLE IF EXISTS `za_movie`;
CREATE TABLE `za_movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movTitle` varchar(32) DEFAULT NULL,
  `movUrl` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_outgoing_history`
-- ----------------------------
DROP TABLE IF EXISTS `za_outgoing_history`;
CREATE TABLE `za_outgoing_history` (
  `id` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  `outgoingAmount` decimal(11,3) NOT NULL,
  `outgoingDate` date NOT NULL,
  `outgoingUserId` int(11) NOT NULL,
  `outgoingMethodId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lcpam25ua50arty1emqt14j38` (`outgoingMethodId`),
  KEY `FK_e471b6qpulprkiggp53aieylj` (`outgoingUserId`),
  KEY `FK_51x2qnnwasvchv9v4fmrm3g7y` (`projectId`),
  CONSTRAINT `FK_51x2qnnwasvchv9v4fmrm3g7y` FOREIGN KEY (`projectId`) REFERENCES `za_projects` (`id`),
  CONSTRAINT `FK_e471b6qpulprkiggp53aieylj` FOREIGN KEY (`outgoingUserId`) REFERENCES `za_users` (`id`),
  CONSTRAINT `FK_lcpam25ua50arty1emqt14j38` FOREIGN KEY (`outgoingMethodId`) REFERENCES `za_pay_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_pay_method`
-- ----------------------------
DROP TABLE IF EXISTS `za_pay_method`;
CREATE TABLE `za_pay_method` (
  `id` int(11) NOT NULL,
  `payMethod` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_pay_wx_union_id`
-- ----------------------------
DROP TABLE IF EXISTS `za_pay_wx_union_id`;
CREATE TABLE `za_pay_wx_union_id` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_configure_id` int(11) NOT NULL COMMENT 'link to za_core_wx_config',
  `wx_device_info` varchar(32) NOT NULL,
  `wx_nonce_str` varchar(64) NOT NULL,
  `wx_order_body` text NOT NULL,
  `wx_order_detail` text NOT NULL,
  `wx_order_attach` text NOT NULL,
  `wx_out_trade_no` varchar(64) NOT NULL,
  `wx_fee_type` varchar(8) NOT NULL DEFAULT 'CNY',
  `wx_total_fee` int(11) NOT NULL COMMENT '以分为单位',
  `wx_spbill_create_ip` varchar(32) NOT NULL,
  `wx_time_start` datetime NOT NULL COMMENT '订单开始有效时间',
  `wx_time_expire` datetime NOT NULL COMMENT '订单有效结束时间',
  `wx_goods_tag` varchar(16) NOT NULL DEFAULT 'WXG',
  `wx_trade_type` varchar(64) NOT NULL COMMENT '支付类型',
  `wx_product_id` int(11) NOT NULL COMMENT '就是event_id in za_core_event',
  `wx_prepay_id` varchar(64) DEFAULT NULL COMMENT 'prepay_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_pay_wx_union_id`
-- ----------------------------
BEGIN;
INSERT INTO `za_pay_wx_union_id` VALUES ('1', '1', 'n/a', '2016031419223763929', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603141922376393', 'CNY', '1', '127.0.0.1', '2016-03-14 19:22:37', '2016-03-14 21:22:37', 'WXG', 'JSAPI', '1', null), ('2', '1', 'n/a', '2016031419235003637', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603141923500369', 'CNY', '1', '127.0.0.1', '2016-03-14 19:23:50', '2016-03-14 21:23:50', 'WXG', 'JSAPI', '1', null), ('3', '1', 'n/a', '2016031419253182130', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603141925318214', 'CNY', '1', '127.0.0.1', '2016-03-14 19:25:31', '2016-03-14 21:25:31', 'WXG', 'JSAPI', '1', null), ('4', '1', 'n/a', '2016031419254994048', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603141925499402', 'CNY', '1', '127.0.0.1', '2016-03-14 19:25:49', '2016-03-14 21:25:49', 'WXG', 'JSAPI', '1', null), ('5', '1', 'n/a', '2016031419295928889', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603141929592884', 'CNY', '1', '127.0.0.1', '2016-03-14 19:29:59', '2016-03-14 21:29:59', 'WXG', 'JSAPI', '1', 'wx2016031419300041b6cacad00022668622'), ('6', '1', 'n/a', '2016031510450193418', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603151045019343', 'CNY', '1', '127.0.0.1', '2016-03-15 10:45:01', '2016-03-15 12:45:01', 'WXG', 'JSAPI', '1', 'wx20160315104502e0e00a972c0619755359'), ('7', '1', 'n/a', '2016031510503032795', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603151050303282', 'CNY', '1', '127.0.0.1', '2016-03-15 10:50:30', '2016-03-15 12:50:30', 'WXG', 'JSAPI', '1', 'wx20160315105030d94dcff50f0890905920'), ('8', '1', 'n/a', '2016031520142466655', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603152014246665', 'CNY', '1', '127.0.0.1', '2016-03-15 20:14:24', '2016-03-15 22:14:24', 'WXG', 'JSAPI', '1000002', 'wx201603152014252e0a9e6b710412640641'), ('9', '1', 'n/a', '2016031520145372467', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603152014537248', 'CNY', '1', '127.0.0.1', '2016-03-15 20:14:53', '2016-03-15 22:14:53', 'WXG', 'JSAPI', '1000002', 'wx2016031520145435e88ce18f0108057820'), ('10', '1', 'n/a', '2016031610502809868', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161050280999', 'CNY', '100', '127.0.0.1', '2016-03-16 10:50:28', '2016-03-16 12:50:28', 'WXG', 'JSAPI', '1000003', 'wx20160316105029feac35e68c0908707200'), ('11', '1', 'n/a', '2016031610562299884', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161056229985', 'CNY', '2000', '127.0.0.1', '2016-03-16 10:56:22', '2016-03-16 12:56:22', 'WXG', 'JSAPI', '1000003', 'wx20160316105624f6056ba2a00423511725'), ('12', '1', 'n/a', '2016031610565730983', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161056573096', 'CNY', '2000', '127.0.0.1', '2016-03-16 10:56:57', '2016-03-16 12:56:57', 'WXG', 'JSAPI', '1000003', 'wx2016031610565837375c32150936827841'), ('13', '1', 'n/a', '201603161104101306', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161104101301', 'CNY', '100', '127.0.0.1', '2016-03-16 11:04:10', '2016-03-16 13:04:10', 'WXG', 'JSAPI', '2000004', 'wx20160316110411855ef2ada40462939663'), ('14', '1', 'n/a', '2016031611042327259', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161104232731', 'CNY', '4556', '127.0.0.1', '2016-03-16 11:04:23', '2016-03-16 13:04:23', 'WXG', 'JSAPI', '2000004', 'wx20160316110423be5b6ddce90543101924'), ('15', '1', 'n/a', '2016031611054958276', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161105495826', 'CNY', '565', '127.0.0.1', '2016-03-16 11:05:49', '2016-03-16 13:05:49', 'WXG', 'JSAPI', '2000004', 'wx2016031611055086b767b90c0416133760'), ('16', '1', 'n/a', '2016031611055763242', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161105576327', 'CNY', '560', '127.0.0.1', '2016-03-16 11:05:57', '2016-03-16 13:05:57', 'WXG', 'JSAPI', '2000004', 'wx20160316110557d87cc4e1120506574523'), ('17', '1', 'n/a', '2016031611060680188', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161106068018', 'CNY', '500', '127.0.0.1', '2016-03-16 11:06:06', '2016-03-16 13:06:06', 'WXG', 'JSAPI', '2000004', 'wx201603161106077313a99bfa0558158467'), ('18', '1', 'n/a', '2016031612104914645', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161210491468', 'CNY', '1', '127.0.0.1', '2016-03-16 12:10:49', '2016-03-16 14:10:49', 'WXG', 'JSAPI', '1000003', null), ('19', '1', 'n/a', '201603161212522732', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161212522737', 'CNY', '1', '127.0.0.1', '2016-03-16 12:12:52', '2016-03-16 14:12:52', 'WXG', 'JSAPI', '1000003', 'wx2016031612125354facd08230172177346'), ('20', '1', 'n/a', '2016031612165926472', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161216592649', 'CNY', '1', '127.0.0.1', '2016-03-16 12:16:59', '2016-03-16 14:16:59', 'WXG', 'JSAPI', '1000001', 'wx20160316121659b8b4dd52bb0623984933'), ('21', '1', 'n/a', '2016031612194504675', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161219450467', 'CNY', '1', '127.0.0.1', '2016-03-16 12:19:45', '2016-03-16 14:19:45', 'WXG', 'JSAPI', '1000002', 'wx20160316121946a7b3f2ecf10980463465'), ('22', '1', 'n/a', '2016031612241887441', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161224188744', 'CNY', '1', '127.0.0.1', '2016-03-16 12:24:18', '2016-03-16 14:24:18', 'WXG', 'JSAPI', '1000002', 'wx20160316122419a21dc294a00747300889'), ('23', '1', 'n/a', '2016031612464227532', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161246422752', 'CNY', '1', '127.0.0.1', '2016-03-16 12:46:42', '2016-03-16 14:46:42', 'WXG', 'JSAPI', '1000002', 'wx20160316124643e63d65b2090722708049'), ('24', '1', 'n/a', '2016031612513882622', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161251388274', 'CNY', '1', '127.0.0.1', '2016-03-16 12:51:38', '2016-03-16 14:51:38', 'WXG', 'JSAPI', '1000002', 'wx20160316125139a5257066370852452274'), ('25', '1', 'n/a', '2016031613052585025', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161305258504', 'CNY', '1', '127.0.0.1', '2016-03-16 13:05:25', '2016-03-16 15:05:25', 'WXG', 'JSAPI', '1000002', 'wx20160316130527dee2805cc80310979432'), ('26', '1', 'n/a', '2016031613115801176', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161311580119', 'CNY', '1', '127.0.0.1', '2016-03-16 13:11:58', '2016-03-16 15:11:58', 'WXG', 'JSAPI', '1000002', 'wx201603161311593a99c5e3560401011646'), ('27', '1', 'n/a', '2016031614414944161', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161441494419', 'CNY', '1', '127.0.0.1', '2016-03-16 14:41:49', '2016-03-16 16:41:49', 'WXG', 'JSAPI', '1000002', 'wx20160316144151e1291730900213277155'), ('28', '1', 'n/a', '2016031614455182240', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161445518228', 'CNY', '1', '127.0.0.1', '2016-03-16 14:45:51', '2016-03-16 16:45:51', 'WXG', 'JSAPI', '1000003', 'wx20160316144552a79a5f4cf20313658099'), ('29', '1', 'n/a', '2016031615474330935', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161547433094', 'CNY', '1', '127.0.0.1', '2016-03-16 15:47:43', '2016-03-16 17:47:43', 'WXG', 'JSAPI', '1000002', 'wx20160316154745356e1476410493923297'), ('30', '1', 'n/a', '2016031615503200194', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161550320013', 'CNY', '1', '127.0.0.1', '2016-03-16 15:50:32', '2016-03-16 17:50:32', 'WXG', 'JSAPI', '1000002', 'wx20160316155032f757130ee20564335359'), ('31', '1', 'n/a', '2016031615531256537', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161553125653', 'CNY', '1', '127.0.0.1', '2016-03-16 15:53:12', '2016-03-16 17:53:12', 'WXG', 'JSAPI', '1000002', 'wx201603161553134ba67294230622539260'), ('32', '1', 'n/a', '2016031615562887141', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161556288715', 'CNY', '1', '127.0.0.1', '2016-03-16 15:56:28', '2016-03-16 17:56:28', 'WXG', 'JSAPI', '1000002', 'wx20160316155630ea3136882f0287707074'), ('33', '1', 'n/a', '2016031615584814198', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161558481415', 'CNY', '1', '127.0.0.1', '2016-03-16 15:58:48', '2016-03-16 17:58:48', 'WXG', 'JSAPI', '1000002', 'wx20160316155850afe8562d0e0755727665'), ('34', '1', 'n/a', '2016031616001857118', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161600185716', 'CNY', '1', '127.0.0.1', '2016-03-16 16:00:18', '2016-03-16 18:00:18', 'WXG', 'JSAPI', '1000002', 'wx20160316160019b29301c2e10918215015'), ('35', '1', 'n/a', '2016031616014077929', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161601407798', 'CNY', '1', '127.0.0.1', '2016-03-16 16:01:40', '2016-03-16 18:01:40', 'WXG', 'JSAPI', '1000002', 'wx201603161601412a8c6691ad0921480387'), ('36', '1', 'n/a', '2016031616032012991', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603161603201292', 'CNY', '1', '127.0.0.1', '2016-03-16 16:03:20', '2016-03-16 18:03:20', 'WXG', 'JSAPI', '1000002', 'wx201603161603213d286301d70008109721'), ('37', '1', 'n/a', '2016031621551795443', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603162155179544', 'CNY', '1', '127.0.0.1', '2016-03-16 21:55:17', '2016-03-16 23:55:17', 'WXG', 'JSAPI', '1000002', 'wx201603162155191921813a9f0034023898'), ('38', '1', 'n/a', '201603162206288968', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603162206288961', 'CNY', '1', '127.0.0.1', '2016-03-16 22:06:28', '2016-03-17 00:06:28', 'WXG', 'JSAPI', '1000003', 'wx201603162206300c8d39bc020939244743'), ('39', '1', 'n/a', '2016031622102740898', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603162210274085', 'CNY', '1', '127.0.0.1', '2016-03-16 22:10:27', '2016-03-17 00:10:27', 'WXG', 'JSAPI', '1000003', 'wx201603162210281eebdea7360769223111'), ('40', '1', 'n/a', '2016031623201458365', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603162320145834', 'CNY', '1', '127.0.0.1', '2016-03-16 23:20:14', '2016-03-17 01:20:14', 'WXG', 'JSAPI', '1000002', 'wx201603162320151331a383080596552797'), ('41', '1', 'n/a', '2016031722544924871', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603172254492486', 'CNY', '1', '127.0.0.1', '2016-03-17 22:54:49', '2016-03-18 00:54:49', 'WXG', 'JSAPI', '1000002', 'wx20160317225450b3a03870610132164623'), ('42', '1', 'n/a', '2016031822483664143', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603182248366416', 'CNY', '1', '127.0.0.1', '2016-03-18 22:48:36', '2016-03-19 00:48:36', 'WXG', 'JSAPI', '2000004', 'wx20160318224838463b67c7590107822865'), ('43', '1', 'n/a', '2016031921221005892', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603192122100589', 'CNY', '1', '127.0.0.1', '2016-03-19 21:22:10', '2016-03-19 23:22:10', 'WXG', 'JSAPI', '1000002', 'wx20160319212211b7863556e40961375418'), ('44', '1', 'n/a', '2016032021312893966', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603202131289401', 'CNY', '1', '127.0.0.1', '2016-03-20 21:31:28', '2016-03-20 23:31:28', 'WXG', 'JSAPI', '1000001', 'wx2016032021313212edd98fc90174727410'), ('45', '1', 'n/a', '2016032212080051272', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603221208005135', 'CNY', '9999900', '127.0.0.1', '2016-03-22 12:08:00', '2016-03-22 14:08:00', 'WXG', 'JSAPI', '1000001', 'wx201603221208042086eb55420227644790'), ('46', '1', 'n/a', '2016032212151587445', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603221215158743', 'CNY', '200', '127.0.0.1', '2016-03-22 12:15:15', '2016-03-22 14:15:15', 'WXG', 'JSAPI', '1000002', 'wx201603221215165b7314dfa30545203288'), ('47', '1', 'n/a', '2016032213532043066', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603221353204309', 'CNY', '1', '127.0.0.1', '2016-03-22 13:53:20', '2016-03-22 15:53:20', 'WXG', 'JSAPI', '1000002', 'wx2016032213532329661e486b0514023188'), ('48', '1', 'n/a', '2016032218494360261', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603221849436039', 'CNY', '1', '127.0.0.1', '2016-03-22 18:49:43', '2016-03-22 20:49:43', 'WXG', 'JSAPI', '2000004', 'wx201603221849482a9b10f3b80716605018'), ('49', '1', 'n/a', '2016032218531373670', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603221853137369', 'CNY', '1', '127.0.0.1', '2016-03-22 18:53:13', '2016-03-22 20:53:13', 'WXG', 'JSAPI', '1000001', 'wx201603221853140dcf2858cf0805520689'), ('50', '1', 'n/a', '2016032219100803287', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603221910080323', 'CNY', '1', '127.0.0.1', '2016-03-22 19:10:08', '2016-03-22 21:10:08', 'WXG', 'JSAPI', '1000001', 'wx20160322191008805676bf7a0316059409'), ('51', '1', 'n/a', '2016032222272652192', '我是支付内容', '我是详情部分', '我是拖油瓶', '1295518101201603222227265214', 'CNY', '1', '127.0.0.1', '2016-03-22 22:27:26', '2016-03-23 00:27:26', 'WXG', 'JSAPI', '2000004', 'wx20160322222731feca0465320576739277'), ('52', '1', 'n/a', '2016032223185955750', '【带摸】活动众筹，手感之旅01', '奉献1块钱', '共 1 份', '1295518101201603222318595572', 'CNY', '1', '127.0.0.1', '2016-03-22 23:18:59', '2016-03-23 01:18:59', 'WXG', 'JSAPI', '1000002', 'wx201603222319008911858df20099335683'), ('53', '1', 'n/a', '2016032310411196193', '【带摸】活动众筹，手感之旅01', '奉献1块钱', '共 1 份', '1295518101201603231041119612', 'CNY', '1', '127.0.0.1', '2016-03-23 10:41:11', '2016-03-23 12:41:11', 'WXG', 'JSAPI', '1000002', 'wx20160323104116f8fa00d6490453168616'), ('54', '1', 'n/a', '2016032312275074587', '【带摸】活动众筹，手感之旅01', '奉献1块钱', '共 1 份', '1295518101201603231227507456', 'CNY', '1', '127.0.0.1', '2016-03-23 12:27:50', '2016-03-23 14:27:50', 'WXG', 'JSAPI', '1000002', 'wx20160323122755213ca769f90722897483'), ('55', '1', 'n/a', '2016032321420593557', '【带摸】活动众筹，手感之旅01', '自由奉献', '', '1295518101201603232142059353', 'CNY', '1', '127.0.0.1', '2016-03-23 21:42:05', '2016-03-23 23:42:05', 'WXG', 'JSAPI', '1000001', 'wx201603232142094227ff312e0329129957');
COMMIT;

-- ----------------------------
--  Table structure for `za_picture`
-- ----------------------------
DROP TABLE IF EXISTS `za_picture`;
CREATE TABLE `za_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picTitle` varchar(32) DEFAULT NULL,
  `picUrl` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_picture`
-- ----------------------------
BEGIN;
INSERT INTO `za_picture` VALUES ('1', 'test1', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('2', 'test2', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('3', 'test1', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('4', 'test2', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('5', 'test1', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('6', 'test2', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('7', 'test1', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg'), ('8', 'test2', 'http://mt1.baidu.com/timg?wh_rate=0&wapiknow&quality=100&size=w250&sec=0&di=329ddaec49045f63c92d3d84ef560d47&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D800%252C450%2Fsign%3D4f0c1792a0efce1bea7ec0c29f61dfea%2Ff31fbe096b63f624b4836d678044ebf81b4ca3fc.jpg');
COMMIT;

-- ----------------------------
--  Table structure for `za_project_workers`
-- ----------------------------
DROP TABLE IF EXISTS `za_project_workers`;
CREATE TABLE `za_project_workers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `payment` decimal(11,3) NOT NULL,
  `workDetailDesc` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_or24yb7eem81p5q0ggffea8o4` (`roleId`),
  KEY `FK_ns9if5dx825cd1pypci3sodhb` (`projectId`),
  KEY `FK_80svxi1ctf4bjde9iyav88j19` (`userId`),
  CONSTRAINT `FK_80svxi1ctf4bjde9iyav88j19` FOREIGN KEY (`userId`) REFERENCES `za_users` (`id`),
  CONSTRAINT `FK_ns9if5dx825cd1pypci3sodhb` FOREIGN KEY (`projectId`) REFERENCES `za_projects` (`id`),
  CONSTRAINT `FK_or24yb7eem81p5q0ggffea8o4` FOREIGN KEY (`roleId`) REFERENCES `za_roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_project_workers`
-- ----------------------------
BEGIN;
INSERT INTO `za_project_workers` VALUES ('1', '2', '2', '4', '123.000', 'abbkds'), ('2', '2', '3', '5', '8798.000', '看见了；');
COMMIT;

-- ----------------------------
--  Table structure for `za_projects`
-- ----------------------------
DROP TABLE IF EXISTS `za_projects`;
CREATE TABLE `za_projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectName` text NOT NULL,
  `projectRequirement` text NOT NULL,
  `clientCompany` text NOT NULL,
  `clientContact` text NOT NULL,
  `accountManager` int(11) NOT NULL,
  `contractAmount` decimal(11,3) NOT NULL,
  `isSigned` bit(1) NOT NULL,
  `signedDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a7e28xqb8ffj05d1dogmw1okw` (`accountManager`),
  CONSTRAINT `FK_a7e28xqb8ffj05d1dogmw1okw` FOREIGN KEY (`accountManager`) REFERENCES `za_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_projects`
-- ----------------------------
BEGIN;
INSERT INTO `za_projects` VALUES ('2', '项目名称', '项目需求BlaBlaBlaBlaBlaBlaBlaBlaBlaBla', '公司名称', '联系人', '3', '10000000.000', b'1', '2016-01-21'), ('3', '项目名称2', 'blablablabla', '公司名称2', '联系人2', '3', '238409.000', b'1', '2016-01-18');
COMMIT;

-- ----------------------------
--  Table structure for `za_reim`
-- ----------------------------
DROP TABLE IF EXISTS `za_reim`;
CREATE TABLE `za_reim` (
  `id` int(11) NOT NULL,
  `chargeToProjectId` int(11) DEFAULT NULL,
  `usageDesc` text NOT NULL,
  `amount` decimal(11,3) NOT NULL,
  `happenDate` date NOT NULL,
  `isPaied` bit(1) NOT NULL,
  `paiedDate` date DEFAULT NULL,
  `paiedMethodId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b8w2cwtijkqipvcgsik3j9lwn` (`chargeToProjectId`),
  KEY `FK_h9thynd3lfcddbsc5qlkk2xy` (`paiedMethodId`),
  CONSTRAINT `FK_b8w2cwtijkqipvcgsik3j9lwn` FOREIGN KEY (`chargeToProjectId`) REFERENCES `za_projects` (`id`),
  CONSTRAINT `FK_h9thynd3lfcddbsc5qlkk2xy` FOREIGN KEY (`paiedMethodId`) REFERENCES `za_pay_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `za_roles`
-- ----------------------------
DROP TABLE IF EXISTS `za_roles`;
CREATE TABLE `za_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` text NOT NULL,
  `roleDesc` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_roles`
-- ----------------------------
BEGIN;
INSERT INTO `za_roles` VALUES ('1', 'UI设计师', 'UI设计师'), ('2', '前端工程师', '前端工程师'), ('3', 'JAVA后台', 'JAVA后台'), ('4', 'PHP后台', 'PHP后台'), ('5', '.Net后台', '.Net后台'), ('6', 'Android', 'Android开发'), ('7', 'iOS', 'iOS开发'), ('8', 'Android+iOS', '同时开发两个app'), ('9', '产品经理', '设计产品画UE'), ('10', '项目经理', '与客户沟通确认需求跟进进度');
COMMIT;

-- ----------------------------
--  Table structure for `za_users`
-- ----------------------------
DROP TABLE IF EXISTS `za_users`;
CREATE TABLE `za_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(128) NOT NULL,
  `isDeveloper` bit(1) NOT NULL,
  `isAccountManager` bit(1) NOT NULL,
  `isZAOwner` bit(1) NOT NULL,
  `realname` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_users`
-- ----------------------------
BEGIN;
INSERT INTO `za_users` VALUES ('2', 'yuhangshen', 'b0f7ea13237c37d77a52f4da5668ae9d', b'0', b'0', b'1', '沈宇航'), ('3', 'test', 'test', b'0', b'0', b'1', 'abc');
COMMIT;

-- ----------------------------
--  Table structure for `za_wx_pay_callback`
-- ----------------------------
DROP TABLE IF EXISTS `za_wx_pay_callback`;
CREATE TABLE `za_wx_pay_callback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_subscribe` bit(1) DEFAULT NULL,
  `appid` varchar(64) DEFAULT NULL,
  `fee_type` varchar(8) DEFAULT NULL,
  `nonce_str` varchar(64) DEFAULT NULL,
  `out_trade_no` varchar(64) DEFAULT NULL,
  `device_info` varchar(64) DEFAULT NULL,
  `transaction_id` varchar(64) DEFAULT NULL,
  `trade_type` varchar(16) DEFAULT NULL,
  `result_code` varchar(16) DEFAULT NULL,
  `mch_id` varchar(32) DEFAULT NULL,
  `total_fee` int(11) DEFAULT NULL,
  `attach` text,
  `time_end` datetime DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  `return_code` varchar(16) DEFAULT NULL,
  `bank_type` varchar(16) DEFAULT NULL,
  `cash_fee` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `za_wx_pay_callback`
-- ----------------------------
BEGIN;
INSERT INTO `za_wx_pay_callback` VALUES ('1', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031614455182240', '1295518101201603161445518228', 'n/a', '1000610514201603164029508168', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 14:45:58', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('2', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031614414944161', '1295518101201603161441494419', 'n/a', '1000610514201603164027942415', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 14:41:56', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('3', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031613115801176', '1295518101201603161311580119', 'n/a', '1000610514201603164028443316', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 13:12:04', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('4', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031612464227532', '1295518101201603161246422752', 'n/a', '1000610514201603164026103782', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 12:46:47', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('5', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031615503200194', '1295518101201603161550320013', 'n/a', '1000610514201603164030680754', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 15:50:37', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('6', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031615474330935', '1295518101201603161547433094', 'n/a', '1000610514201603164030646501', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 15:47:50', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('7', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031615531256537', '1295518101201603161553125653', 'n/a', '1000610514201603164030712013', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 15:53:17', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('8', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031612513882622', '1295518101201603161251388274', 'n/a', '1000610514201603164026639479', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 12:51:45', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('9', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031615562887141', '1295518101201603161556288715', 'n/a', '1000610514201603164030271496', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 15:56:36', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('10', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031615584814198', '1295518101201603161558481415', 'n/a', '1000610514201603164030767712', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 15:58:54', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('11', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031616001857118', '1295518101201603161600185716', 'n/a', '1000610514201603164032308989', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 16:00:24', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('12', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031616014077929', '1295518101201603161601407798', 'n/a', '1000610514201603164032326517', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 16:01:46', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('13', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031616032012991', '1295518101201603161603201292', 'n/a', '1000610514201603164030822520', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 16:03:25', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('14', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031621551795443', '1295518101201603162155179544', 'n/a', '1000610514201603164039489701', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 21:55:26', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('15', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '201603162206288968', '1295518101201603162206288961', 'n/a', '1000610514201603164039624084', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 22:06:36', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('16', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031622102740898', '1295518101201603162210274085', 'n/a', '1000610514201603164039678671', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 22:10:35', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('17', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031623201458365', '1295518101201603162320145834', 'n/a', '1000610514201603164042047838', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-16 23:20:20', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('18', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031722544924871', '1295518101201603172254492486', 'n/a', '1000610514201603174066802814', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-17 22:54:56', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('19', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031822483664143', '1295518101201603182248366416', 'n/a', '4000612001201603184091714930', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-18 22:48:45', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('20', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016031921221005892', '1295518101201603192122100589', 'n/a', '1000610514201603194117517289', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-19 21:22:21', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('21', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032021312893966', '1295518101201603202131289401', 'n/a', '1000610514201603204144813113', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-20 21:31:38', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('22', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032218494360261', '1295518101201603221849436039', 'n/a', '4000612001201603224192706960', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-22 18:49:53', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('23', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032218531373670', '1295518101201603221853137369', 'n/a', '4000612001201603224191076697', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-22 18:53:18', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('24', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032219100803287', '1295518101201603221910080323', 'n/a', '4000612001201603224193023121', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-22 19:10:13', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('25', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032222272652192', '1295518101201603222227265214', 'n/a', '4000612001201603224196229746', 'JSAPI', 'SUCCESS', '1295518101', '1', '我是拖油瓶', '2016-03-22 22:27:37', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('26', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032223185955750', '1295518101201603222318595572', 'n/a', '4000612001201603224200403756', 'JSAPI', 'SUCCESS', '1295518101', '1', '共 1 份', '2016-03-22 23:19:07', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('27', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032310411196193', '1295518101201603231041119612', 'n/a', '4000612001201603234204005468', 'JSAPI', 'SUCCESS', '1295518101', '1', '共 1 份', '2016-03-23 10:41:24', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('28', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032312275074587', '1295518101201603231227507456', 'n/a', '4000612001201603234209137147', 'JSAPI', 'SUCCESS', '1295518101', '1', '共 1 份', '2016-03-23 12:28:11', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1'), ('29', b'1', 'wx9b3d1cb48fb48ff4', 'CNY', '2016032321420593557', '1295518101201603232142059353', 'n/a', '4000612001201603234224667256', 'JSAPI', 'SUCCESS', '1295518101', '1', null, '2016-03-23 21:42:15', 'oecAVt4hSa4941GwbIWkLDhrTTwA', 'SUCCESS', 'ICBC_CREDIT', '1');
COMMIT;

