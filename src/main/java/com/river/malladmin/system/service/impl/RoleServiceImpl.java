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

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author xiang
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

    @Override
    public List<Role> getRolesByIds(Set<Long> roleIds) {
        if (roleIds.isEmpty()) return Collections.emptyList();
        return this.lambdaQuery().in(Role::getId, roleIds).list();
    }

    @Override
    public void deleteRoleById(Long id) {
        this.lambdaUpdate().eq(Role::getId, id).remove();
    }
}




