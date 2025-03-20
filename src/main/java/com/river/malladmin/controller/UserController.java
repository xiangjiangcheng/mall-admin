package com.river.malladmin.controller;

import com.river.malladmin.model.SysUser;
import com.river.malladmin.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JiangCheng Xiang
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private SysUserService userService;

    /**
     * 获取用户列表
     */
    @GetMapping
    public List<SysUser> listUsers() {
        List<SysUser> list = userService.list();
        return list;
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    public SysUser getUserById(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        System.out.println(user.getNickname());
        return user;
    }

    /**
     * 新增用户
     */
    @PostMapping
    public String createUser(@RequestBody SysUser user) {
        userService.save(user);
        return "用户创建成功";
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody SysUser user) {
        userService.updateById(user);
        return "用户更新成功";
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return "用户删除成功";
    }
}
