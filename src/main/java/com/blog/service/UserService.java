package com.blog.service;

import com.blog.entity.User;
import com.blog.enums.ErrorCode;
import com.blog.exception.BusinessException;
import com.blog.mapper.UserMapper;
import com.blog.util.SecurityUtils;
import com.blog.util.SnowFlakeUtil;
import com.blog.util.bo.HttpSessionBO;
import com.blog.vo.Loginer;
import com.blog.vo.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @Descriptionn 用户登录
     * @param loginer
     * @Return boolean
     */
    public void userLogin( Loginer loginer) {
        String email = loginer.getEmail();
        String password = loginer.getPassword();
        User user = selectUserByEmail(email);
        boolean loginFlag = SecurityUtils.checkPassword(password, user.getPassword());
        if (!loginFlag){
            log.error("密码错误，登陆失败，请重新输入");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误,请重新输入");
        }
        log.info("用户{}登陆成功",user.getAccount());

    }

    /**
     * @Transactional 如果事务抛出异常，则进行回滚
     * @Description 注册新用户
     * @Param register
     * @Param session
     * @Return User
     *
     */
    @Transactional
    public void userRegister(@Validated Register register, HttpSessionBO sessionBO){
        String account = register.getAccount();
        log.info("开始注册新用户："+account);
        String nickName = register.getNickName();
        String password = register.getPassword();
        String checkPassword = register.getCheckPassword();
        String email = register.getEmail();
        String phone = register.getPhone();
        String emailCode = register.getEmailCode().trim();

        /*验证两次输入密码是否一致*/
        if (!checkPassword.equals(password)){
            log.error("两次输入密码不一致，注册失败：{}", account);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致，请重新输入");
        }

        /*验证邮箱验证码输入是否正确*/
        Optional<String> codeSessionOpt = Optional.ofNullable(sessionBO.getCode().toString());
        Optional<String> emailSessionOpt = Optional.ofNullable(sessionBO.getEmail().toString());
        String codeSession = codeSessionOpt.get();
        String emailSession = emailSessionOpt.get();
        if (!emailSession.equals(email)){
            log.error("邮箱输入错误，注册失败：{}", account);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱输入有误");
        }
        if (!codeSession.equals(emailCode)) {
            log.error("邮箱验证码输入错误，注册失败：{}", account);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请确认邮箱验证码是否正确");
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
        log.info("用户 {} 添加成功",account);

    }

    /**
     * @Description 根据用户邮箱查找用户
     * @Param email
     * @Return User
     */
    public User selectUserByEmail(String email){
        if (StringUtils.isEmpty(email)){
            log.error("输入邮箱不能为空");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入邮箱不能为空");
        }
        User user = userMapper.findByEmail(email);
        if (user==null){
            log.error("邮箱{}未注册，请注册",email);
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"邮箱未注册，请注册");
        }
        return user;
    }

    public void deleteUserByEmail(String email){
        if (StringUtils.isEmpty(email)){
            log.error("输入邮箱不能为空");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入邮箱不能为空");
        }
        User user = userMapper.findByEmail(email);
        if (user==null){
            log.error("邮箱{}未注册，无需删除",email);
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"邮箱未注册，无需删除");
        }
        userMapper.deleteByEmail(email);
        log.info("用户{}删除成功",email);
    }
    public void updateUser(User user){
        userMapper.updateUser(user);
    }
}