package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.river.malladmin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 用户表
 *
 * @TableName sys_user
 */
@Data
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别(1-男 2-女 0-保密)
     */
    private Integer gender;

    /**
     * 密码 默认123456
     */
    private String password;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 状态(1-正常 0-禁用)
     */
    private Integer status;

    /**
     * 用户邮箱
     */
    private String email;

    @TableField(exist = false)
    private Set<String> roles;

    @TableField(exist = false)
    private Set<String> perms;

}