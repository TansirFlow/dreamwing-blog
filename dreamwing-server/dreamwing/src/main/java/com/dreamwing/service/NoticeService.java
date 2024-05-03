package com.dreamwing.service;

import com.dreamwing.pojo.NoticeDTO;
import com.dreamwing.pojo.NoticeVO;

import java.util.List;

public interface NoticeService {
    void add(NoticeDTO noticeDTO);

    void update(NoticeDTO noticeDTO);

    NoticeVO getById(Integer id);

    List<NoticeVO> getAll();
}
