package com.river.malladmin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.river.malladmin.system.model.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author xiang
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByRoleCodes(@Param("roleCodes") Set<String> roleCodes);
}




