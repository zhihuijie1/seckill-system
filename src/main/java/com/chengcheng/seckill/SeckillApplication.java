package com.chengcheng.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//添加MapperScan注解的功能是：直接扫描指定包中的所有mapper接口，让spring一次性实现他们的实现类，并在以后的操作中可以直接注入。
//不用这个注解，需要在每一个Mapper接口上添加@Mapper注解来让spring识别
@MapperScan("com.chengcheng.seckill.mapper")
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}

//对用户密码进行两次MD5加密的原因
//pass = MD5(明文+固定salt)
//第一次进行加密 -- 客户端向服务器发送前进行MD5加密 -- 防止密码明文在网络中直接传输，会被拦截捕获
//pass = MD5(用户输入+随机salt)
//第二次进行加密 -- 加密后的密码在存入数据库之前再进行MD5加密 -- 防止数据库泄露后根据第一次加密的密码倒推出密码明文
