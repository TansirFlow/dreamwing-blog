package com.dreamwing.service.impl;

import com.dreamwing.mapper.RegisterVerifyCodeMapper;
import com.dreamwing.pojo.EmailPojo;
import com.dreamwing.pojo.EmailRequest;
import com.dreamwing.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailPojo emailPojo;
    @Autowired
    private RegisterVerifyCodeMapper emailMapper;

    public void sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailPojo.getUsername());
        mailMessage.setTo(emailRequest.getRecipient());
        mailMessage.setText(emailRequest.getMsgBody());
        mailMessage.setSubject(emailRequest.getSubject() != null ? emailRequest.getSubject() : "DreamWing博客系统通知");
        System.out.println("准备发送");
        javaMailSender.send(mailMessage);
        System.out.println("发送完毕");
    }
}
