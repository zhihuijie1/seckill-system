package com.chengcheng.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 秒杀订单表
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_seckill_order")
public class SeckillOrder implements Serializable {

    private long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private Long goodsId;


}
