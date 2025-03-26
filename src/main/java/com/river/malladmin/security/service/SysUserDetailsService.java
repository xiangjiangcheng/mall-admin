package com.river.malladmin.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.river.malladmin.security.model.SysUserDetails;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.entity.User;
import com.river.malladmin.system.service.RoleMenuService;
import com.river.malladmin.system.service.UserRoleService;
import com.river.malladmin.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户认证信息加载服务类
 * <p>
 * 在用户登录时，Spring Security 会自动调用该类的 {@link #loadUserByUsername(String)} 方法，
 * 获取封装后的用户信息对象 {@link SysUserDetails}，用于后续的身份验证和权限管理。
 *
 * @author youlai
 */
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleMenuService roleMenuService;

    /**
     * 根据用户名加载用户的认证信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户基本信息
        User user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // 获取用户角色信息
        List<Role> roles = userRoleService.getRolesByUserId(user.getId());
        user.setRoles(roles.stream().map(Role::getCode).collect(Collectors.toSet()));

        // 获取用户权限信息
        Set<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        Set<String> perms = roleMenuService.getPermsByRoleIds(roleIds);
        user.setPerms(perms);

        // 将数据库中查询到的用户信息封装成 Spring Security 需要的 UserDetails 对象
        return new SysUserDetails(user);
    }
}
