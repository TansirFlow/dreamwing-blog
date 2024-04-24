package com.dreamwing.config;

import com.dreamwing.pojo.MinioPojo;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Autowired
    private MinioPojo minioPojo;
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioPojo.getUrl())  //传入url地址
                //传入用户名和密码
                .credentials(minioPojo.getUsername(), minioPojo.getPassword())
                .build();  //完成MinioClient的初始化
    }
}
