package com.chengcheng.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.mapper.GoodsMapper;
import com.chengcheng.seckill.pojo.Goods;
import com.chengcheng.seckill.service.IGoodsService;
import com.chengcheng.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    @Autowired
    private GoodsMapper goodsMapper;
    /**
     * 获取商品列表
     * 通过数据库查询来获取商品列表
     * @return List<GoodsVo>
     */
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodVoByGoodsId(Long goodsId) {
        System.out.println("---------------------- 3");
        return goodsMapper.findGoodVoByGoodsId(goodsId);
    }
}
