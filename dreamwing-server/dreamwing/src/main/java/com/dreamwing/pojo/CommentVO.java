package com.dreamwing.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {
    private Integer id;
    private Integer userId;
    private String commentUserName;
    private String content;
    private Integer parentCommentId;
    private Integer type;
    private Integer parentId;
    private Integer idDelete;
    private Integer idReview;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
