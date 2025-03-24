package com.river.malladmin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.system.model.entity.User;
import com.river.malladmin.system.model.query.UserPageQuery;
import com.river.malladmin.system.model.vo.UserPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiang
 * @description 针对表【sys_user(用户表)】的数据库操作Mapper
 * @createDate 2025-03-20 15:23:50
 * @Entity com.river.malladmin.model.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<UserPageVO> getUserPage(@Param("page") Page<UserPageVO> page, @Param("query") UserPageQuery query);
}




