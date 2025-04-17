package com.river.malladmin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.system.model.entity.Category;
import com.river.malladmin.system.model.query.CategoryPageQuery;
import com.river.malladmin.system.model.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiang
 * @description 针对表【biz_category】的数据库操作Mapper
 * @createDate 2025-04-15 21:52:30
 * @Entity com.river.malladmin.system.model.entity.Category
 */
public interface CategoryMapper extends BaseMapper<Category> {

    IPage<CategoryVO> getCategoryPage(@Param("page") Page<CategoryVO> page, @Param("queryParams") CategoryPageQuery queryParams);
}




