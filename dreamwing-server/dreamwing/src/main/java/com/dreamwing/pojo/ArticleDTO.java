package com.dreamwing.pojo;

import com.dreamwing.anno.ArticleState;
import com.dreamwing.anno.ArticleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDTO {
    private Integer id;
    private Integer userId;
    private Integer categoryId;
    private String articleCover;

    @NotEmpty(groups = {AddArticleGroup.class})
    private String articleTitle;

    private String articleAbstract;

    @NotEmpty(groups = {AddArticleGroup.class})
    private String articleContent;

    private Integer isDelete;

    @ArticleState(groups = {AddArticleGroup.class})
    @NotNull(groups = {AddArticleGroup.class})
    private Integer status;

    @ArticleType(groups = {AddArticleGroup.class})
    @NotNull(groups = {AddArticleGroup.class})
    private Integer type;

    private Integer lookNum;

    private String password;
    private String originUrl;
    LocalDateTime createTime;
    LocalDateTime updateTime;

    private List<TagVO> tagList;

    public interface AddArticleGroup {
    }
}
