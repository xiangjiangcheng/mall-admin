package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.contant.RedisConstants;
import com.river.malladmin.common.enums.MenuTypeEnum;
import com.river.malladmin.system.mapper.RoleMenuMapper;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.model.entity.RoleMenu;
import com.river.malladmin.system.service.MenuService;
import com.river.malladmin.system.service.RoleMenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiang
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
 * @createDate 2025-03-25 22:31:19
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    private final MenuService menuService;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        // 初始化角色权限关系映射到redis中
        refreshRoleMenuMap();
    }

    private void refreshRoleMenuMap() {
        // 从数据库中查询所有角色权限关系
        this.baseMapper.selectRolePerms(null).forEach(roleMenu -> {
            // 将角色权限关系存储到redis中
            redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleMenu.getRoleCode(), roleMenu.getPerms());
        });
    }

    @Override
    public List<Menu> getMenusByRoleIds(Set<Long> roleIds) {
        if (roleIds.isEmpty()) return Collections.emptyList();
        List<RoleMenu> roleMenus = this.lambdaQuery().in(RoleMenu::getRoleId, roleIds).list();
        Set<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
        return menuService.getMenusByIds(menuIds);
    }

    @Override
    public Set<String> getPermsByRoleIds(Set<Long> roleIds) {
        List<Menu> menus = this.getMenusByRoleIds(roleIds);
        return menus.stream().filter(m -> MenuTypeEnum.BUTTON == m.getType()).map(Menu::getPerm).collect(Collectors.toSet());
    }
}




