package com.river.malladmin.common.contant;

/**
 * Redis 相关常量
 */
public interface RedisConstants {

    interface Auth {
        // 黑名单 Token（用于退出登录或注销）
        // string类型的key
        String TOKEN_BLACKLIST = "auth:token:blacklist:{}";
    }

    interface System {
        // hash类型的key
        String CONFIG = "system:config";
        String ROLE_PERMS = "system:role:perms";
    }

    interface Captcha {
        String IMAGE_CODE = "captcha:image:{}";
    }

}
