package com.dreamwing.service.impl;

import com.dreamwing.constants.ArticleConstants;
import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.ArticleMapper;
import com.dreamwing.pojo.*;
import com.dreamwing.service.ArticleService;
import com.dreamwing.service.RoleService;
import com.dreamwing.service.TagService;
import com.dreamwing.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private RoleService roleService;

    /**
     * 看看是否有权限管理文章
     * @param authority
     */
    public void lookAuthority(String authority){
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        RoleVO roleVO=roleService.getRoleByUserId(userId);
        List<AuthorityVO> authorityVOList=roleService.getAuthorityByRoleId(roleVO.getId());
        boolean flag=false;
        for(int i=0;i<authorityVOList.size();++i) {
            if(Objects.equals(authorityVOList.get(i).getName(), authority)) {
                flag=true;
            }
        }
        if(!flag){
            throw new DreamWingRuntimeException("您无权访问该接口！");
        }
    }

    /**
     * 增加文章
     *
     * @param articleDTO
     */
    @Override
    public void add(ArticleDTO articleDTO) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        articleDTO.setUserId(id);
        if (articleDTO.getCategoryId() == null) articleDTO.setCategoryId(ArticleConstants.DEFAULT_ARTICLE_CATEGORY_ID);
        articleDTO.setIsDelete(ArticleConstants.ARTICLE_NOT_DELETE);
        articleDTO.setLookNum(0);
        articleDTO.setCreateTime(LocalDateTime.now());
        System.out.println(articleDTO);
        Integer articleId = articleMapper.add(articleDTO);
        List<TagVO> tagList = articleDTO.getTagList();
        String[] tagNameList = new String[tagList.size()];
        for (int i = 0; i < tagList.size(); ++i) {
            tagNameList[i] = tagList.get(i).getName();
        }
        setTagList(articleId, tagNameList);
    }

    /**
     * 根据id查询文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ArticleVO getById(Integer id) {
        ArticleVO articleVO = articleMapper.getById(id);
        if (articleVO == null) throw new DreamWingRuntimeException("文章不存在");
        articleVO.setTagList(getTagListByArticleId(id));
        return articleVO;
    }

    /**
     * 查询文章列表（已废弃）
     *
     * @return
     */
    @Override
    public List<ArticleVO> getList() {
        List<ArticleVO> list = new ArrayList<>();
        list = articleMapper.getList();
        return list;
    }

    /**
     * 分页查询文章列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override   //条件分页列表查询
    public PageBean<ArticleVO> list(Integer pageNum, Integer pageSize) {
        PageBean<ArticleVO> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Page<ArticleVO> p = (Page<ArticleVO>) articleMapper.getList();
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        for (int i = 0; i < pb.getItems().size(); ++i) {
            pb.getItems().get(i).setTagList(getTagListByArticleId(pb.getItems().get(i).getId()));
        }
        return pb;
    }

    /**
     * 修改文章的tag绑定
     *
     * @param articleId
     * @param tagList
     */
    @Override
    public void setTagList(Integer articleId, String[] tagList) {
        Set<String> toAddTagSet = new HashSet<>();
        Set<String> toDeleteSet = new HashSet<>();
        List<TagVO> tagVOList = getTagListByArticleId(articleId);
        Set<String> alreadyHasList = new HashSet<>();
        for (int i = 0; i < tagVOList.size(); ++i) {
            alreadyHasList.add(tagVOList.get(i).getName());
            toDeleteSet.add(tagVOList.get(i).getName());
        }
        for (int i = 0; i < tagList.length; ++i) {
            toAddTagSet.add(tagList[i]);
        }
        toDeleteSet.removeAll(toAddTagSet);//从已经存在的中去掉需要添加的不用删除的
        toAddTagSet.removeAll(alreadyHasList);//从所有的要添加的中去掉已经存在的不用添加的
        List<Integer> toDeleteTagIdList = new ArrayList<>();//要删除的tag绑定的tagVO对象，为了id所以用TagVO
        List<String> toDeleteList = new ArrayList<>(toDeleteSet);//需要删除的tag名列表
        for (int i = 0; i < toDeleteList.size(); ++i) {
            for (int j = 0; j < tagVOList.size(); ++j) {
                if (Objects.equals(tagVOList.get(j).getName(), toDeleteList.get(i))) {
                    toDeleteTagIdList.add(tagVOList.get(j).getId());
                    break;
                }
            }
        }

        if (!toDeleteTagIdList.isEmpty()) {//如果存在需要删除的Tag
            deleteByArticleIdAndTagList(articleId, toDeleteTagIdList);
        }

        if (!toAddTagSet.isEmpty()) {//如果存在需要添加的Tag
            System.out.println(toAddTagSet);
            tagService.tryAddTag(new ArrayList<>(toAddTagSet));
            List<TagVO> tagVOListResult = tagService.getTagVOListByTagNameList(new ArrayList<>(toAddTagSet));
            addTagListToArticle(articleId, tagVOListResult);
        }
    }

    @Override
    public void updateArticle(ArticleDTO articleDTO) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        if (articleDTO.getCategoryId() == null) articleDTO.setCategoryId(ArticleConstants.DEFAULT_ARTICLE_CATEGORY_ID);
        articleDTO.setIsDelete(ArticleConstants.ARTICLE_NOT_DELETE);
        articleDTO.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticle(articleDTO,userId);
        List<TagVO> tagList = articleDTO.getTagList();
        String[] tagNameList = new String[tagList.size()];
        for (int i = 0; i < tagList.size(); ++i) {
            tagNameList[i] = tagList.get(i).getName();
        }
        setTagList(articleDTO.getId(), tagNameList);
    }

    @Override
    public void updateArticleForAdmin(ArticleDTO articleDTO) {
        lookAuthority("管理文章");
        if (articleDTO.getCategoryId() == null) articleDTO.setCategoryId(ArticleConstants.DEFAULT_ARTICLE_CATEGORY_ID);
        articleDTO.setIsDelete(ArticleConstants.ARTICLE_NOT_DELETE);
        articleDTO.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticleForAdmin(articleDTO);
        List<TagVO> tagList = articleDTO.getTagList();
        String[] tagNameList = new String[tagList.size()];
        for (int i = 0; i < tagList.size(); ++i) {
            tagNameList[i] = tagList.get(i).getName();
        }
        setTagList(articleDTO.getId(), tagNameList);
    }

    @Override
    public void deleteArticleById(Integer id) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer thisUserId = (Integer) claims.get("id");
        articleMapper.deleteArticleById(id,thisUserId);
    }

    @Override
    public void deleteArticleByIdForAdmin(Integer id) {
        lookAuthority("管理文章");
        articleMapper.deleteArticleByIdForAdmin(id);
    }

    @Override
    public PageBean<ArticleVO> getListByCondition(ArticleGetListDataDTO articleGetListDataDTO) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        PageBean<ArticleVO> pb = new PageBean<>();
        PageHelper.startPage(articleGetListDataDTO.getPageNum(), articleGetListDataDTO.getPageSize());
        Page<ArticleVO> p = (Page<ArticleVO>) articleMapper.getListByCondition(articleGetListDataDTO,userId);
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        for (int i = 0; i < pb.getItems().size(); ++i) {
            pb.getItems().get(i).setTagList(getTagListByArticleId(pb.getItems().get(i).getId()));
        }
        return pb;
    }

    @Override
    public void deleteArticleByIdList(List<Integer> idList) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        articleMapper.deleteArticleByIdList(idList,userId);
    }

    @Override
    public void deleteArticleByIdListForAdmin(List<Integer> idList) {
        lookAuthority("管理文章");
        articleMapper.deleteArticleByIdListForAdmin(idList);
    }

    @Override
    public PageBean<ArticleVO> getListByConditionForAdmin(ArticleGetListDataDTO articleGetListDataDTO) {
        lookAuthority("管理文章");
        PageBean<ArticleVO> pb = new PageBean<>();
        PageHelper.startPage(articleGetListDataDTO.getPageNum(), articleGetListDataDTO.getPageSize());
        Page<ArticleVO> p = (Page<ArticleVO>) articleMapper.getListByConditionForAdmin(articleGetListDataDTO);
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        for (int i = 0; i < pb.getItems().size(); ++i) {
            pb.getItems().get(i).setTagList(getTagListByArticleId(pb.getItems().get(i).getId()));
        }
        return pb;
    }

    /**
     * 给文章绑定Tag
     *
     * @param articleId
     * @param tagList
     */
    public void addTagListToArticle(Integer articleId, List<TagVO> tagList) {
        articleMapper.addTagListToArticle(articleId, tagList);
    }

    /**
     * 根据文章id获取文章的tag
     *
     * @param articleId
     * @return
     */
    public List<TagVO> getTagListByArticleId(Integer articleId) {
        List<TagVO> tagVOList = articleMapper.getTagListByArticleId(articleId);
        return tagVOList;
    }

    /**
     * 删除指定文章指定tag列表的tag
     *
     * @param articleId
     * @param tagList
     */
    public void deleteByArticleIdAndTagList(Integer articleId, List<Integer> tagList) {
        articleMapper.deleteByArticleIdAndTagList(articleId, tagList);
    }
}
