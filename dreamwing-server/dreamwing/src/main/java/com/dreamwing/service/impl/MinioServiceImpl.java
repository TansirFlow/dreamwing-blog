package com.dreamwing.service.impl;

import com.dreamwing.pojo.MinioPojo;
import com.dreamwing.service.MinioService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioPojo minioPojo;

    public String uploadImage(String bucketName, MultipartFile file) {
        try {
            //判断桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                //如果不存在，就创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            //本地时间，具体到年、月、日
            String uuid= UUID.randomUUID().toString();
            String filename= UUID.randomUUID() +file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //加一个/表示创建一个文件夹
            minioClient.putObject(PutObjectArgs.builder().
                    bucket(bucketName).
                    object(filename).
                    stream(file.getInputStream(), file.getSize(), -1).//文件上传的类型，如果不指定，那么每次访问时都要先下载文件
                            contentType(file.getContentType()).
                    build());
            String url = minioPojo.getUrl() + "/" + minioPojo.getBucketName() + "/" + filename;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
    }

}
