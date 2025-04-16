package com.river.malladmin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.river.malladmin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @TableName biz_order
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "biz_order")
@Data
public class Order extends BaseEntity {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     *
     */
    private Long shopId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal actualAmount;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     *
     */
    private String remark;

    /**
     * 0-待支付 1-已支付/待接单 2-已接单/制作中 3-配送中 4-已完成 5-已取消
     */
    private Integer status;

    /**
     * 支付时间
     */
    private Date payTime;

}