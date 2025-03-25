package com.river.malladmin.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.BasePageQuery;
import com.river.malladmin.common.contant.SystemConstants;
import com.river.malladmin.common.exception.BusinessException;
import com.river.malladmin.system.mapper.UserMapper;
import com.river.malladmin.system.model.entity.User;
import com.river.malladmin.system.model.form.UserForm;
import com.river.malladmin.system.model.query.UserPageQuery;
import com.river.malladmin.system.model.vo.UserDetailsVO;
import com.river.malladmin.system.model.vo.UserPageVO;
import com.river.malladmin.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author xiang
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2025-03-20 15:23:50
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;

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
        // TODO: 2025/3/24 获取用户权限
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
}




