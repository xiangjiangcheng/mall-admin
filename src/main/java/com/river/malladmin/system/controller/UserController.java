package com.river.malladmin.system.controller;

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

    @Autowired
    private SysUserService userService;

    /**
     * 获取用户列表
     */
    @Operation(summary = "获取用户列表")
    @GetMapping
    public List<SysUser> listUsers() {
        List<SysUser> list = userService.list();
        return list;
    }

    /**
     * 获取用户详情
     */
    @Operation(summary = "获取用户详情")
    @Parameters({
            @Parameter(name = "id", description = "用户ID", in = ParameterIn.PATH)
    })
    @GetMapping("/{id}")
    public SysUser getUserById(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        log.info("user:{}", user);
        return user;
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping
    public String createUser(@RequestBody SysUser user) {
        userService.save(user);
        return "用户创建成功";
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody SysUser user) {
        userService.updateById(user);
        return "用户更新成功";
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return "用户删除成功";
    }
}
