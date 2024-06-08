package com.dreamwing.service;

import com.dreamwing.pojo.Attachment;
import com.dreamwing.pojo.AttachmentGetListDataDTO;
import com.dreamwing.pojo.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    String upload(String bucketName, MultipartFile file);

    Attachment getById(Integer id);

    void delete(Integer id);

    PageBean<Attachment> getListByCondition(AttachmentGetListDataDTO attachmentGetListDataDTO);

    List<String> getAttachmentTypeList();
}
