package com.river.malladmin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.model.entity.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author xiang
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
 * @createDate 2025-03-25 22:31:19
 */
public interface RoleMenuService extends IService<RoleMenu> {

    List<Menu> getMenusByRoleIds(Set<Long> roleIds);

    Set<String> getPermsByRoleIds(Set<Long> roleIds);

    List<Long> listMenuIdsByRoleId(Long roleId);

    boolean assignMenusToRole(Long roleId, List<Long> menuIds);

    void refreshRolePermsCache(String roleCode);

    void refreshRolePermsCache(String oldRoleCode, String roleRoleCode);

}
