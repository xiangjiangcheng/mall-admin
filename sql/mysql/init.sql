-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS river_admin DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 2. 创建表 && 数据初始化
-- ----------------------------
use river_admin;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `username` varchar(64) NULL DEFAULT NULL COMMENT '用户名',
                            `nickname` varchar(64) NULL DEFAULT NULL COMMENT '昵称',
                            `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别(1-男 2-女 0-保密)',
                            `password` varchar(100) NULL DEFAULT NULL COMMENT '密码',
                            `dept_id` int NULL DEFAULT NULL COMMENT '部门ID',
                            `avatar` varchar(255) NULL DEFAULT '' COMMENT '用户头像',
                            `mobile` varchar(20) NULL DEFAULT NULL COMMENT '联系方式',
                            `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
                            `email` varchar(128) NULL DEFAULT NULL COMMENT '用户邮箱',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
                            `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
                            `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18866668888', 1, 'youlaitech@163.com', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 1, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18866668887', 1, '', now(), NULL, now(), NULL, 0);
INSERT INTO `sys_user` VALUES (3, 'websocket', '测试小用户', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 3, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18866668886', 1, 'youlaitech@163.com', now(), NULL, now(), NULL, 0);

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`               bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `module`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '日志模块',
    `request_method`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '请求方式',
    `request_params`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '请求参数(批量请求参数可能会超过text)',
    `response_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL COMMENT '返回参数',
    `content`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志内容',
    `request_uri`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求路径',
    `method`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
    `ip`               varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT 'IP地址',
    `province`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
    `city`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
    `execution_time`   bigint(0)                                                     NULL DEFAULT NULL COMMENT '执行时间(ms)',
    `browser`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
    `browser_version`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器版本',
    `os`               varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终端系统',
    `create_by`        bigint(0)                                                     NULL DEFAULT NULL COMMENT '创建人ID',
    `create_time`      datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `is_deleted`       tinyint(0)                                                    NULL DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统日志表'
  ROW_FORMAT = Dynamic;