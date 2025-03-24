package com.river.malladmin.security.filter;

import com.river.malladmin.common.contant.BizConstants;
import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.common.util.ResponseUtils;
import com.river.malladmin.security.manager.JwtTokenManager;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT token 验证和解析过滤器
 * 用于从请求头中获取 JWT Token，验证其有效性，并将用户信息设置到 Sprint security 的上下文中。
 * 如果Token无效或者解析失败，直接返回错误响应
 *
 * @author JiangCheng Xiang
 */
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (StringUtils.isNotBlank(token) && token.startsWith(BizConstants.BEARER_PREFIX)) {
                // 去除 Bearer 前缀
                token = token.substring(BizConstants.BEARER_PREFIX.length());
                // 校验 JWT Token ，包括验签和是否过期
                boolean isValidate = jwtTokenManager.validateToken(token);
                if (!isValidate) {
                    ResponseUtils.writeErrMsg(response, ResultCode.TOKEN_INVALID);
                    return;
                }

                // 解析 JWT Token 获取用户信息
                Authentication authentication = jwtTokenManager.parseToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            ResponseUtils.writeErrMsg(response, ResultCode.TOKEN_INVALID);
            return;
        }
        // 无 Token 或 Token 验证通过时，继续执行过滤链。
        // 如果请求不在白名单内（例如登录接口、静态资源等），
        // 后续的 AuthorizationFilter 会根据配置的权限规则和安全策略进行权限校验。
        // 例如：
        // - 匹配到 permitAll() 的规则会直接放行。
        // - 需要认证的请求会校验 SecurityContext 中是否存在有效的 Authentication。
        // 若无有效 Authentication 或权限不足，则返回 403 Forbidden。
        filterChain.doFilter(request, response);
    }
}
