package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.NoticeMapper;
import com.dreamwing.pojo.NoticeDTO;
import com.dreamwing.pojo.NoticeVO;
import com.dreamwing.service.NoticeService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public void add(NoticeDTO noticeDTO) {
        noticeDTO.setCreateTime(LocalDateTime.now());
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        noticeDTO.setCreateUser(id);
        noticeMapper.add(noticeDTO);
    }

    @Override
    public void update(NoticeDTO noticeDTO) {
        noticeDTO.setUpdateTime(LocalDateTime.now());
        noticeMapper.update(noticeDTO);
    }

    @Override
    public NoticeVO getById(Integer id) {
        NoticeVO noticeVO=noticeMapper.getById(id);
        if(noticeVO==null){
            throw new DreamWingRuntimeException("通知id不存在，请勿非法请求");
        }
        return noticeVO;
    }

    @Override
    public List<NoticeVO> getAll() {
        List<NoticeVO> noticeVOList=noticeMapper.getAll();
        return noticeVOList;
    }
}
