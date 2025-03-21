package com.river.malladmin.system.controller;

import com.river.malladmin.common.result.Result;
import com.river.malladmin.system.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiangCheng Xiang
 */
@Tag(name = "01.认证中心")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Parameters({
            @Parameter(name = "username", description = "用户名", example = "admin"),
            @Parameter(name = "password", description = "密码", example = "123456")
    })
    public Result<String> login(@RequestParam String username,
                                @RequestParam String password) {
        String accessToken = authService.login(username, password);
        return Result.success(accessToken);
    }


}
