package com.river.malladmin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.BasePageQuery;
import com.river.malladmin.common.contant.SystemConstants;
import com.river.malladmin.common.exception.BusinessException;
import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.security.utils.SecurityUtils;
import com.river.malladmin.system.mapper.UserMapper;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.entity.User;
import com.river.malladmin.system.model.form.UserForm;
import com.river.malladmin.system.model.query.UserPageQuery;
import com.river.malladmin.system.model.vo.UserDetailsVO;
import com.river.malladmin.system.model.vo.UserPageVO;
import com.river.malladmin.system.service.RoleMenuService;
import com.river.malladmin.system.service.UserRoleService;
import com.river.malladmin.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiang
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2025-03-20 15:23:50
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final RoleMenuService roleMenuService;

    @Override
    public Page<UserPageVO> getUserPage(UserPageQuery query) {
        Page<UserPageVO> page = BasePageQuery.page(query);
        return this.baseMapper.getUserPage(page, query);
    }

    @Override
    public UserDetailsVO getUserById(Long id) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserDetailsVO vo = new UserDetailsVO();
        BeanUtils.copyProperties(user, vo);
        List<Role> roles = userRoleService.getRolesByUserId(id);
        Set<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        vo.setRoles(roles.stream().map(Role::getCode).collect(Collectors.toSet()));
        vo.setRoleNames(CollUtil.join(roles.stream().map(Role::getName).collect(Collectors.toSet()), ","));
        Set<String> permissions = roleMenuService.getPermsByRoleIds(roleIds);
        vo.setPermissions(permissions);
        return vo;
    }

    @Override
    public Long saveUser(UserForm userForm) {
        String username = userForm.getUsername();

        long count = this.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        Assert.isTrue(count == 0, "用户名已存在");

        // 实体转换 form->entity
        User entity = userForm.toEntity(userForm);

        // 设置默认加密密码
        String defaultEncryptPwd = passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD);
        entity.setPassword(defaultEncryptPwd);

        // 新增用户
        boolean result = this.save(entity);

        if (result) {
            // TODO: 2025/3/24 保存用户角色
            // userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return entity.getId();
    }

    @Override
    public void deleteUserById(Long id) {
        this.lambdaUpdate().set(User::getIsDeleted, true).eq(User::getId, id).update();
    }

    @Override
    public UserDetailsVO me() {
        Long userId = SecurityUtils.getUserId();
        if (Objects.isNull(userId)) return null;
        return this.getUserById(userId);
    }

    @Override
    @Transactional
    public boolean updateUser(Long id, UserForm userForm) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(userForm, user);
        return this.updateById(user);
    }
}




