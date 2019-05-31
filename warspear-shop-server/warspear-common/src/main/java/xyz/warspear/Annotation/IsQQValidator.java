package xyz.warspear.Annotation;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsQQValidator implements ConstraintValidator<IsQQ,String> {


    public boolean required = false;

    @Override
    public void initialize(IsQQ isQQ) {
        required = isQQ.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        String reg = "^[1-9][0-9]{4,9}$";

        if(required)
        {
            //必须的
            return s.matches(reg);
        }
        else {
            //判断值是否为空
            if (StringUtils.isEmpty(s)) {
                //为空的时候返回true
                return true;
            }
            //否则进行格式校验
            return s.matches(reg);

        }
    }
}
