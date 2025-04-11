package com.river.malladmin.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author JiangCheng Xiang
 */
@Data
@NoArgsConstructor
@Schema(description = "下拉选项")
public class Option<T> {

    @Schema(description = "选项的值，例如：id")
    private T value;

    @Schema(description = "选项的标签，页面展示")
    private String label;

    @Schema(description = "子选项列表")
    private List<Option<T>> children;

    public Option(T value, String label, List<Option<T>> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

    public Option(T value, String label) {
        this.value = value;
        this.label = label;
    }
}
