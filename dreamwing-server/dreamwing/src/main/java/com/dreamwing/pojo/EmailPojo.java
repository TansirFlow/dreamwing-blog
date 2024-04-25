package com.dreamwing.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

@ConfigurationProperties(prefix = "spring.mail")
@Component
@Data
public class EmailPojo {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Boolean auth;
    private Boolean starttls;
    private Properties properties;
}
