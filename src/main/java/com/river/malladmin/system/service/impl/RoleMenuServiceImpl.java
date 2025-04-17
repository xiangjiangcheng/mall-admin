package com.river.malladmin.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.contant.RedisConstants;
import com.river.malladmin.common.enums.MenuTypeEnum;
import com.river.malladmin.system.mapper.RoleMenuMapper;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.model.entity.RoleMenu;
import com.river.malladmin.system.model.vo.RolePermVO;
import com.river.malladmin.system.service.RoleMenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        // 初始化角色权限关系映射到redis中
        refreshRolePermsCache();
    }

    public void refreshRolePermsCache() {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, "*");
        // 从数据库中查询所有角色权限关系
        this.baseMapper.selectRolePerms(null).forEach(roleMenu -> {
            // 将角色权限关系存储到redis中
            redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleMenu.getRoleCode(), roleMenu.getPerms());
        });
    }

    @Override
    public List<Menu> getMenusByRoleIds(Set<Long> roleIds) {
        if (roleIds.isEmpty()) return Collections.emptyList();
        return this.baseMapper.getMenusByRoleIds(roleIds);
    }

    @Override
    public Set<String> getPermsByRoleIds(Set<Long> roleIds) {
        List<Menu> menus = this.getMenusByRoleIds(roleIds);
        return menus.stream().filter(m -> MenuTypeEnum.BUTTON == m.getType()).map(Menu::getPerm).collect(Collectors.toSet());
    }

    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        return this.baseMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    @Transactional
    public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
        // 删除角色菜单关系
        this.lambdaUpdate().eq(RoleMenu::getRoleId, roleId).remove();

        // 批量插入新的角色菜单关系
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(roleId, menuId))
                .collect(Collectors.toList());
        this.saveBatch(roleMenus);

        // 刷新角色权限关系映射到redis中
        refreshRolePermsCache();
        return true;
    }

    @Override
    public void refreshRolePermsCache(String roleCode) {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, roleCode);
        List<RolePermVO> rolePerms = this.baseMapper.selectRolePerms(roleCode);
        if (CollectionUtil.isNotEmpty(rolePerms)) {
            RolePermVO rolePerm = rolePerms.get(0);
            Set<String> perms = rolePerm.getPerms();
            if (CollectionUtil.isEmpty(perms)) return;
            redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleCode, perms);
        }
    }

    @Override
    public void refreshRolePermsCache(String oldRoleCode, String newRoleCode) {
        // 清理旧角色权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, oldRoleCode);

        // 添加新角色权限缓存
        List<RolePermVO> list = this.baseMapper.selectRolePerms(newRoleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermVO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, newRoleCode, perms);
        }
    }
}




