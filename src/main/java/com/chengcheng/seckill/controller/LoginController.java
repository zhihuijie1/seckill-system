package com.chengcheng.seckill.controller;

import com.chengcheng.seckill.service.IUserService;
import com.chengcheng.seckill.utils.Result;
import com.chengcheng.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
@Slf4j //lombok打印日志的功能
public class LoginController {
    @Autowired
    IUserService iUserService;

    /**
     * 跳转功能
     */
    //Spring MVC配合Thymeleaf将会寻找并渲染login.html，从而向用户展示登录页面。
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录验证功能
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(@Validated LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) { //@Validated -- 会自动检查传入参数中信息是否正确
        Result result = iUserService.doLogin(loginVo, request, response);
        return result;
    }
}
