package com.dreamwing.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer createUser;
}
