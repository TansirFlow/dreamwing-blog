package com.dreamwing.validation;

import com.dreamwing.anno.ArticleState;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArticleStateValidation implements ConstraintValidator<ArticleState,Integer> {
    /**
     * 文章状态只能是1或2或3
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return s == 1 || s == 2 || s == 3;
    }
}
