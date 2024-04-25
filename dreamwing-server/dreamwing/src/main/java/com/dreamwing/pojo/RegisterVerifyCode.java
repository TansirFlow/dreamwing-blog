package com.dreamwing.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterVerifyCode {
    private String username;
    private String email;
    private String code;
    private LocalDateTime expirationTime;
}
