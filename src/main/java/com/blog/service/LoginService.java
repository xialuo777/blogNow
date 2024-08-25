package com.blog.service;

import com.blog.vo.Loginer;
import com.blog.entity.User;
import com.blog.enums.ErrorCode;
import com.blog.exception.BusinessException;
import com.blog.mapper.UserMapper;
import com.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;
    public boolean userLogin(Loginer loginer) {
        String email = loginer.getEmail();
        String password = loginer.getPassword();
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或者密码为空");
        }
        User user = new User();
        try {
            user = userMapper.findByEmail(email);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        boolean loginFlag = SecurityUtils.checkPassword(password, user.getPassword());
        if (!loginFlag){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误,请重新输入");
        }
        return loginFlag;


    }


}
