package com.dreamwing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer id;
    private Integer userId;
    private String content;
    private Integer parentCommentId;
    private Integer type;
    private Integer parentId;
    private Integer idDelete;
    private Integer idReview;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
