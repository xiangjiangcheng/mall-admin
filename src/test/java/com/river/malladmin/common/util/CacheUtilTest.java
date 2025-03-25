package com.river.malladmin.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.river.malladmin.common.contant.BizConstants.CACHE_DEFAULT;


@SpringBootTest
class CacheUtilTest {

    @Autowired
    private CacheUtil cacheUtil;

    @Test
    void test() {
        // 将值放入缓存
        cacheUtil.put(CACHE_DEFAULT, "name", "张三");
        cacheUtil.put(CACHE_DEFAULT, "age", 25);

        // 从缓存中获取值
        System.out.println("Name: " + cacheUtil.get(CACHE_DEFAULT, "name"));
        System.out.println("Age: " + cacheUtil.get(CACHE_DEFAULT, "age"));

        // 检查缓存中是否包含指定键
        System.out.println("Contains key 'name': " + cacheUtil.containsKey(CACHE_DEFAULT, "name"));

        // 从缓存中移除键值对
        cacheUtil.remove(CACHE_DEFAULT, "age");

        // 再次检查缓存中是否包含指定键
        System.out.println("Contains key 'age': " + cacheUtil.containsKey(CACHE_DEFAULT, "age"));

        // 清空缓存
        cacheUtil.clear(CACHE_DEFAULT);
    }

}