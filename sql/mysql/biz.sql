-- 店铺表
DROP TABLE IF EXISTS `biz_shop`;
CREATE TABLE `biz_shop`
(
    `id`               BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`          BIGINT       NOT NULL COMMENT '关联商家用户ID',
    `name`             VARCHAR(100) NOT NULL,
    `logo`             VARCHAR(255),
    `description`      TEXT,
    `address`          VARCHAR(255),
    `phone`            VARCHAR(20),
    `business_hours`   VARCHAR(100) COMMENT '营业时间，如"09:00-21:00"',
    `status`           TINYINT        DEFAULT 1 COMMENT '1-营业中 2-休息中',
    `delivery_fee`     DECIMAL(10, 2) DEFAULT 0 COMMENT '配送费',
    `min_order_amount` DECIMAL(10, 2) DEFAULT 0 COMMENT '起送金额',
    `create_by` bigint NULL COMMENT '创建人 ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint NULL COMMENT '更新人ID',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)'
);

-- 菜品分类表
DROP TABLE IF EXISTS `biz_category`;
CREATE TABLE `biz_category`
(
    `id`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `shop_id` BIGINT      NOT NULL,
    `name`    VARCHAR(50) NOT NULL,
    `sort`    INT     DEFAULT 0,
    `status`  TINYINT DEFAULT 1 COMMENT '1-显示 0-隐藏',
    `create_by` bigint NULL COMMENT '创建人 ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint NULL COMMENT '更新人ID',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)'
);

-- 菜品表
DROP TABLE IF EXISTS `biz_product`;
CREATE TABLE `biz_product`
(
    `id`             BIGINT PRIMARY KEY AUTO_INCREMENT,
    `shop_id`        BIGINT         NOT NULL,
    `category_id`    BIGINT         NOT NULL,
    `name`           VARCHAR(100)   NOT NULL,
    `description`    TEXT,
    `image`          VARCHAR(255),
    `price`          DECIMAL(10, 2) NOT NULL COMMENT '现价',
    `original_price` DECIMAL(10, 2) COMMENT '原价',
    `stock`          INT      DEFAULT -1 COMMENT '-1表示无限库存',
    `status`         TINYINT  DEFAULT 1 COMMENT '1-上架 0-下架',
    `sales`          INT      DEFAULT 0 COMMENT '销量',
    `sort`           INT      DEFAULT 0,
    `create_by` bigint NULL COMMENT '创建人 ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint NULL COMMENT '更新人ID',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)'
);

-- 订单表
Drop table if exists `biz_order`;
CREATE TABLE `biz_order`
(
    `id`            BIGINT PRIMARY KEY AUTO_INCREMENT,
    `order_no`      VARCHAR(32) UNIQUE NOT NULL COMMENT '订单号',
    `user_id`       BIGINT             NOT NULL COMMENT '用户ID',
    `shop_id`       BIGINT             NOT NULL,
    `total_amount`  DECIMAL(10, 2)     NOT NULL COMMENT '订单总金额',
    `actual_amount` DECIMAL(10, 2)     NOT NULL COMMENT '实际支付金额',
    `delivery_fee`  DECIMAL(10, 2) DEFAULT 0 COMMENT '配送费',
    `remark`        VARCHAR(255),
    `status`        TINYINT        DEFAULT 0 COMMENT '0-待支付 1-已支付/待接单 2-已接单/制作中 3-配送中 4-已完成 5-已取消',
    `pay_time`      DATETIME       DEFAULT NULL COMMENT '支付时间',
    `create_by` bigint NULL COMMENT '创建人 ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint NULL COMMENT '更新人ID',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)'
);


-- 数据字典
-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
                            `dict_code` varchar(50) COMMENT '类型编码',
                            `name` varchar(50) COMMENT '类型名称',
                            `status` tinyint(1) DEFAULT '0' COMMENT '状态(0:正常;1:禁用)',
                            `remark` varchar(255) COMMENT '备注',
                            `create_time` datetime COMMENT '创建时间',
                            `create_by` bigint COMMENT '创建人ID',
                            `update_time` datetime COMMENT '更新时间',
                            `update_by` bigint COMMENT '修改人ID',
                            `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除(1-删除，0-未删除)',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_dict_code` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';
-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '性别', 1, NULL, now() , 1,now(), 1,0);
INSERT INTO `sys_dict` VALUES (2, 'notice_type', '通知类型', 1, NULL, now(), 1,now(), 1,0);
INSERT INTO `sys_dict` VALUES (3, 'notice_level', '通知级别', 1, NULL, now(), 1,now(), 1,0);


-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `dict_code` varchar(50) COMMENT '关联字典编码，与sys_dict表中的dict_code对应',
                                 `value` varchar(50) COMMENT '字典项值',
                                 `label` varchar(100) COMMENT '字典项标签',
                                 `tag_type` varchar(50) COMMENT '标签类型，用于前端样式展示（如success、warning等）',
                                 `status` tinyint DEFAULT '0' COMMENT '状态（1-正常，0-禁用）',
                                 `sort` int DEFAULT '0' COMMENT '排序',
                                 `remark` varchar(255) COMMENT '备注',
                                 `create_time` datetime COMMENT '创建时间',
                                 `create_by` bigint COMMENT '创建人ID',
                                 `update_time` datetime COMMENT '更新时间',
                                 `update_by` bigint COMMENT '修改人ID',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 'gender', '1', '男', 'primary', 1, 1, NULL, now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (2, 'gender', '2', '女', 'danger', 1, 2, NULL, now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (3, 'gender', '0', '保密', 'info', 1, 3, NULL, now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (4, 'notice_type', '1', '系统升级', 'success', 1, 1, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (5, 'notice_type', '2', '系统维护', 'primary', 1, 2, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (6, 'notice_type', '3', '安全警告', 'danger', 1, 3, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (7, 'notice_type', '4', '假期通知', 'success', 1, 4, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (8, 'notice_type', '5', '公司新闻', 'primary', 1, 5, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (9, 'notice_type', '99', '其他', 'info', 1, 99, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (10, 'notice_level', 'L', '低', 'info', 1, 1, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (11, 'notice_level', 'M', '中', 'warning', 1, 2, '', now(), 1,now(),1);
INSERT INTO `sys_dict_data` VALUES (12, 'notice_level', 'H', '高', 'danger', 1, 3, '', now(), 1,now(),1);