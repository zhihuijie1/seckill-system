package com.chengcheng.seckill.exception;

import com.chengcheng.seckill.utils.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    ResultCodeEnum resultCodeEnum;
}
