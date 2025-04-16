package com.river.malladmin.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "分类表单")
public class CategoryForm {

    @Schema(description = "分类ID")
    private Long id;

    private Long shopId;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "1-显示 0-隐藏")
    private Integer status;

}
