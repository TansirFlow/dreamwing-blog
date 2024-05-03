package com.dreamwing.service;

import com.dreamwing.pojo.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    String upload(String bucketName, MultipartFile file);

    Attachment getById(Integer id);

    void delete(Integer id);

    List<Attachment> list();
}
