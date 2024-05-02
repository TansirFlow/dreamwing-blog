package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.AboutMapper;
import com.dreamwing.pojo.AboutVO;
import com.dreamwing.service.AboutService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AboutServiceImpl implements AboutService {
    @Autowired
    private AboutMapper aboutMapper;

    @Override
    public void add(String content) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        aboutMapper.add(content,id);
    }

    @Override
    public AboutVO getById(Integer id) {
        AboutVO aboutVO=aboutMapper.getById(id);
        if(aboutVO==null){
            throw new DreamWingRuntimeException("请勿非法访问");
        }
        return aboutVO;
    }

    @Override
    public List<AboutVO> list() {
        List<AboutVO> aboutVOList=aboutMapper.getAll();
        return aboutVOList;
    }

    @Override
    public void delete(Integer id) {
        aboutMapper.delete(id);
    }
}
