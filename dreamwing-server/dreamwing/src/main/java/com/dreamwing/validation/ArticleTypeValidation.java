package com.dreamwing.validation;

import com.dreamwing.anno.ArticleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArticleTypeValidation implements ConstraintValidator<ArticleType, Integer> {
    public boolean isValid(Integer s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return s == 1 || s == 2 || s == 3;
    }
}
