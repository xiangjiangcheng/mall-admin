package com.river.malladmin.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.system.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.river.malladmin.system.model.form.RoleForm;
import com.river.malladmin.system.model.query.RolePageQuery;
import com.river.malladmin.system.model.vo.RoleDetailsVO;
import com.river.malladmin.system.model.vo.RolePageVO;

import java.util.List;
import java.util.Set;

/**
 * @author xiang
 * @description 针对表【sys_role(角色表)】的数据库操作Service
 * @createDate 2025-03-25 22:31:04
 */
public interface RoleService extends IService<Role> {

    Page<RolePageVO> getRolePage(RolePageQuery query);

    RoleDetailsVO getRoleById(Long id);

    Long saveRole(RoleForm roleForm);

    List<Role> getRolesByIds(Set<Long> roleIds);

    void deleteRoleById(Long id);
}
