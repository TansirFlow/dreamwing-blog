package com.dreamwing.service;

import com.dreamwing.pojo.ArticleDTO;
import com.dreamwing.pojo.ArticleGetListDataDTO;
import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.pojo.PageBean;

import java.util.List;

public interface ArticleService {
    void add(ArticleDTO articleDTO);
    ArticleVO getById(Integer id);

    List<ArticleVO> getList();

    //条件分页列表查询
    PageBean<ArticleVO> list(Integer pageNum, Integer pageSize);

    void setTagList(Integer articleId, String[] tagList);

    void updateArticle(ArticleDTO articleDTO);

    void deleteArticleById(Integer id);

    PageBean<ArticleVO> getListByCondition(ArticleGetListDataDTO articleGetListDataDTO);

    void deleteArticleByIdList(List<Integer> idList);

    PageBean<ArticleVO> getListByConditionForAdmin(ArticleGetListDataDTO articleGetListDataDTO);

    void updateArticleForAdmin(ArticleDTO articleDTO);

    void deleteArticleByIdForAdmin(Integer id);

    void deleteArticleByIdListForAdmin(List<Integer> idList);
}
