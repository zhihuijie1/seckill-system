package com.chengcheng.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order")
public class Order implements Serializable {

    private long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 收货地址ID
     */
    private Long deliveryAddrId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer goodsCount;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 1pc 2安卓 3ios
     */
    private Integer orderChannel;

    /**
     * 0未支付 1已支付 2已发货 3已收货 4已退款 5已完成
     */
    private Integer status;

    /**
     * 订单的创建时间
     */
    private LocalDateTime createDate;

    /**
     * 支付时间
     */
    private LocalDateTime payDate;


}
