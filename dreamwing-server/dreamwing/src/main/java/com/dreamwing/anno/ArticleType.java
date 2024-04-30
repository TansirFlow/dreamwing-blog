package com.dreamwing.anno;

import com.dreamwing.validation.ArticleStateValidation;
import com.dreamwing.validation.ArticleTypeValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(
        validatedBy = {ArticleTypeValidation.class}//提供校验规则的类
)
public @interface ArticleType {
    String message() default "ArticleType的值只能是1 or 2 or 3";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
