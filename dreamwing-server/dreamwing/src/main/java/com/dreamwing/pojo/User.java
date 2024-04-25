package com.dreamwing.pojo;

import com.dreamwing.anno.UserState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull(groups = {UpdateGroup.class})
    private Integer id;//主键ID

    @NotEmpty(groups = {RegisterGroup.class,LoginGroup.class,FindPwd.class})
    @Pattern(regexp = "^\\S{3,16}$",groups = {RegisterGroup.class,FindPwd.class})
    private String username;//用户名

    @NotEmpty(groups = {RegisterGroup.class,LoginGroup.class,FindPwd.class})
    @Pattern(regexp = "^\\S{6,18}$",groups = {RegisterGroup.class,FindPwd.class})
    private String inputPassword;//前端传入的密码，不被忽略

    @JsonIgnore//转json时自动忽略password
    @Pattern(regexp = "^\\S{6,18}$")
    private String password;//后端发出的密码，被忽略

    @NotEmpty(groups = {UpdateGroup.class})
    @Pattern(regexp = "^\\S{3,10}$",groups = {UpdateGroup.class})
    private String nickname;//昵称

    @NotEmpty(groups = {RegisterGroup.class,UpdateGroup.class})
    @Email(groups = {RegisterGroup.class,UpdateGroup.class})
    private String email;//邮箱
    private String avatar;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private String signature;//个性签名
    private Integer role;//用户角色
    @UserState
    private String state;//用户封禁状态
    @NotEmpty(groups = {RegisterGroup.class,FindPwd.class})
    private String verifyCode;//获取的验证码

    public interface RegisterGroup {}

    public interface LoginGroup {}

    public interface UpdateGroup {}

    public interface FindPwd {}

}
