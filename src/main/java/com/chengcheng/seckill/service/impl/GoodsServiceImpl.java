package com.chengcheng.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.mapper.GoodsMapper;
import com.chengcheng.seckill.pojo.Goods;
import com.chengcheng.seckill.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
