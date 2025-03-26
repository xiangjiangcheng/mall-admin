package com.river.malladmin.system.controller;

import com.river.malladmin.common.annotation.Log;
import com.river.malladmin.common.enums.LogModuleEnum;
import com.river.malladmin.common.result.Result;
import com.river.malladmin.security.model.AuthenticationToken;
import com.river.malladmin.system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangCheng Xiang
 */
@Tag(name = "01.认证中心")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户名密码登录")
    @PostMapping("/login")
    @Parameters({
            @Parameter(name = "username", description = "用户名", example = "admin"),
            @Parameter(name = "password", description = "密码", example = "123456")
    })
    @Log(value = "用户名密码登录", module = LogModuleEnum.AUTH)
    public Result<AuthenticationToken> login(@RequestParam String username,
                                             @RequestParam String password) {
        AuthenticationToken accessToken = authService.login(username, password);
        return Result.success(accessToken);
    }

    @Operation(summary = "注销登录")
    @GetMapping("/logout")
    @Log(value = "注销登录", module = LogModuleEnum.AUTH)
    public Result<Boolean> logout() {
        Boolean logout = authService.logout();
        return Result.success(logout);
    }


}
