package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.system.mapper.CategoryMapper;
import com.river.malladmin.system.model.entity.Category;
import com.river.malladmin.system.model.form.CategoryForm;
import com.river.malladmin.system.model.query.CategoryPageQuery;
import com.river.malladmin.system.model.vo.CategoryVO;
import com.river.malladmin.system.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiang
 * @description 针对表【biz_category】的数据库操作Service实现
 * @createDate 2025-04-15 21:52:30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<CategoryVO> getCategoryList(CategoryPageQuery queryParams) {
        return null;
    }

    @Override
    public List<Option<Long>> listCategoryOptions() {
        return null;
    }

    @Override
    public CategoryForm getCategoryForm(Long id) {
        return null;
    }

    @Override
    public boolean saveCategory(CategoryForm categoryForm) {
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        return false;
    }
}




