package com.dreamwing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {
    private Integer id;
    private Integer userId;
    private String commentUserName;
    private String commentUserAvatar;
    private String content;
    private Integer parentCommentId;
    private Integer type;
    private Integer parentId;
    private Integer isReview;
    private Integer isDelete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;
}
