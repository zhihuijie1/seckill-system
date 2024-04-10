package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;

    //--- 跳转功能 ---
    @RequestMapping("/toList")
    public String toList(Model model, User user) {
        //关于user的判断会放在MVC层处理
        model.addAttribute("user", user);
        return "goodsList";
    }
}
