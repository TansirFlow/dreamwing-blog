package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.TagMapper;
import com.dreamwing.pojo.TagVO;
import com.dreamwing.service.TagService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 文章tag更新时尝试来添加tag，不存在的就添加这个tag，否则忽视操作
     * @param tagList
     */
    @Override
    public void tryAddTag(ArrayList<String> tagList) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        tagMapper.tryToAddTagList(id,tagList);
    }

    /**
     * 根据一个表的Tag名来获取TagVO对象列表
     * @param tagNameList
     * @return
     */
    @Override
    public List<TagVO> getTagVOListByTagNameList(List<String> tagNameList) {
        return tagMapper.getTagVOListByTagNameList(tagNameList);
    }
}
