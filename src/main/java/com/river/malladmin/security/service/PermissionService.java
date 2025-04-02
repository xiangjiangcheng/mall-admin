package com.river.malladmin.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.river.malladmin.common.contant.RedisConstants;
import com.river.malladmin.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义注解权限校验
 *
 * @author JiangCheng Xiang
 */
@Slf4j
@Component("ss")
@RequiredArgsConstructor
public class PermissionService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        boolean root = SecurityUtils.isRoot();
        // 超级管理员拥有所有权限
        if (root) {
            return true;
        }

        // 获取当前登录用户对应的角色
        Set<String> roleCodes = SecurityUtils.getRoles();
        if (CollectionUtil.isEmpty(roleCodes)) {
            return false;
        }

        // 获取当前登录用户的所有角色的权限列表
        Set<String> rolePerms = this.getRolePermsFormCache(roleCodes);
        if (CollectionUtil.isEmpty(rolePerms)) {
            return false;
        }

        // 判断当前登录用户的所有角色的权限列表中是否包含所需权限
        boolean hasPermission = rolePerms.stream()
                .anyMatch(rolePerm ->
                        // 匹配权限，支持通配符(* 等)
                        PatternMatchUtils.simpleMatch(rolePerm, permission)
                );

        if (!hasPermission) {
            log.error("用户无操作权限：{}", permission);
        }
        return hasPermission;
    }

    private Set<String> getRolePermsFormCache(Set<String> roleCodes) {
        // 检查输入是否为空
        if (CollectionUtil.isEmpty(roleCodes)) {
            return Collections.emptySet();
        }

        // 从缓存中获取所有角色对应的权限列表
        List<Object> cachePerms = redisTemplate.opsForHash().multiGet(RedisConstants.System.ROLE_PERMS, new ArrayList<>(roleCodes));
        List<Set<String>> rolePermS = cachePerms.stream()
                .filter(rolePerm -> rolePerm instanceof Set<?>)
                .map(rolePerm -> {
                    @SuppressWarnings("unchecked")
                    Set<String> perms = (Set<String>) rolePerm;
                    return perms;
                }).toList();

        return rolePermS.stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
