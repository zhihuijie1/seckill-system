package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    /**
     * 跳转功能
     */
    @GetMapping("/toList")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String userTicket) {
        if (StringUtils.isEmpty(userTicket)) {
            System.out.println("userTicket is null");
            return "login";
        }
        User user = (User) session.getAttribute(userTicket);
        System.out.println("userTicket is   " + userTicket);
        if (user == null) {
            System.out.println("user is null");
            return "login";
        }
        //user是合法的
        model.addAttribute("user", user);
        System.out.println("finish");
        return "goodsList";
    }
}
