package com.chengcheng.seckill.service.impl;

import com.baomidou.ant.seckill.entity.Order;
import com.baomidou.ant.seckill.mapper.OrderMapper;
import com.baomidou.ant.seckill.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
