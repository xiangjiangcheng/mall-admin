package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.BasePageQuery;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.system.converter.CategoryConverter;
import com.river.malladmin.system.mapper.CategoryMapper;
import com.river.malladmin.system.model.entity.Category;
import com.river.malladmin.system.model.form.CategoryForm;
import com.river.malladmin.system.model.query.CategoryPageQuery;
import com.river.malladmin.system.model.vo.CategoryVO;
import com.river.malladmin.system.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiang
 * @description 针对表【biz_category】的数据库操作Service实现
 * @createDate 2025-04-15 21:52:30
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryConverter categoryConverter;

    @Override
    public IPage<CategoryVO> getCategoryList(CategoryPageQuery queryParams) {
        Page<CategoryVO> page = BasePageQuery.page(queryParams);
        return this.baseMapper.getCategoryPage(page, queryParams);
    }

    @Override
    public List<Option<Long>> listCategoryOptions() {
        List<Category> categoryList = this.list(new LambdaQueryWrapper<Category>()
                .orderByAsc(Category::getSort)
        );
        return buildCategoryOptions(categoryList);
    }

    private List<Option<Long>> buildCategoryOptions(List<Category> categoryList) {
        return categoryList.stream()
                .map(category -> new Option<>(category.getId(), category.getName())).collect(Collectors.toList());
    }

    @Override
    public CategoryForm getCategoryForm(Long id) {
        Category entity = this.getById(id);
        return categoryConverter.toForm(entity);
    }

    @Override
    public boolean saveCategory(Long id, CategoryForm formData) {
        Category entity = categoryConverter.toEntity(formData);
        entity.setId(id);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean deleteCategory(Long id) {
        // 逻辑删除
        return this.removeById(id);
    }
}




