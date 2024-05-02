package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.CategoryMapper;
import com.dreamwing.pojo.CategoryDTO;
import com.dreamwing.pojo.CategoryVO;
import com.dreamwing.service.CategoryService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(CategoryDTO categoryDTO) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        categoryDTO.setCreateTime(LocalDateTime.now());
        categoryDTO.setCreateUser(id);
        categoryMapper.add(categoryDTO);
    }

    @Override
    public CategoryVO getById(Integer id) {
        System.out.println("分类id="+id);
        CategoryVO categoryVO=categoryMapper.getById(id);
        if(categoryVO==null){
            throw new DreamWingRuntimeException("分类id不存在");
        }
        return categoryVO;
    }

    @Override
    public List<CategoryVO> list() {
        List<CategoryVO> categoryVOList=categoryMapper.getAll();
        return categoryVOList;
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
