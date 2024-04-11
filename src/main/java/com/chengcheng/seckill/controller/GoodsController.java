package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


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
}


