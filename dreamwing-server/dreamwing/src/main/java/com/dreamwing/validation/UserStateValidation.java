package com.dreamwing.validation;

import com.dreamwing.anno.UserState;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserStateValidation implements ConstraintValidator<UserState,String> {
    /**
     * 返回true则校验通过
     * 返回false则检验不通过
     * @param s 输入的值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null){
            return false;
        }
        return s.equals("正常") || s.equals("封禁");
    }
}
