package com.blog.controller;

import com.blog.service.MailService;
import com.blog.service.UserService;

import com.blog.util.bo.HttpSessionBO;
import com.blog.vo.Loginer;
import com.blog.vo.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("mailService")
    private MailService mailService;
    @Autowired
    private HttpSession session;

    private HttpSessionBO sessionBO;


    @PostMapping("/register")
    public void register(@RequestBody @Validated Register register){
        userService.userRegister(register,sessionBO);
    }
    @GetMapping("/email_code")
    @ResponseBody
    public void getCode(String email){
        sessionBO = HttpSessionBO.getHttpSessionBO(session,email);
        mailService.sendCodeMailMessage(email, sessionBO);
    }

    @GetMapping("/login")
    @ResponseBody
    public void login(@Validated Loginer loginer){
        userService.userLogin(loginer);

    }

}
