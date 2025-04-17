package com.river.malladmin.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @Schema(description = "标签类型")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String tag;


    @Schema(description = "子选项列表")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option<T>> children;

    public Option(T value, String label, String tag) {
        this.value = value;
        this.label = label;
        this.tag= tag;
    }

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
