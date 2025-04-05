package com.river.malladmin.system.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.river.malladmin.common.contant.RedisConstants;
import com.river.malladmin.common.enums.CaptchaTypeEnum;
import com.river.malladmin.config.property.CaptchaProperties;
import com.river.malladmin.security.manager.JwtTokenManager;
import com.river.malladmin.security.model.AuthenticationToken;
import com.river.malladmin.security.utils.SecurityUtils;
import com.river.malladmin.system.model.vo.CaptchaVO;
import com.river.malladmin.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.concurrent.TimeUnit;

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

    private final CaptchaProperties captchaProperties;
    private final CodeGenerator codeGenerator;
    private final Font captchaFont;

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

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    @Override
    public CaptchaVO getCaptcha() {

        String captchaType = captchaProperties.getType();
        int width = captchaProperties.getWidth();
        int height = captchaProperties.getHeight();
        int interfereCount = captchaProperties.getInterfereCount();
        int codeLength = captchaProperties.getCode().getLength();

        AbstractCaptcha captcha;
        if (CaptchaTypeEnum.CIRCLE.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createCircleCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.GIF.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createGifCaptcha(width, height, codeLength);
        } else if (CaptchaTypeEnum.LINE.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createLineCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.SHEAR.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createShearCaptcha(width, height, codeLength, interfereCount);
        } else {
            throw new IllegalArgumentException("Invalid captcha type: " + captchaType);
        }
        captcha.setGenerator(codeGenerator);
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setFont(captchaFont);

        String captchaCode = captcha.getCode();
        String imageBase64Data = captcha.getImageBase64Data();

        // 验证码文本缓存至Redis，用于登录校验
        String captchaKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(
                StrUtil.format(RedisConstants.Captcha.IMAGE_CODE, captchaKey),
                captchaCode,
                captchaProperties.getExpireSeconds(),
                TimeUnit.SECONDS
        );

        return CaptchaVO.builder()
                .captchaKey(captchaKey)
                .captchaBase64(imageBase64Data)
                .build();
    }
}
