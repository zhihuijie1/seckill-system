package com.chengcheng.seckill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.exception.GlobalException;
import com.chengcheng.seckill.mapper.UserMapper;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IUserService;
import com.chengcheng.seckill.utils.*;
import com.chengcheng.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录功能
     */
    @Override
    public Result doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        //先把手机号码，密码拿出来
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //根据手机号码从数据库取出对应的用户
        User user = userMapper.selectById(mobile);
        System.out.println("test");
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.LOGIN_ERROR);
        }
        //有当前的用户。然后进行密码校对
        if (!MD5.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(ResultCodeEnum.LOGIN_ERROR);
        }
        //生成cookieID
        String userTicket = UUID.uuid();
        //将用户信息放到redis中
        redisTemplate.opsForValue().set("user:" + userTicket, user);
        User usr = (User) (redisTemplate.opsForValue().get("user:" + userTicket));
        System.out.println("my user is " + usr.getNickname());
        CookieUtil.setCookie(request, response, "userTicket", userTicket);
        //用户名与密码全部正确
        //将ticket返回，因为前端需要 -- document.cookie = "userTicket=" + data.object;
        return Result.ok();
    }

    @Override
    public User getByUserTicket(HttpServletRequest request, HttpServletResponse response, String userTicket) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        System.out.println("2 userTicket is " + userTicket);
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }

    /*更改密码*/
    @Override
    public Result updatePassword(String userTicket, Long id, String password) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5.inputPassToDBPass(password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            //删除Redis
            redisTemplate.delete("user:" + userTicket);
            return Result.ok();
        }
        return Result.fail(ResultCodeEnum.PASSWORD_UPDATE_FAIL);
    }
}
