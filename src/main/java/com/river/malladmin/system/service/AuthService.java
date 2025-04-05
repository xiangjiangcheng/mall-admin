package com.river.malladmin.system.service;

import com.river.malladmin.security.model.AuthenticationToken;
import com.river.malladmin.system.model.vo.CaptchaVO;

/**
 * @author JiangCheng Xiang
 */
public interface AuthService {

    AuthenticationToken login(String username, String password);

    Boolean logout();

    CaptchaVO getCaptcha();

}
