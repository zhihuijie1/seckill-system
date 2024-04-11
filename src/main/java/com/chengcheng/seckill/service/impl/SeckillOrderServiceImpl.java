package com.chengcheng.seckill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.mapper.SeckillOrderMapper;
import com.chengcheng.seckill.pojo.SeckillOrder;
import com.chengcheng.seckill.service.ISeckillOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

}
