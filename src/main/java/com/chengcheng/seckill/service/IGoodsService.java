package com.chengcheng.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chengcheng.seckill.pojo.Goods;
import com.chengcheng.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodVoByGoodsId(Long GoodsId);
}
