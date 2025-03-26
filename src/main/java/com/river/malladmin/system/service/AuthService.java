package com.river.malladmin.system.service;

import com.river.malladmin.security.model.AuthenticationToken;

/**
 * @author JiangCheng Xiang
 */
public interface AuthService {

    AuthenticationToken login(String username, String password);

    Boolean logout();
}
