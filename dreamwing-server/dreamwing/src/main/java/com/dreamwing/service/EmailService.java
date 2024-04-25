package com.dreamwing.service;

import com.dreamwing.pojo.EmailRequest;

public interface EmailService {
    void sendEmail(EmailRequest emailRequest);
}
