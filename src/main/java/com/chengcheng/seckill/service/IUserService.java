package com.chengcheng.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.utils.Result;
import com.chengcheng.seckill.vo.LoginVo;


public interface IUserService extends IService<User> {
    Result doLogin(LoginVo loginVo);

}
