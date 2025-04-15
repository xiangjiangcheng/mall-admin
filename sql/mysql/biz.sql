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