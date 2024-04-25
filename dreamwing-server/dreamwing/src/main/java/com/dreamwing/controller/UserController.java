package com.dreamwing.controller;

import com.dreamwing.pojo.Result;
import com.dreamwing.pojo.User;
import com.dreamwing.service.UserService;
import com.dreamwing.utils.JwtUtil;
import com.dreamwing.utils.Md5Util;
import com.dreamwing.utils.ThreadLocalUtil;
import jakarta.validation.ConstraintViolation;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    public Result register(@RequestBody User user) {
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
    public Result login(@RequestBody User user) {
        System.out.println(user);
        Set<ConstraintViolation<User>> loginViolations = validator.validate(user, User.LoginGroup.class);
        if (!loginViolations.isEmpty()) {
            System.out.println("error");
            return Result.error("登录参数不合法");
        }
        String password = Md5Util.getMD5String(user.getInputPassword());
        user.setPassword(password);
        User findUser = userService.findByUserName(user.getUsername());
        if (findUser == null || !Objects.equals(findUser.getPassword(), user.getPassword())) {
            return Result.error("用户名或密码错误");
        }
        if (!Objects.equals(findUser.getState(), "正常")) {
            return Result.error("该用户已被封禁，无法登陆");
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

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        System.out.println(user);
        Set<ConstraintViolation<User>> updateViolations = validator.validate(user, User.UpdateGroup.class);
        if (!updateViolations.isEmpty()) {
            System.out.println("error");
            return Result.error("修改信息参数不合法");
        }
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/changePwd")
    public Result changePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd)) {
            return Result.error("缺少必要的参数");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        User user = userService.findByUserName((String) claims.get("username"));
        if (!Md5Util.checkPassword(oldPwd, user.getPassword())) {
            return Result.error("原密码错误");
        } else {
            userService.updatePassword(newPwd, id);
            ValueOperations<String, String> operates = stringRedisTemplate.opsForValue();
            operates.getOperations().delete(token);
            return Result.success();
        }
    }

    @PostMapping("/avatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(){
        User user=userService.getUserInfo();
        return Result.success(user);
    }
}

