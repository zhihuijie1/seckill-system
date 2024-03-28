package com.chengcheng.seckill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.mapper.UserMapper;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IUserService;
import com.chengcheng.seckill.utils.MD5;
import com.chengcheng.seckill.utils.Result;
import com.chengcheng.seckill.utils.ResultCodeEnum;
import com.chengcheng.seckill.utils.Validator;
import com.chengcheng.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service("UserServiceImpl")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 登录功能
     */
    @Override
    public Result doLogin(LoginVo loginVo) {
        //先把手机号码，密码拿出来
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //-----------------------------  begin -----------------------------
        //对手机号码的校验可以写一个自己的注解，这样有更好的通用性。
        //判空
        /*if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        System.out.println("-----------null");
        System.out.println(mobile);
        //判断号码格式对不对
        if(!Validator.isMobile(mobile)) {
            return Result.fail(ResultCodeEnum.MOBILE_ERROR);
        }
        System.out.println("-----------ismobile");*/
        //-----------------------------  end -----------------------------

        //根据手机号码从数据库取出对应的用户
        User user = userMapper.selectById(mobile);
        System.out.println("test");
        if (user == null) {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        System.out.println(user.getNickname());
        System.out.println("数据库中的第二次    "+user.getPassword());

        System.out.println("第一次加密的     " + password);
        System.out.println("-----------mapper");
        //有当前的用户。然后进行密码校对
        System.out.println("第2次"+"  "+  MD5.formPassToDBPass(password, user.getSalt()));
        if (!MD5.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        System.out.println("-----------password");
        //用户名与密码全部正确
        return Result.ok();
    }
}
