package com.dreamwing.service;

import com.dreamwing.pojo.AboutVO;

import java.util.List;

public interface AboutService {
    void add(String content);

    AboutVO getById(Integer id);

    List<AboutVO> list();

    void delete(Integer id);
}
