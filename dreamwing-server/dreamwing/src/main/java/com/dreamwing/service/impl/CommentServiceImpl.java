package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.CommentMapper;
import com.dreamwing.pojo.AuthorityVO;
import com.dreamwing.pojo.CommentDTO;
import com.dreamwing.pojo.CommentVO;
import com.dreamwing.pojo.RoleVO;
import com.dreamwing.service.CommentService;
import com.dreamwing.service.RoleService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

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


    @Override
    public void add(CommentDTO commentDTO) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        commentDTO.setUserId(id);
        commentDTO.setIdDelete(0);
        commentDTO.setIdReview(1);
        commentDTO.setCreateTime(LocalDateTime.now());
        commentMapper.add(commentDTO);
    }

    @Override
    public CommentVO getById(Integer id) {
        CommentVO commentVO=commentMapper.getById(id);
        if(commentVO==null){
            throw new DreamWingRuntimeException("评论id不存在，请勿非法请求");
        }
        return commentVO;
    }

    @Override
    public List<CommentVO> list() {
        List<CommentVO> commentVOList=commentMapper.getAll();
        return commentVOList;
    }

    @Override
    public void delete(Integer id) {
        commentMapper.delete(id);
    }

    @Override
    public List<CommentVO> getCommentByArticleId(Integer articleId) {
        return commentMapper.getByArticleId(articleId);
    }

    @Override
    public List<CommentVO> getByArticleIdForAdmin(Integer articleId) {
        lookAuthority("评论管理");
        return commentMapper.getByArticleIdForAdmin(articleId);
    }
}
