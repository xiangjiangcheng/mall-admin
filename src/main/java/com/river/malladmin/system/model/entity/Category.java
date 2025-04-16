package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.river.malladmin.common.base.BaseEntity;
import lombok.*;

/**
 * @TableName biz_category
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "biz_category")
public class Category extends BaseEntity {

    private Long shopId;

    private String name;

    private Integer sort;

    /**
     * 1-显示 0-隐藏
     */
    private Integer status;

}