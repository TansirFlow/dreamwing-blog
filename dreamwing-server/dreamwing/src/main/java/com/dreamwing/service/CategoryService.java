package com.dreamwing.service;

import com.dreamwing.pojo.CategoryDTO;
import com.dreamwing.pojo.CategoryVO;

import java.util.List;

public interface CategoryService {
    void add(CategoryDTO categoryDTO);

    CategoryVO getById(Integer id);

    List<CategoryVO> list();

    void delete(Integer id);
}
