package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.river.malladmin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @TableName biz_product
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "biz_product")
@Data
public class Product extends BaseEntity {

    /**
     *
     */
    private Long shopId;

    /**
     *
     */
    private Long categoryId;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String image;

    /**
     * 现价
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * -1表示无限库存
     */
    private Integer stock;

    /**
     * 1-上架 0-下架
     */
    private Integer status;

    /**
     * 销量
     */
    private Integer sales;

    /**
     *
     */
    private Integer sort;

}