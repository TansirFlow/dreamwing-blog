package com.dreamwing.anno;

import com.dreamwing.validation.UserStateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(
        validatedBy = {UserStateValidation.class}//提供校验规则的类
)
public @interface UserState {
    String message() default "userState的值只能是正常或封禁";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
