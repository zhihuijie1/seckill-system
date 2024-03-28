package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @RequestMapping("/toList")
    public String toList(HttpSession session, @CookieValue("userTicket") String userTicket, Model model) {
        if(userTicket == null) {
            return "login";
        }
        if(session.getAttribute(userTicket) == null) {
            return "login";
        }
    }
}
