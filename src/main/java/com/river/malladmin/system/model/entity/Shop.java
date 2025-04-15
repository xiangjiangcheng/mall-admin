package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联商家用户ID
     */
    private Long userId;

    /**
     *
     */
    private String name;

    /**
     *
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
     *
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

    /**
     * 创建人 ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}