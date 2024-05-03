package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.CommentMapper;
import com.dreamwing.pojo.CommentDTO;
import com.dreamwing.pojo.CommentVO;
import com.dreamwing.service.CommentService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
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
}
