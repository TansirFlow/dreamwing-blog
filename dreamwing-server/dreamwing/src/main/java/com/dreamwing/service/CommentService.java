package com.dreamwing.service;

import com.dreamwing.pojo.CommentDTO;
import com.dreamwing.pojo.CommentVO;

import java.util.List;

public interface CommentService {
    void add(CommentDTO commentDTO);

    CommentVO getById(Integer id);

    List<CommentVO> list();

    void delete(Integer id);
}
