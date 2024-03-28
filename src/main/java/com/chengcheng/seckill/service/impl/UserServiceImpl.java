package com.chengcheng.seckill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.exception.GlobalException;
import com.chengcheng.seckill.mapper.UserMapper;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IUserService;
import com.chengcheng.seckill.utils.*;
import com.chengcheng.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service("UserServiceImpl")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 登录功能
     */
    @Override
    public Result doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        //先把手机号码，密码拿出来
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //-----------------------------  begin -----------------------------
        //对手机号码的校验可以写一个自己的注解，这样有更好的通用性。
        //判空
        /*if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        //判断号码格式对不对
        if(!Validator.isMobile(mobile)) {
            return Result.fail(ResultCodeEnum.MOBILE_ERROR);
        }*/
        //-----------------------------  end -----------------------------

        //根据手机号码从数据库取出对应的用户
        User user = userMapper.selectById(mobile);
        System.out.println("test");
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.LOGIN_ERROR);
            //return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        //有当前的用户。然后进行密码校对
        if (!MD5.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(ResultCodeEnum.LOGIN_ERROR);
            //return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        //生成cookieID
        String userTicket = UUID.uuid();
        //将cookie与对象对应起来，存入到session中
        request.getSession().setAttribute(userTicket,user);
        CookieUtil.setCookie(request,response,"userTicket",userTicket);


        //用户名与密码全部正确
        //将ticket返回，因为前端需要 -- document.cookie = "userTicket=" + data.object;
        return Result.ok(userTicket);
    }
}
