package com.river.malladmin.system.controller;

import com.river.malladmin.common.result.Result;
import com.river.malladmin.system.model.SysUser;
import com.river.malladmin.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JiangCheng Xiang
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "01.User Apis")
public class UserController {

    private final SysUserService userService;

    /**
     * 获取用户列表
     */
    @Operation(summary = "获取用户列表")
    @GetMapping
    public Result<List<SysUser>> listUsers() {
        List<SysUser> list = userService.list();
        return Result.success(list);
    }

    /**
     * 获取用户详情
     */
    @Operation(summary = "获取用户详情")
    @Parameters({
            @Parameter(name = "id", description = "用户ID", in = ParameterIn.PATH)
    })
    @GetMapping("/{id}")
    public Result<SysUser> getUserById(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        log.info("user:{}", user);
        return Result.success(user);
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping
    public Result<String> createUser(@RequestBody SysUser user) {
        userService.save(user);
        return Result.success("用户创建成功");
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody SysUser user) {
        userService.updateById(user);
        return Result.success("用户更新成功");
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success("用户删除成功");
    }
}
