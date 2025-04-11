package com.river.malladmin.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.common.annotation.Log;
import com.river.malladmin.common.enums.LogModuleEnum;
import com.river.malladmin.common.result.PageResult;
import com.river.malladmin.common.result.Result;
import com.river.malladmin.system.model.form.RoleForm;
import com.river.malladmin.system.model.query.RolePageQuery;
import com.river.malladmin.system.model.vo.RoleDetailsVO;
import com.river.malladmin.system.model.vo.RolePageVO;
import com.river.malladmin.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JiangCheng Xiang
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "03.Role Apis")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "角色分页列表")
    @GetMapping("/page")
    // @PreAuthorize("@ss.hasPermission('sys:role:query')")
    @Log(value = "角色分页列表", module = LogModuleEnum.ROLE)
    public PageResult<RolePageVO> page(@Valid RolePageQuery query) {
        Page<RolePageVO> result = roleService.getRolePage(query);
        return PageResult.success(result);
    }

    /**
     * 获取角色详情
     */
    @Operation(summary = "获取角色详情")
    @Parameters({
            @Parameter(name = "id", description = "角色ID", in = ParameterIn.PATH)
    })
    @GetMapping("/{id}")
    // @PreAuthorize("@ss.hasPermission('sys:role:details')")
    @Log(value = "获取角色详情", module = LogModuleEnum.ROLE)
    public Result<RoleDetailsVO> details(@PathVariable Long id) {
        RoleDetailsVO user = roleService.getRoleById(id);
        return Result.success(user);
    }

    /**
     * 新增角色
     */
    @Operation(summary = "新增角色")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:role:add')")
    @Log(value = "新增角色", module = LogModuleEnum.ROLE)
    public Result<Long> createRole(@Valid @RequestBody RoleForm roleForm) {
        Long userId = roleService.saveRole(roleForm);
        return Result.success(userId);
    }

    /**
     * 更新角色信息
     */
    @Operation(summary = "更新角色信息")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:role:edit')")
    @Log(value = "更新角色信息", module = LogModuleEnum.ROLE)
    public Result<String> updateRole(@PathVariable Long id, @RequestBody RoleForm roleForm) {
        boolean result = roleService.updateRole(id, roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改角色状态")
    @PutMapping("/{id}/status")
    public Result<String> updateRoleStatus(@PathVariable Long id, @RequestBody RoleForm roleForm) {
        boolean result = roleService.updateRole(id, roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "获取角色的权限ID列表")
    @GetMapping("/{id}/menuIds")
    public Result<List<Long>> getRoleMenuIds(@PathVariable Long id) {
        List<Long> result = roleService.getRoleMenuIds(id);
        return Result.success(result);
    }

    @Operation(summary = "分配权限")
    @PutMapping("/{id}/menus")
    public Result<String> assignMenusToRole(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        boolean result = roleService.assignMenusToRole(id, menuIds);
        return Result.judge(result);
    }

    /**
     * 删除角色
     */
    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:role:delete')")
    @Log(value = "删除角色", module = LogModuleEnum.ROLE)
    public Result<String> deleteRole(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.success("角色删除成功");
    }
}
