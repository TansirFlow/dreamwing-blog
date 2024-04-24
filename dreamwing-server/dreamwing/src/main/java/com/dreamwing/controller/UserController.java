package com.dreamwing.controller;

import com.dreamwing.pojo.Result;
import com.dreamwing.pojo.User;
import com.dreamwing.service.UserService;
import com.dreamwing.utils.JwtUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Validator validator;

    public UserController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        System.out.println(user);
        Set<ConstraintViolation<User>> registerViolations = validator.validate(user, User.RegisterGroup.class);
        if (!registerViolations.isEmpty()) {
            System.out.println(registerViolations);
            System.out.println("error");
            return Result.error("注册参数不合法");
        }
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println(user);
        Set<ConstraintViolation<User>> loginViolations = validator.validate(user, User.LoginGroup.class);
        if (!loginViolations.isEmpty()) {
            System.out.println("error");
            return Result.error("登录参数不合法");
        }
        User findUser=userService.login(user);
        if(findUser==null){
            return Result.error("账号或密码错误");
        }
        //----------------返回token-----------------------
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", findUser.getId());
        claims.put("username", findUser.getUsername());
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> operates = stringRedisTemplate.opsForValue();
        operates.set(token, token, 7, TimeUnit.DAYS);
        //-----------------------------------------------
        return Result.success(token);
    }
}
