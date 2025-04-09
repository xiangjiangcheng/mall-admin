package com.river.malladmin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus 字段自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间
     *
     * @param metaObject 元数据
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
    }

    /**
     * 更新填充更新时间
     *
     * @param metaObject 元数据
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}
