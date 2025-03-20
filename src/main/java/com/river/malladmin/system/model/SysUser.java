package com.river.malladmin.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 *
 * @TableName sys_user
 */
@Schema(description = "用户表")
@TableName(value = "sys_user")
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

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

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateBy;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    @Schema(description = "逻辑删除标识(0-未删除 1-已删除)", example = "0")
    private Integer isDeleted;

}