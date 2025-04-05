package com.river.malladmin.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.river.malladmin.system.model.entity.User;
import com.river.malladmin.system.model.form.UserForm;
import com.river.malladmin.system.model.query.UserPageQuery;
import com.river.malladmin.system.model.vo.UserDetailsVO;
import com.river.malladmin.system.model.vo.UserPageVO;

/**
 * @author xiang
 * @description 针对表【sys_user(用户表)】的数据库操作Service
 * @createDate 2025-03-20 15:23:50
 */
public interface UserService extends IService<User> {

    Page<UserPageVO> getUserPage(UserPageQuery query);

    UserDetailsVO getUserById(Long id);

    Long saveUser(UserForm userForm);

    void deleteUserById(Long id);

    UserDetailsVO me();
}
