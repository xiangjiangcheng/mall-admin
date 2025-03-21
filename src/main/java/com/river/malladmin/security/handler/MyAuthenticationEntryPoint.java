package com.river.malladmin.security.handler;

import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.common.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 用户未认证处理器
 *
 * @author JiangCheng Xiang
 */
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        if (authException instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResponseUtils.writeErrMsg(response, ResultCode.USER_PASSWORD_ERROR);
        } else {
            // token 无效或者 token 过期
            ResponseUtils.writeErrMsg(response, ResultCode.TOKEN_INVALID);
        }
    }
}
