package com.river.malladmin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.system.model.entity.Category;
import com.river.malladmin.system.model.form.CategoryForm;
import com.river.malladmin.system.model.query.CategoryPageQuery;
import com.river.malladmin.system.model.vo.CategoryVO;

import java.util.List;

/**
 * @author xiang
 * @description 针对表【biz_category】的数据库操作Service
 * @createDate 2025-04-15 21:52:30
 */
public interface CategoryService extends IService<Category> {

    List<CategoryVO> getCategoryList(CategoryPageQuery queryParams);

    List<Option<Long>> listCategoryOptions();

    CategoryForm getCategoryForm(Long id);

    boolean saveCategory(CategoryForm categoryForm);

    boolean deleteCategory(Long id);
}
