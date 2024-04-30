package com.dreamwing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleVO {
    private Integer id;
    private Integer userId;
    private String author; //作者
    private Integer categoryId;
    private String categoryName;
    private String articleCover;
    private String articleTitle;
    private String articleAbstract;
    private String articleContent;
    private Integer isDelete;
    private Integer status;
    private Integer type;
    private Integer lookNum;
    private String password;
    private String originUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;
}
