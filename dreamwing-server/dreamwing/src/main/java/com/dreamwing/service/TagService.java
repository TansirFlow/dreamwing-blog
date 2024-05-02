package com.dreamwing.service;

import com.dreamwing.pojo.TagVO;

import java.util.List;

public interface TagService {
    void add(String name);

    TagVO getById(Integer id);

    List<TagVO> list();
}
