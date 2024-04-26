package com.dreamwing.controller;

import com.dreamwing.constants.GlobalConstants;
import com.dreamwing.constants.UserConstants;
import com.dreamwing.pojo.Result;
import com.dreamwing.pojo.User;
import com.dreamwing.service.UserService;
import com.dreamwing.utils.JwtUtil;
import com.dreamwing.utils.Md5Util;
import com.dreamwing.utils.ThreadLocalUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        System.out.println(user);
        Set<ConstraintViolation<User>> loginViolations = validator.validate(user, User.LoginGroup.class);
        if (!loginViolations.isEmpty()) {
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }
        String password = Md5Util.getMD5String(user.getInputPassword());
        user.setPassword(password);
        User findUser = userService.findByUserName(user.getUsername());
        if (findUser == null || !Objects.equals(findUser.getPassword(), user.getPassword())) {
            return Result.error(UserConstants.LOGIN_INFO_ERROR);
        }
        if (!Objects.equals(findUser.getState(), "正常")) {
            return Result.error(UserConstants.USER_BAN_LOGIN_ERROR);
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
        Set<ConstraintViolation<User>> updateViolations = validator.validate(user, User.UpdateGroup.class);
        if (!updateViolations.isEmpty()) {
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }

        userService.update(user);
        return Result.success();
    }

    @PostMapping("/changePwd")
    public Result changePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd)) {
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        User user = userService.findByUserName((String) claims.get("username"));
        if (!Md5Util.checkPassword(oldPwd, user.getPassword())) {
            return Result.error(UserConstants.OLD_PASSWORD_ERROR);
        } else {
            userService.updatePassword(newPwd, id);
            ValueOperations<String, String> operates = stringRedisTemplate.opsForValue();
            operates.getOperations().delete(token);
            return Result.success();
        }
    }

    @PostMapping("/avatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> getUserInfo() {
        User user = userService.getUserInfo();
        return Result.success(user);
    }

    @PostMapping("/register/getVerifyCode")
    public Result getVerifyCodeForRegister(
            @RequestParam @NotEmpty @Pattern(regexp = "^\\S{3,16}$") String username,
            @RequestParam @Email String email
    ) {
        User user = userService.findByUserName(username);
        if(user!=null){
            return Result.error(UserConstants.USER_NAME_ALREADY_EXIST_ERROR);
        }
        userService.sendRegisterVerifyCodeEmail(username,email);
        return Result.success();
    }

    @PostMapping("/findPwd/getVerifyCode")
    public Result getVerifyCodeForFindPwd(
            @RequestParam @NotEmpty @Pattern(regexp = "^\\S{3,16}$") String username,
            @RequestParam @Email String email
    ){
        User user = userService.findByUserName(username);
        if(user==null || !Objects.equals(user.getEmail(), email)){
            return Result.error("用户名与邮箱不匹配");
        }
        userService.sendFindPwdEmail(username,email);
        return Result.success();
    }

    @PostMapping("/findPwd")
    public Result findPwd(@RequestBody User user){
        Set<ConstraintViolation<User>> findPwdViolations = validator.validate(user, User.FindPwd.class);
        if (!findPwdViolations.isEmpty()) {
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }
        userService.findPwd(user);
        return Result.success();
    }
}

