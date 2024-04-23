package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.pojo.Goods;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IGoodsService;
import com.chengcheng.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    //函数逻辑：
    //直接将goodsList页面放入redis中，如果redis中已经有goodsList页面了，就直接拿出这个页面返回，如果没有这个页面
    //就手动创建这个页面，并将其存放入redis中，这样可以避免页面的重复渲染。
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
//produces用于指定响应返回的内容格式与编码格式，这样做是为了确保前端能够正确地解析响应内容。
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse
            response, Model model, User user) {
        String html = (String) redisTemplate.opsForValue().get("goodsList");//在redis中寻找这个页面
        if (!StringUtils.isEmpty(html)) {//redis中已经有这个页面了，就直接返回这个页面
            return html;
        }
        //redis中没有这个页面，就手动创建这个页面，存入Redis并返回
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        //Thymeleaf中的WebContext对象主要用于在模板中渲染数据
        //最通俗的理解：当你创建了一个 WebContext 对象，并将一些数据传递给它，比如收件人的名字，
        //Thymeleaf 就会把这些数据填写到模板的相应位置。最终，Thymeleaf 会根据填写好数据的模板生成一张完整的贺卡，也就是最终的 HTML 页面。
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //使用thymeleaf视图解析器获取模板引擎，然后调用process方法来处理goodsList模板文件，
        //在处理模板时，会使用之前创建的 WebContext 对象（webContext），它包含了要填充到模板中的数据。
        //这个过程会将模板中的标签替换为具体的数据，
        //并将填充好的thymeleaf模板渲染成 HTML 页面。
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (!StringUtils.isEmpty(html)) {
            //goodsList是键， html是值 设置的时间是60s
            redisTemplate.opsForValue().set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }


    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    //produces是RequestMapping一个注解，用于指定响应返回格式与字符编码格式，便于前端处理响应
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable Long goodsId) {
        String html = (String) redisTemplate.opsForValue().get("goodsDetail" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //redis中没有goodsDetail这个页面
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodVoByGoodsId(goodsId);
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
        //如果为空，手动渲染，存入Redis并返回
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if (!StringUtils.isEmpty(html)) {
            redisTemplate.opsForValue().set("goodsDetail:" + goodsId, html, 60, TimeUnit.SECONDS);
        }
        return html;
    }
}