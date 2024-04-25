package com.dreamwing.utils;

import com.dreamwing.constants.EmailConstants;
import com.dreamwing.pojo.EmailRequest;

public class EmailRequestUtil {
    public static EmailRequest getEmailRequest(
            String subject,
            String recipient,
            String msgBody
    ){
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject(subject);
        emailRequest.setRecipient(recipient);
        emailRequest.setMsgBody(msgBody);
        return emailRequest;
    }
}
