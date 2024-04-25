package com.dreamwing.controller;

import com.dreamwing.pojo.EmailRequest;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.EmailService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
}
