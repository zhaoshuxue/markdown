/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : markdown

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-03-26 01:09:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_note
-- ----------------------------
DROP TABLE IF EXISTS `m_note`;
CREATE TABLE `m_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int(11) DEFAULT NULL COMMENT '父ID',
  `user_id` int(11) NOT NULL COMMENT '账户ID',
  `types` int(5) NOT NULL COMMENT '类型，1-目录，2-文章',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `summary` varchar(512) DEFAULT '' COMMENT '摘要',
  `content` varchar(512) DEFAULT '' COMMENT '内容',
  `orders` int(8) NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '标识，0-未删除，1-删除',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(50) DEFAULT NULL COMMENT '修改人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='笔记表';
