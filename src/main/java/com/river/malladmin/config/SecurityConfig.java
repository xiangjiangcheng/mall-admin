package com.river.malladmin.config;

import cn.hutool.captcha.generator.CodeGenerator;
import com.river.malladmin.security.filter.CaptchaValidationFilter;
import com.river.malladmin.security.filter.JWTAuthenticationFilter;
import com.river.malladmin.security.handler.MyAccessDeniedHandler;
import com.river.malladmin.security.handler.MyAuthenticationEntryPoint;
import com.river.malladmin.security.manager.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author JiangCheng Xiang
 */
@Configuration
@EnableWebSecurity // 启用 Spring Security 的 Web 安全功能，允许配置安全过滤链
@EnableMethodSecurity // 启用方法级别的安全控制（如 @PreAuthorize 等）
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 忽略认证的URI 地址
     */
    private final String[] IGNORE_URIS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/doc.html",
            "/api/v1/auth/login",
            "/api/v1/auth/captcha",
    };

    // JWT Token 服务 , 用于 Token 的生成、解析、验证等操作
    private final JwtTokenManager jwtTokenManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CodeGenerator codeGenerator;

    /**
     * 配置安全过滤链，用于定义哪些请求需要认证或授权
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 配置认证与授权规则
        http
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry
                                .requestMatchers(IGNORE_URIS).permitAll() // 登录接口无需认证
                                .anyRequest().authenticated() // 其他请求必须认证
                )
                // Captcha 验证码过滤器
                .addFilterBefore(new CaptchaValidationFilter(redisTemplate, codeGenerator), UsernamePasswordAuthenticationFilter.class)
                // JWT 验证和解析过滤器
                .addFilterBefore(new JWTAuthenticationFilter(jwtTokenManager), UsernamePasswordAuthenticationFilter.class)
                // 使用无状态认证，禁用 Session 管理（前后端分离 + JWT）
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 禁用 CSRF 防护（前后端分离通过 Token 验证，不需要 CSRF）
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认的表单登录功能
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用 HTTP Basic 认证（统一使用 JWT 认证）
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用 X-Frame-Options 响应头，允许页面被嵌套到 iframe 中
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                // 异常处理
                .exceptionHandling(configurer -> {
                    configurer
                            .authenticationEntryPoint(new MyAuthenticationEntryPoint()) // 未认证处理器
                            .accessDeniedHandler(new MyAccessDeniedHandler()); // 无权限访问处理器
                });

        return http.build();
    }

    /**
     * 配置密码加密器
     *
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
