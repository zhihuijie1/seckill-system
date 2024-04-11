package com.chengcheng.seckill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.mapper.OrderMapper;
import com.chengcheng.seckill.pojo.Order;
import com.chengcheng.seckill.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
