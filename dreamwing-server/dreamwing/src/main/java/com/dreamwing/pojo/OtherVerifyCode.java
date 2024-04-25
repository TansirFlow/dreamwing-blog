package com.dreamwing.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtherVerifyCode {
    private String username;
    private String type;
    private String code;
    private LocalDateTime expirationTime;
}
