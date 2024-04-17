package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.pojo.Goods;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IGoodsService;
import com.chengcheng.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    //--- 跳转功能 ---
    @RequestMapping("/toList")
    public String toList(Model model, User user) {//Model - 主要用来向前端传递数据，前端通过model获取数据然后方便渲染
        //关于user的判断会放在MVC层处理
        model.addAttribute("user", user);//在前端页面中就可以通过 ${user} 的方式来获取并显示用户对象的信息。
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        //在前端页面中就可以通过 ${goodsList} 的方式来获取并显示商品列表的信息
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long GoodsId) {
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodVoByGoodsId(GoodsId);
        model.addAttribute("goods", goods);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //剩余开始时间
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
            // 秒杀已结束
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
            // 秒杀中
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goodsDetail";
    }
}


