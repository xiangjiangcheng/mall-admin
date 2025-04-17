package com.river.malladmin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.common.result.PageResult;
import com.river.malladmin.common.result.Result;
import com.river.malladmin.system.model.form.CategoryForm;
import com.river.malladmin.system.model.query.CategoryPageQuery;
import com.river.malladmin.system.model.vo.CategoryVO;
import com.river.malladmin.system.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JiangCheng Xiang
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/categorys")
@RequiredArgsConstructor
@Tag(name = "05.Category Apis")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "分类列表")
    @GetMapping("/page")
    public PageResult<CategoryVO> getCategoryList(CategoryPageQuery queryParams) {
        IPage<CategoryVO> list = categoryService.getCategoryList(queryParams);
        return PageResult.success(list);
    }

    @ApiOperationSupport(order = 2)
    @Operation(summary = "分类下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> getCategoryOptions() {
        List<Option<Long>> list = categoryService.listCategoryOptions();
        return Result.success(list);
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "获取分类表单数据")
    @GetMapping("/{id}/form")
    public Result<CategoryForm> getCategoryForm(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        CategoryForm category = categoryService.getCategoryForm(id);
        return Result.success(category);
    }

    @ApiOperationSupport(order = 4)
    @Operation(summary = "新增分类")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:category:add')")
    public Result<Boolean> addCategory(@RequestBody CategoryForm categoryForm) {
        boolean result = categoryService.saveCategory(null, categoryForm);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "修改分类")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermission('sys:category:edit')")
    public Result<Boolean> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryForm categoryForm) {
        boolean result = categoryService.saveCategory(id, categoryForm);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 6)
    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:category:delete')")
    public Result<Boolean> deleteMenu(
            @Parameter(description = "分类ID") @PathVariable("id") Long id
    ) {
        boolean result = categoryService.deleteCategory(id);
        return Result.judge(result);
    }

}
