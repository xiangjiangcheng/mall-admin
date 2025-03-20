package com.river.malladmin;

import com.river.malladmin.system.model.SysUser;
import com.river.malladmin.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author JiangCheng Xiang
 */
//用于加载ApplicationContext，启动spring容器
// @ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest(classes = MallAdminApplication.class)
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysUserService userService;

    @Test
    void testSetAndGet() {
        Long userId = 1L;
        // 1. 从数据库中获取用户信息
        SysUser user = userService.getById(userId);
        log.info("从数据库中获取用户信息: {}", user);

        // 2. 将用户信息缓存到 Redis
        redisTemplate.opsForValue().set("user:" + userId, user);

        // 3. 从 Redis 中获取缓存的用户信息
        SysUser cachedUser = (SysUser) redisTemplate.opsForValue().get("user:" + userId);
        log.info("从 Redis 中获取用户信息: {}", cachedUser);
    }

    /**
     * 操作字符串
     */
    @Test
    public void testString() {
        //设置值
        stringRedisTemplate.opsForValue().set("String", "Mao");
        //获取值
        String string = stringRedisTemplate.opsForValue().get("String");

        //设置值且设置超时时间
        stringRedisTemplate.opsForValue().set("Middle", "Yu", 3, TimeUnit.MINUTES);
        String middle = stringRedisTemplate.opsForValue().get("Middle");
        System.out.println(middle);

        //删除数据
        Boolean isDelete = stringRedisTemplate.delete("String");
        System.out.println(isDelete ? "Yes" : "No");
    }

}
