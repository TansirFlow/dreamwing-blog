package com.dreamwing.service.impl;

import com.dreamwing.constants.ArticleConstants;
import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.ArticleMapper;
import com.dreamwing.pojo.Article;
import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.service.ArticleService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        article.setUserId(id);
        if(article.getCategoryId()==null)article.setCategoryId(ArticleConstants.DEFAULT_ARTICLE_CATEGORY_ID);
        article.setIsDelete(ArticleConstants.ARTICLE_NOT_DELETE);
        article.setLookNum(0);
        article.setCreateTime(LocalDateTime.now());
        articleMapper.add(article);
    }

    @Override
    public Article getById(Integer id) {
        Article article=articleMapper.getById(id);
        if(article==null)throw new DreamWingRuntimeException("文章不存在");
        article.setPassword(null);
        return article;
    }

    @Override
    public List<ArticleVO> getList() {
        List<ArticleVO> list=new ArrayList<>();
        list=articleMapper.getList();
        return list;
    }
}
