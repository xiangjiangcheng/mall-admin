package com.river.malladmin.system.mapper;

import com.river.malladmin.system.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiang
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2025-03-20 15:23:50
* @Entity com.river.malladmin.model.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




