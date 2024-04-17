package com.chengcheng.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengcheng.seckill.pojo.Goods;
import com.chengcheng.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author chengcheng
 * @since 2024-04-11
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();


    GoodsVo findGoodVoByGoodsId(Long goodsId);

}
