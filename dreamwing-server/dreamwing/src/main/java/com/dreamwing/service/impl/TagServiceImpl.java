package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.TagMapper;
import com.dreamwing.pojo.TagVO;
import com.dreamwing.service.TagService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public void add(String name) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        tagMapper.add(name,id);
    }

    @Override
    public TagVO getById(Integer id) {
        TagVO tagVO=tagMapper.getById(id);
        if(tagVO==null){
            throw new DreamWingRuntimeException("tag id不存在");
        }
        return tagVO;
    }

    @Override
    public List<TagVO> list() {
        List<TagVO> tagVOList=tagMapper.getAll();
        return tagVOList;
    }
}
