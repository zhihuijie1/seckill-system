package com.chengcheng.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    @NotNull
    @NotEmpty
    @IsMobile
    private String mobile;

    @NotNull
    @NotEmpty
    @Length(min = 32)//加密后的密码最小长度是32位
    private String password;
}
