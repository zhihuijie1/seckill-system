package com.chengcheng.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chengcheng.seckill.pojo.Order;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.vo.GoodsVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);
}
