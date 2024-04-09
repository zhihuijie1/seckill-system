package com.chengcheng.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.utils.Result;
import com.chengcheng.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface IUserService extends IService<User> {
    Result doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    User getByUserTicket(HttpServletRequest request, HttpServletResponse response, String userTicket);

}
