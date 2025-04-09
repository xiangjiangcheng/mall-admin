package com.river.malladmin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.entity.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @author xiang
 * @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service
 * @createDate 2025-03-25 22:31:25
 */
public interface UserRoleService extends IService<UserRole> {

    List<Role> getRolesByUserId(Long userId);

    Set<String> getRoleCodesByUserId(Long userId);

    void saveUserRoles(Long userId, List<Long> roleIds);
}
