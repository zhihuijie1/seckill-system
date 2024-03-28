package com.chengcheng.seckill.validator;

import com.chengcheng.seckill.utils.Validator;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//实现 ConstraintValidator<IsMobile, String> -- 用来实现自定义验证器里面的验证逻辑
//泛型 -- IsMobile -- 当某个字段上面标有@IsMobile时，当前的这个自定义验证器会自动介入
//泛型 -- String -- 当前的验证器适用于哪种类型的字段 -- 即@IsMobile注解适用于标注String类型的字段，当验证该字段时，该验证器会自动接入
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    //
    private boolean required = false;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return Validator.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return Validator.isMobile(value);
            }
        }
    }

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }
}
