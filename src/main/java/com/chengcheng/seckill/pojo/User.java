package com.chengcheng.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
//@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    //private static final long serialVersionUID = 1L;

    private long id;
    private String nickname;

    /**
     * MD5(明文+salt)
     */
    private String password;

    private String salt;

    /**
     * 头像
     */
    private String head;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

      /**
     * 登录次数
     */
    private Integer loginCount;


}
