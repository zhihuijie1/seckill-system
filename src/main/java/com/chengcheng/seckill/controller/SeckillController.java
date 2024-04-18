package com.chengcheng.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengcheng.seckill.pojo.Order;
import com.chengcheng.seckill.pojo.SeckillOrder;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IGoodsService;
import com.chengcheng.seckill.service.IOrderService;
import com.chengcheng.seckill.service.ISeckillOrderService;
import com.chengcheng.seckill.utils.ResultCodeEnum;
import com.chengcheng.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errorMessage", ResultCodeEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new
                QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq(
                "goods_id",
                goodsId));
        if (seckillOrder != null) {// 说明之前已经抢购过了
            model.addAttribute("errorMessage", ResultCodeEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }
        //有库存且第一次抢购
        Order order = orderService.seckill(user, goods);//进行秒杀的逻辑处理 哪个用户对哪个商品进行秒杀，返回一个秒杀订单
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }
}