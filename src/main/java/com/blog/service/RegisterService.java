package com.blog.service;

import com.blog.enums.ErrorCode;
import com.blog.exception.BusinessException;
import com.blog.util.LogUtil;
import com.blog.vo.Register;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import com.blog.util.SecurityUtils;
import com.blog.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * TODO :异常处理,验证码超时处理
 */
@Service
@Validated
public class RegisterService {
    @Autowired
    private UserMapper userMapper;


    /**
    *  @Transactional 如果事务抛出异常，则进行回滚
     * @Description 注册新用户
     * @Param register
     * @Param session
     * @Return User
     *
    */
    @Transactional(rollbackFor = Exception.class)
    public User userRegister(Register register,HttpSession session){
        String account = register.getAccount();
        LogUtil.logInfo("开始注册新用户："+account);
        String nickName = register.getNickName();
        String password = register.getPassword();
        String checkPassword = register.getCheckPassword();
        String email = register.getEmail();
        String phone = register.getPhone();
        String emailCode = register.getEmailCode().trim();

        /*验证两次输入密码是否一致*/
        if (!checkPassword.equals(password)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致，请重新输入");
        }


        /*验证邮箱验证码输入是否正确*/
        Optional<String> codeSessionOpt = Optional.ofNullable((String) session.getAttribute("code"));
        Optional<String> emailSessionOpt = Optional.ofNullable((String) session.getAttribute("email"));
        if (!codeSessionOpt.isPresent()||!emailSessionOpt.isPresent()){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"会话数据丢失，请重新发送请求");
        }
        String codeSession = codeSessionOpt.get();
        String emailSession = emailSessionOpt.get();

        if (!codeSession.equals(emailCode) || !emailSession.equals(register.getEmail())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请确认输入的邮箱及邮箱验证码是否正确");
        }


        /*封装用户*/
        User user = new User();
        Long userId = SnowFlakeUtil.getID();
        user.setUserId(userId);
        user.setAccount(account);
        user.setNickName(nickName);
        String BCPassword = SecurityUtils.encodePassword(password);
        user.setPassword(BCPassword);
        user.setEmail(email);
        user.setPhone(phone);
        /*添加用户到数据库*/
        userMapper.insertUser(user);
        return user;
    }
}
