package com.chengcheng.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration//配置类
@EnableWebMvc//开启MVC特性
public class WebConfig implements WebMvcConfigurer {//WebMvcConfigurer这个接口提供了一些方法，可以用来配置Spring MVC的一些行为。
    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Override
    //添加自定义的方法参数解析器到Spring MVC中
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //将userArgumentResolver添加到解析器列表中，这样Spring MVC在处理请求时会使用这个解析器来解析方法参数
        resolvers.add(userArgumentResolver);
    }


    //默认情况下，mvc会去static的默认路径下去找资源，但是我自己写了mvc配置类之后，会优先来配置类里面找指定的路径
    //所以要是在配置类里面不指定路径的话，是找不到静态资源的。
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}