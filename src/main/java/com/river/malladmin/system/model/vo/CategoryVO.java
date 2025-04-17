package com.river.malladmin.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.river.malladmin.system.model.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "分类分页对象")
public class CategoryVO {

    @Schema(description = "分类ID")
    private Long id;

    private Long shopId;

    private String name;

    private Integer sort;

    @Schema(description = "1-显示 0-隐藏")
    private Integer status;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static CategoryVO from(Category entity) {
        if (Objects.isNull(entity)) return null;
        CategoryVO menuVO = new CategoryVO();
        BeanUtils.copyProperties(entity, menuVO);
        return menuVO;
    }
}
