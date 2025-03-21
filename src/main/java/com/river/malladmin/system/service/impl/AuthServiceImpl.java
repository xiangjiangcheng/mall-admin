package com.river.malladmin.system.service.impl;

import com.river.malladmin.security.manager.JwtTokenManager;
import com.river.malladmin.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author JiangCheng Xiang
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // 认证管理器 - 用于执行认证
    private final AuthenticationManager authenticationManager;

    // JWT 令牌服务类 - 用于生成 JWT 令牌
    private final JwtTokenManager jwtTokenManager;

    @Override
    public String login(String username, String password) {
        // 1. 创建用于密码认证的令牌（未认证）
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username.trim(), password);

        // 2. 执行认证（认证中）
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 认证成功后生成 JWT 令牌（已认证）
        return jwtTokenManager.generateToken(authentication);
    }
}
