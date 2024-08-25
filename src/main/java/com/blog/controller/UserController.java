package com.blog.controller;

import com.blog.enums.ErrorCode;
import com.blog.exception.BusinessException;
import com.blog.exception.R;
import com.blog.service.LoginService;
import com.blog.util.LogUtil;
import com.blog.vo.Loginer;
import com.blog.vo.Register;
import com.blog.entity.User;
import com.blog.service.MailService;
import com.blog.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private MailService mailService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public R register(@RequestBody @Validated Register register, HttpSession session){
        User user = registerService.userRegister(register,session);
        if (user==null){
            LogUtil.logInfo("用户注册失败");
            return R.error(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMessage());
        }else {
            LogUtil.logInfo("用户注册成功");
            return R.ok(ErrorCode.SUCCESS.getMessage());
        }

    }
    @GetMapping("/email_code")
    @ResponseBody
    public R getCode(@Validated String email,HttpSession session){
        boolean flag = mailService.sendCodeMailMessage(email, session);
        if (flag){
            return R.ok(ErrorCode.SUCCESS.getMessage());
        }else {
            return R.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMessage());
        }

    }

    @GetMapping("/loginer")
    @ResponseBody
    public R login(Loginer loginer){
        try {
            loginService.userLogin(loginer);
            return R.ok(ErrorCode.SUCCESS.getMessage());
        }catch (Exception e){
            LogUtil.logError("用户登录失败",e);
            return R.error(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMessage());
        }

    }

}
