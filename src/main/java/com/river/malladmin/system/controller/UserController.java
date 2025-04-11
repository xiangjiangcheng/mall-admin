package com.river.malladmin.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.common.annotation.Log;
import com.river.malladmin.common.enums.LogModuleEnum;
import com.river.malladmin.common.result.PageResult;
import com.river.malladmin.common.result.Result;
import com.river.malladmin.system.model.entity.User;
import com.river.malladmin.system.model.form.UserForm;
import com.river.malladmin.system.model.query.UserPageQuery;
import com.river.malladmin.system.model.vo.UserDetailsVO;
import com.river.malladmin.system.model.vo.UserPageVO;
import com.river.malladmin.system.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "02.User Apis")
public class UserController {

    private final UserService userService;

    /**
     * 获取用户列表
     */
    @Operation(summary = "获取用户列表")
    @GetMapping
    // @PreAuthorize("@ss.hasPermission('sys:user:query')")
    @Log(value = "获取用户列表", module = LogModuleEnum.USER)
    public Result<List<User>> listUsers() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    @Operation(summary = "用户分页列表")
    @GetMapping("/page")
    // @PreAuthorize("@ss.hasPermission('sys:user:query')")
    @Log(value = "用户分页列表", module = LogModuleEnum.USER)
    public PageResult<UserPageVO> pageUsers(@Valid UserPageQuery query) {
        Page<UserPageVO> result = userService.getUserPage(query);
        return PageResult.success(result);
    }

    /**
     * 获取用户详情
     */
    @Operation(summary = "获取用户详情")
    @Parameters({
            @Parameter(name = "id", description = "用户ID", in = ParameterIn.PATH)
    })
    @GetMapping("/{id}")
    // @PreAuthorize("@ss.hasPermission('sys:user:view')")
    @Log(value = "获取用户详情", module = LogModuleEnum.USER)
    public Result<UserDetailsVO> getUserById(@PathVariable Long id) {
        UserDetailsVO user = userService.getUserById(id);
        return Result.success(user);
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:user:add')")
    @Log(value = "新增用户", module = LogModuleEnum.USER)
    public Result<Long> createUser(@Valid @RequestBody UserForm userForm) {
        Long userId = userService.saveUser(userForm);
        return Result.success(userId);
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}")
    @Parameters({
            @Parameter(name = "id", description = "用户ID", in = ParameterIn.PATH)
    })
    @PreAuthorize("@ss.hasPermission('sys:user:edit')")
    @Log(value = "更新用户信息", module = LogModuleEnum.USER)
    public Result<String> updateUser(@PathVariable Long id, @RequestBody @Valid UserForm userForm) {
        boolean result = userService.updateUser(id, userForm);
        return Result.judge(result);
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:user:delete')")
    @Log(value = "删除用户", module = LogModuleEnum.USER)
    public Result<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return Result.success("用户删除成功");
    }

    @Operation(summary = "获取当前登录用户")
    @GetMapping("/me")
    @Log(value = "获取当前登录用户", module = LogModuleEnum.USER)
    public Result<UserDetailsVO> me() {
        UserDetailsVO user = userService.me();
        return Result.success(user);
    }
}
