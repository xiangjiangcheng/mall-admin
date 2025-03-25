package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.system.mapper.RoleMapper;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.form.RoleForm;
import com.river.malladmin.system.model.query.RolePageQuery;
import com.river.malladmin.system.model.vo.RoleDetailsVO;
import com.river.malladmin.system.model.vo.RolePageVO;
import com.river.malladmin.system.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author xiang
 * @description 针对表【sys_role(角色表)】的数据库操作Service实现
 * @createDate 2025-03-25 22:31:04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery query) {
        return null;
    }

    @Override
    public RoleDetailsVO getRoleById(Long id) {
        return null;
    }

    @Override
    public Long saveRole(RoleForm userForm) {
        return null;
    }
}




