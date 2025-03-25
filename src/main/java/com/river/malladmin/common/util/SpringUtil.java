package com.river.malladmin.common.util;

import com.river.malladmin.common.contant.Environ;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public class SpringUtil {

    @Getter
    private static ApplicationContext applicationContext;
    ///获取当前环境
    @Getter
    private static Environ environ = Environ.LOCAL;

    public static void setApplicationContext(ApplicationContext context) {
        SpringUtil.applicationContext = context;
        // 获取当前的系统环境
        Environment evn = applicationContext.getEnvironment();
        String[] activeProfiles = evn.getActiveProfiles();
        environ = Arrays.stream(activeProfiles).map(Environ::parse).findFirst().orElse(Environ.LOCAL);
    }

    // 获取配置文件配置值
    public static String getEvnProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    // 通过bean名称获取bean
    public static Object getBeanByName(String name) {
        try {
            return getApplicationContext().getBean(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getBeanByClassName(String className) {
        try {
            Class aClass = Class.forName(className);
            Object obj = getApplicationContext().getBean(aClass);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        if (SpringUtil.applicationContext == null) {
            return null;
        }
        try {
            return SpringUtil.applicationContext.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    // 当前是否为本地环境
    public static boolean isLocal() {
        return environ == Environ.LOCAL;
    }

    // 当前是否为开发环境
    public static boolean isDev() {
        return environ == Environ.DEV;
    }

    // 是否为测试环境
    public static boolean isTest() {
        return environ == Environ.TEST;
    }

    // 是否为QA环境
    public static boolean isQA() {
        return environ == Environ.QA;
    }

    // 是否为生产环境
    public static boolean isProd() {
        return environ == Environ.PROD;
    }

}
