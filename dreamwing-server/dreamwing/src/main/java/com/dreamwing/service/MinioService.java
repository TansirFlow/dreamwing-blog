package com.dreamwing.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String uploadImage(String bucketName, MultipartFile file);
}
