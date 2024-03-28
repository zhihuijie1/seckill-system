package com.chengcheng.seckill.exception;

import com.chengcheng.seckill.utils.Result;
import com.chengcheng.seckill.utils.ResultCodeEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//声明一个全局异常处理器类，用于处理Controller层抛出的异常。
@RestControllerAdvice
public class GlobalExceptionHandler {
    //声明一个异常处理方法，用于处理Exception类型的异常。
    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.fail(ex.getResultCodeEnum());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            Result result = Result.fail(ResultCodeEnum.BIND_ERROR);
            result.setMessage("参数校验异常：" +
                    ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        return Result.fail(ResultCodeEnum.SERVICE_ERROR);
    }
}
