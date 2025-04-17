-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `table_name`     varchar(100) NOT NULL COMMENT '表名',
    `module_name`    varchar(100) COMMENT '模块名',
    `package_name`   varchar(255) NOT NULL COMMENT '包名',
    `business_name`  varchar(100) NOT NULL COMMENT '业务名',
    `entity_name`    varchar(100) NOT NULL COMMENT '实体类名',
    `author`         varchar(50)  NOT NULL COMMENT '作者',
    `parent_menu_id` bigint COMMENT '上级菜单ID，对应sys_menu的id ',
    `create_by` bigint NULL COMMENT '创建人 ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint NULL COMMENT '更新人ID',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tablename` (`table_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='代码生成基础配置表';

-- ----------------------------
-- Table structure for gen_field_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_field_config`;
CREATE TABLE `gen_field_config`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `config_id`        bigint       NOT NULL COMMENT '关联的配置ID',
    `column_name`      varchar(100),
    `column_type`      varchar(50),
    `column_length`    int,
    `field_name`       varchar(100) NOT NULL COMMENT '字段名称',
    `field_type`       varchar(100) COMMENT '字段类型',
    `field_sort`       int COMMENT '字段排序',
    `field_comment`    varchar(255) COMMENT '字段描述',
    `max_length`       int,
    `is_required`      tinyint(1) COMMENT '是否必填',
    `is_show_in_list`  tinyint(1) DEFAULT '0' COMMENT '是否在列表显示',
    `is_show_in_form`  tinyint(1) DEFAULT '0' COMMENT '是否在表单显示',
    `is_show_in_query` tinyint(1) DEFAULT '0' COMMENT '是否在查询条件显示',
    `query_type`       tinyint COMMENT '查询方式',
    `form_type`        tinyint COMMENT '表单类型',
    `dict_type`        varchar(50) COMMENT '字典类型',
    `create_by` bigint NULL COMMENT '创建人 ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint NULL COMMENT '更新人ID',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
    PRIMARY KEY (`id`),
    KEY                `config_id` (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成字段配置表';