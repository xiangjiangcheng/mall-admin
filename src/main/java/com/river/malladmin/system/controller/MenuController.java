package com.river.malladmin.system.controller;

import com.river.malladmin.common.base.Option;
import com.river.malladmin.common.result.Result;
import com.river.malladmin.system.model.form.MenuForm;
import com.river.malladmin.system.model.query.MenuPageQuery;
import com.river.malladmin.system.model.vo.MenuVO;
import com.river.malladmin.system.model.vo.RouteVO;
import com.river.malladmin.system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Tag(name = "04.Menu Apis")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "获取菜单树 (角色分配权限)")
    @GetMapping("/tree")
    public Result<List<Option<Long>>> getMenuTree() {
        List<Option<Long>> result = menuService.getMenuTree();
        return Result.success(result);
    }

    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    public Result<List<MenuVO>> getMenus(MenuPageQuery query) {
        List<MenuVO> result = menuService.getMenus(query);
        return Result.success(result);
    }

    @Operation(summary = "菜单表单数据")
    @GetMapping("/{id}/form")
    public Result<MenuForm> getMenuForm(
            @Parameter(description = "菜单ID") @PathVariable Long id) {
        MenuForm menu = menuService.getMenuForm(id);
        return Result.success(menu);
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:menu:add')")
    public Result<?> addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermission('sys:menu:edit')")
    public Result<?> updateMenu(
            @RequestBody MenuForm menuForm
    ) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:menu:delete')")
    public Result<?> deleteMenu(
            @Parameter(description = "菜单ID") @PathVariable("id") Long id
    ) {
        boolean result = menuService.deleteMenu(id);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public Result<?> updateMenuVisible(
            @Parameter(description = "菜单ID") @PathVariable Long menuId,
            @Parameter(description = "显示状态(1:显示;0:隐藏)") Integer visible

    ) {
        boolean result = menuService.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }

    @Operation(summary = "获取菜单路由列表")
    @GetMapping("/routes")
    public Result<List<RouteVO>> getRoutes() {
        List<RouteVO> result = menuService.getRoutes();
        return Result.success(result);
    }

}
