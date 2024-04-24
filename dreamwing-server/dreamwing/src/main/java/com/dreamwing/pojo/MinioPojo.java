package com.dreamwing.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioPojo {
    private String url;
    private String username;
    private String password;
    private String bucketName;
}
