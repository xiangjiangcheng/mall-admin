package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.system.mapper.UserRoleMapper;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.entity.UserRole;
import com.river.malladmin.system.service.RoleService;
import com.river.malladmin.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiang
 * @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
 * @createDate 2025-03-25 22:31:25
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    public final RoleService roleService;

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId);
        List<UserRole> list = this.list(queryWrapper);
        Set<Long> roleIds = list.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        return roleService.getRolesByIds(roleIds);
    }

    @Override
    public Set<String> getRoleCodesByUserId(Long userId) {
        List<Role> roles = this.getRolesByUserId(userId);
        return roles.stream().map(Role::getCode).collect(Collectors.toSet());
    }
}




