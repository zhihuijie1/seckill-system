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

    /**
     * 跳转功能
     */
    @GetMapping("/toList")
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket) {
        System.out.println(1);
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        //User user = (User) session.getAttribute(userTicket);
        System.out.println("1 my ticket is " + ticket);
        User user = userService.getByUserTicket(request, response, ticket);
        System.out.println(3);
        if (user == null) {
            return "login";
        }
        System.out.println(user.getNickname() + "  " + user.getPassword() + "  " + user.getLoginCount());
        model.addAttribute("user", user);
        System.out.println(4);
        return "goodsList";
    }
}
