package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.river.malladmin.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 用户表
 *
 * @TableName sys_user
 */

@Data
@Schema(description = "用户表")
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "admin")
    private String nickname;

    /**
     * 性别(1-男 2-女 0-保密)
     */
    @Schema(description = "性别(1-男 2-女 0-保密)", example = "1")
    private Integer gender;

    /**
     * 密码
     */
    @Schema(description = "密码", example = "123456")
    private String password;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID", example = "1")
    private Integer deptId;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String mobile;

    /**
     * 状态(1-正常 0-禁用)
     */
    @Schema(description = "状态(1-正常 0-禁用)", example = "1")
    private Integer status;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    @TableField(exist = false)
    @Schema(description = "用户角色集")
    private Set<String> roles;

    @TableField(exist = false)
    @Schema(description = "用户权限集")
    private Set<String> perms;
}