package com.river.malladmin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.BasePageQuery;
import com.river.malladmin.common.contant.SystemConstants;
import com.river.malladmin.common.exception.BusinessException;
import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.security.utils.SecurityUtils;
import com.river.malladmin.system.mapper.RoleMapper;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.form.RoleForm;
import com.river.malladmin.system.model.query.RolePageQuery;
import com.river.malladmin.system.model.vo.RoleDetailsVO;
import com.river.malladmin.system.model.vo.RolePageVO;
import com.river.malladmin.system.service.MenuService;
import com.river.malladmin.system.service.RoleMenuService;
import com.river.malladmin.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiang
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;

    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery query) {
        Page<Role> page = BasePageQuery.page(query);
        Page<Role> rolePage = this.lambdaQuery()
                .like(StringUtils.isNotBlank(query.getKeywords()), Role::getName, query.getKeywords())
                .orderByDesc(Role::getSort)
                .ne(!SecurityUtils.isRoot(), Role::getCode, SystemConstants.ROOT_ROLE_CODE)
                .page(page);
        List<RolePageVO> rolePages = rolePage.getRecords().stream()
                .map(role -> RolePageVO.from(role, new RolePageVO())).collect(Collectors.toList());
        return new Page<RolePageVO>(rolePage.getCurrent(), rolePage.getSize(), rolePage.getTotal()).setRecords(rolePages);
    }

    @Override
    public RoleDetailsVO getRoleById(Long id) {
        Role role = this.getById(id);
        if (Objects.isNull(role)) throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        RoleDetailsVO details = RoleDetailsVO.from(role, new RoleDetailsVO());
        // 获取用户权限列表
        assert details != null;
        Set<String> perms = roleMenuService.getPermsByRoleIds(CollUtil.newHashSet(id));
        details.setPermissions(perms);
        return details;
    }

    @Override
    public Long saveRole(RoleForm roleForm) {
        Role entity = roleForm.toEntity(roleForm);
        boolean result = this.save(entity);
        if (result) {
            roleMenuService.refreshRolePermsCache(entity.getCode());
        }
        return entity.getId();
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

    @Override
    public boolean updateRole(Long id, RoleForm roleForm) {
        Role role = this.getById(id);
        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        }
        // 角色名称 / code 不能重复
        validateRoleRepeated(roleForm);
        String oldCode = role.getCode();
        Integer oldStatus = role.getStatus();
        BeanUtils.copyProperties(roleForm, role);
        boolean result = this.updateById(role);
        if (result) {
            // 判断角色编码或状态是否修改，修改了则刷新权限缓存
            String newCode = roleForm.getCode();
            if (!StrUtil.equals(oldCode, newCode) ||
                    !ObjectUtil.equals(oldStatus, roleForm.getStatus()
                    )) {
                roleMenuService.refreshRolePermsCache(oldCode, newCode);
            }
        }
        return result;
    }

    private void validateRoleRepeated(RoleForm roleForm) {
        Long roleFormId = roleForm.getId();
        String code = roleForm.getCode();
        String name = roleForm.getName();
        Optional<Role> roleOptional = this.lambdaQuery()
                .ne(Role::getId, roleFormId)
                .and(wrapper -> wrapper.eq(Role::getCode, code).or().eq(Role::getName, name))
                .oneOpt();
        roleOptional.ifPresent(role -> {
            throw new BusinessException(ResultCode.ROLE_REPEATED);
        });
    }

    @Override
    public List<Long> getRoleMenuIds(Long id) {
        Role role = this.getById(id);
        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        }
        return roleMenuService.listMenuIdsByRoleId(id);
    }

    @Override
    public boolean assignMenusToRole(Long id, List<Long> menuIds) {
        Role role = this.getById(id);
        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        }
        return roleMenuService.assignMenusToRole(id, menuIds);
    }
}