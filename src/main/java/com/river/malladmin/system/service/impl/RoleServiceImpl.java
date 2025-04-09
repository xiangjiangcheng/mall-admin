package com.river.malladmin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.BasePageQuery;
import com.river.malladmin.common.exception.BusinessException;
import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.security.service.PermissionService;
import com.river.malladmin.system.mapper.RoleMapper;
import com.river.malladmin.system.model.entity.Role;
import com.river.malladmin.system.model.form.RoleForm;
import com.river.malladmin.system.model.query.RolePageQuery;
import com.river.malladmin.system.model.vo.RoleDetailsVO;
import com.river.malladmin.system.model.vo.RolePageVO;
import com.river.malladmin.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiang
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final PermissionService permissionService;

    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery query) {
        Page<Role> page = BasePageQuery.page(query);
        Page<Role> rolePage = this.lambdaQuery()
                .like(StringUtils.isNotBlank(query.getKeywords()), Role::getName, query.getKeywords())
                .orderByDesc(Role::getId)
                .page(page);
        List<RolePageVO> rolePages = rolePage.getRecords().stream().map(RolePageVO::from).collect(Collectors.toList());
        return new Page<RolePageVO>(rolePage.getCurrent(), rolePage.getSize(), rolePage.getTotal()).setRecords(rolePages);
    }

    @Override
    public RoleDetailsVO getRoleById(Long id) {
        Role role = this.getById(id);
        if (Objects.isNull(role)) throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        RoleDetailsVO details = (RoleDetailsVO) RoleDetailsVO.from(role);
        // 获取用户权限列表
        assert details != null;
        Set<String> perms = permissionService.getRolePermsFormCache(CollUtil.newHashSet(details.getCode()));
        details.setPermissions(perms);
        return details;
    }

    @Override
    public Long saveRole(RoleForm roleForm) {
        Role entity = roleForm.toEntity(roleForm);
        boolean result = this.save(entity);
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
}




