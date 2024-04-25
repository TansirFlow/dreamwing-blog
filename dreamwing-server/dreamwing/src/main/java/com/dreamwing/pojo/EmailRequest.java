package com.dreamwing.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
public class EmailRequest {
    // Class data members
    @NotEmpty
    private String recipient;//接收方
    @NotEmpty
    private String msgBody;//邮件内容
    private String subject;//主题
}
