package com.chengcheng.seckill.vo;


import com.chengcheng.seckill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}

/**
 * goodsVo
 * 同时查询商品表和秒杀商品表的返回对象
 * 即将 秒杀商品表 与 商品表 进行取交集，直接使用goodsvo作为返回值。
 * */