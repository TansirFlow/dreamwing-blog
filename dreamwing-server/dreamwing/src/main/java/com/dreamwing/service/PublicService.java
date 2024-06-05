package com.dreamwing.service;

import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.pojo.CategoryVO;
import com.dreamwing.pojo.PageBean;

import java.util.List;

public interface PublicService {
    PageBean<ArticleVO> getArticleList(Integer pageNum, Integer pageSize);

    List<CategoryVO> getCategoryList();
}
