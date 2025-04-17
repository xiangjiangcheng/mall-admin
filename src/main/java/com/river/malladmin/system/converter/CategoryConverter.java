package com.river.malladmin.system.converter;

import com.river.malladmin.system.model.entity.Category;
import com.river.malladmin.system.model.form.CategoryForm;
import org.mapstruct.Mapper;

/**
 * 分类对象转换器
 *
 * @author JiangCheng Xiang
 * @since 2025-04-17 11:45
 */
@Mapper(componentModel = "spring")
public interface CategoryConverter {

    CategoryForm toForm(Category entity);

    Category toEntity(CategoryForm formData);
}