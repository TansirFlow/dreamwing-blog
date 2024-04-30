package com.dreamwing.service;

import com.dreamwing.pojo.Article;
import com.dreamwing.pojo.ArticleVO;

import java.util.List;

public interface ArticleService {
    void add(Article article);
    Article getById(Integer id);

    List<ArticleVO> getList();
}
