package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @TableName biz_shop
 */
@TableName(value = "biz_shop")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {

    /**
     * 关联商家用户ID
     */
    private Long userId;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺logo
     */
    private String logo;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 营业时间，如"09:00-21:00"
     */
    private String businessHours;

    /**
     * 1-营业中 2-休息中
     */
    private Integer status;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     * 起送金额
     */
    private BigDecimal minOrderAmount;

}