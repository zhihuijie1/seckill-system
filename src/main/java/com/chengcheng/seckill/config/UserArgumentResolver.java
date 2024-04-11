package com.chengcheng.seckill.config;

import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IUserService;
import com.chengcheng.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义用户参数
 */
@Component//指定这是一个Spring组件，会被Spring扫描并自动加入到spring容器中进行管理
//HandlerMethodArgumentResolver -- 处理controller层方法的参数
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    IUserService userService;

    @Override
    //supportsParameter方法 -- 指定处理的参数 -- 这里指定处理类型为User的参数
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return User.class == parameterType;
    }

    @Override
    //resolveArgument方法 -- 真正处理指定参数的方法
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取HttpServletRequest与HttpServletResponse
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        //获取cookieID
        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if (StringUtils.isEmpty(ticket)) {
            return null;
        }
        //根据cookieID获取User对象
        return userService.getByUserTicket(request, response, ticket);
    }
}