package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色和菜单关联表
 *
 * @TableName sys_role_menu
 */
@TableName(value = "sys_role_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}