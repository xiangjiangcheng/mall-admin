package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.river.malladmin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @TableName sys_role
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role")
@Data
public class Role extends BaseEntity {
    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色状态(1-正常 0-停用)
     */
    private Integer status;

    /**
     * 数据权限(0-所有数据 1-部门及子部门数据 2-本部门数据3-本人数据)
     */
    private Integer dataScope;

}