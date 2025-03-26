package com.river.malladmin.system.service.impl;

import com.river.malladmin.common.contant.RedisConstants;
import com.river.malladmin.security.manager.JwtTokenManager;
import com.river.malladmin.security.model.AuthenticationToken;
import com.river.malladmin.security.utils.SecurityUtils;
import com.river.malladmin.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public AuthenticationToken login(String username, String password) {
        // 1. 创建用于密码认证的令牌（未认证）
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username.trim(), password);

        // 2. 执行认证（认证中）
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 认证成功后生成 JWT 令牌（已认证）
        return jwtTokenManager.generateToken(authentication);
    }

    @Override
    public Boolean logout() {
        String tokenFromRequest = SecurityUtils.getTokenFromRequest();
        // 将token放入redis黑名单中
        jwtTokenManager.invalidateToken(tokenFromRequest);
        // 清空Security上下文
        SecurityContextHolder.clearContext();
        return true;
    }
}
