package com.dreamwing.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Integer id;//主键ID

    @NotEmpty(groups = {RegisterGroup.class,LoginGroup.class})
    @Pattern(regexp = "^\\S{3,16}$",groups = {RegisterGroup.class})
    private String username;//用户名

    @NotEmpty(groups = {RegisterGroup.class,LoginGroup.class})
    @Pattern(regexp = "^\\S{6,18}$",groups = {RegisterGroup.class})
    private String inputPassword;//前端传入的密码，不被忽略

    @JsonIgnore//转json时自动忽略password
    @Pattern(regexp = "^\\S{6,18}$")
    private String password;//后端发出的密码，被忽略

    @NotEmpty
    @Pattern(regexp = "^\\S{3,10}$")
    private String nickname;//昵称

    @NotEmpty(groups = {RegisterGroup.class})
    @Email(groups = {RegisterGroup.class})
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

    public interface RegisterGroup {}

    public interface LoginGroup {}

}
