package com.river.malladmin.security.manager;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.river.malladmin.common.contant.RedisConstants;
import com.river.malladmin.security.model.AuthenticationToken;
import com.river.malladmin.security.model.SysUserDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * JWT Token 管理类
 *
 * @author JiangCheng Xiang
 */
@Component
public class JwtTokenManager {

    /**
     * JWT 密钥，用于签名和解签名
     */
    private final String secretKey = " SecretKey012345678901234567890123456789012345678901234567890123456789";

    /**
     * 访问令牌有效期(单位：秒), 默认 1 小时
     */
    private final Integer accessTokenTimeToLive = 3600;

    /**
     * # 刷新令牌有效期(单位：秒)，默认 7 天，-1 表示永不过期
     */
    private final Integer refreshTokenTimeToLive = 604800;

    private final RedisTemplate<String, Object> redisTemplate;

    public JwtTokenManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成 JWT 访问令牌 - 用于登录认证成功后生成 JWT Token
     *
     * @param authentication 用户认证信息
     * @return JWT 访问令牌
     */

    public AuthenticationToken generateToken(Authentication authentication) {
        String accessToken = generateToken(authentication, accessTokenTimeToLive);
        String refreshToken = generateToken(authentication, refreshTokenTimeToLive);
        return AuthenticationToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(accessTokenTimeToLive)
                .tokenType("Bearer")
                .build();

    }

    public String generateToken(Authentication authentication, int ttl) {
        SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
        Map<String, Object> payload = new HashMap<>();
        // 将用户 ID 放入 JWT 载荷中， 如有其他扩展字段也可以放入
        payload.put("userId", userDetails.getUserId());

        // 将用户的角色和权限信息放入 JWT 载荷中，例如：["ROLE_ADMIN", "sys:user:query"]
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        payload.put("authorities", authorities);

        Date now = new Date();
        payload.put(JWTPayload.ISSUED_AT, now);

        // 设置过期时间 -1 表示永不过期
        if (ttl != -1) {
            Date expiresAt = DateUtil.offsetSecond(now, ttl);
            payload.put(JWTPayload.EXPIRES_AT, expiresAt);
        }
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());

        return JWTUtil.createToken(payload, secretKey.getBytes());
    }


    /**
     * 解析 JWT Token 获取 Authentication 对象 - 用于接口请求时解析 JWT Token 获取用户信息
     *
     * @param token JWT Token
     * @return Authentication 对象
     */
    public Authentication parseToken(String token) {

        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        SysUserDetails userDetails = new SysUserDetails();
        userDetails.setUserId(payloads.getLong("userId")); // 用户ID
        userDetails.setUsername(payloads.getStr(JWTPayload.SUBJECT)); // 用户名
        // 角色集合
        Set<SimpleGrantedAuthority> authorities = payloads.getJSONArray("authorities")
                .stream()
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority)))
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * 验证 JWT Token 是否有效
     *
     * @param token JWT Token 不携带 Bearer 前缀
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        // 检查 Token 是否有效(验签 + 是否过期)
        boolean isValid = jwt.setKey(secretKey.getBytes()).validate(0);
        if (isValid) {
            JSONObject payloads = jwt.getPayloads();
            // jwt的唯一身份标识
            String jwtId = (String) payloads.get(JWTPayload.JWT_ID);

            // TODO: 3/26/2025  检查是否在黑名单内
            Object o = redisTemplate.opsForValue().get(token);
            // if () {
            //
            // }


        }
        return isValid;
    }


    /**
     * 将token加入黑名单中，（应用场景 用户注销和修改密码）
     *
     * @param token 旧的token
     */
    public void invalidateToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);

        // 黑名单Token Key
        String key = RedisConstants.Auth.BLACKLIST_TOKEN + jwt.getPayloads().get(JWTPayload.JWT_ID);

        // TODO: 3/26/2025
        // redisTemplate.opsForValue().set(key, null, expirationIn, TimeUnit.SECONDS);
    }


}
