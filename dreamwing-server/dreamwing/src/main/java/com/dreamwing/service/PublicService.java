package com.dreamwing.service;

import com.dreamwing.pojo.*;

import java.util.List;

public interface PublicService {
    PageBean<ArticleVO> getArticleList(Integer pageNum, Integer pageSize);

    List<CategoryVO> getCategoryList();

    ArticleVO getArticleById(Integer id);

    List<CommentVO> getCommentByArticleId(Integer articleId);

    List<TagVO> getTagListByArticleId(Integer articleId);
}
