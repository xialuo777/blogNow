package com.blog.controller;

import com.blog.entity.User;
import com.blog.service.MailService;
import com.blog.service.UserService;

import com.blog.util.bo.HttpSessionBO;
import com.blog.vo.Loginer;
import com.blog.vo.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
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
        mailService.sendCodeMailMessage(sessionBO);
    }

    @GetMapping("/login")
    @ResponseBody
    public void login(@Validated Loginer loginer){
        session.setAttribute("email", loginer.getEmail());
        userService.userLogin(loginer);

    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam String email) {
        userService.deleteUserByEmail(email);
    }
    @GetMapping("/profile")
    public String profile() {
        String email = (String) session.getAttribute("email");
        User user = userService.selectUserByEmail(email);
        session.setAttribute("path", "profile");
        session.setAttribute("account", user.getAccount());
        session.setAttribute("nickName", user.getNickName());
        session.setAttribute("phone", user.getPhone());
        return "users/profile";
    }
    @PutMapping("/profile/email")
    public void updateEmail(@RequestParam String email,@Email @RequestParam String newEmail,@RequestParam(required = false) String inputCode) {
        User user = userService.selectUserByEmail(email);
        sessionBO = HttpSessionBO.getHttpSessionBO(session,email);
        String code = (String) sessionBO.getCode();
        mailService.sendTextMailMessage(newEmail, "修改绑定邮箱验证码", code);
        if (inputCode.equals(code)){
            userService.updateEmail(user,newEmail);
        }
    }

    @PutMapping("/profile/nick_name")
    public void updateNickName(@RequestParam String email, @RequestParam String newNickName) {
        userService.updateNickName(email,newNickName);
    }

    @PutMapping("/profile/phone")
    public void updateAccount(@RequestParam String email, @RequestParam String phone) {
        userService.updatePhone(email,phone);
    }
    @PutMapping("/profile/password")
    public void updatePassword(@RequestParam String email, @RequestParam String password,@RequestParam String newPassword) {
        userService.updatePassword(email,password,newPassword);
    }



}
