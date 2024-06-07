package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.PublicMapper;
import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.pojo.CategoryVO;
import com.dreamwing.pojo.PageBean;
import com.dreamwing.pojo.TagVO;
import com.dreamwing.service.PublicService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {
    @Autowired
    private PublicMapper publicMapper;
    @Override
    public PageBean<ArticleVO> getArticleList(Integer pageNum, Integer pageSize) {
        PageBean<ArticleVO> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Page<ArticleVO> p = (Page<ArticleVO>) publicMapper.getArticleList();
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        for (int i = 0; i < pb.getItems().size(); ++i) {
            pb.getItems().get(i).setTagList(getTagListByArticleId(pb.getItems().get(i).getId()));
        }
        return pb;
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        List<CategoryVO> categoryVOList=publicMapper.getCategoryList();
        return categoryVOList;
    }

    @Override
    public ArticleVO getArticleById(Integer id) {
        ArticleVO articleVO = publicMapper.getArticleById(id);
        if (articleVO == null) throw new DreamWingRuntimeException("文章不存在");
        articleVO.setPassword(null);
        return articleVO;
    }

    public List<TagVO> getTagListByArticleId(Integer articleId) {
        List<TagVO> tagVOList = publicMapper.getTagListByArticleId(articleId);
        return tagVOList;
    }
}
