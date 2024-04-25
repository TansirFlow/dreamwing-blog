package com.dreamwing.controller;

import com.dreamwing.pojo.MinioPojo;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioPojo minioPojo;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        String url = minioService.uploadImage(minioPojo.getBucketName(), file);
        return Result.success(url);
    }
}
