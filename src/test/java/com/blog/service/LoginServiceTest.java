package com.blog.service;

import com.blog.vo.Loginer;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import com.blog.util.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserMapper mockUserMapper;

    @InjectMocks
    private LoginService loginServiceUnderTest;
   /*正常登录*/
    @Test
    void testUserLogin_success() {
        // Setup
        final Loginer loginer = new Loginer("email", "password");

        // Configure UserMapper.findByEmail(...).
        final User user = new User();
        user.setUserId(0L);
        user.setAccount("account");
        user.setNickName("nickName");
        user.setPassword(SecurityUtils.encodePassword("password"));
        user.setEmail("email");
        when(mockUserMapper.findByEmail("email")).thenReturn(user);

        // Run the test
        boolean userLogin = loginServiceUnderTest.userLogin(loginer);
        // Verify the results
        assertThat(userLogin).isTrue();

    }
    /*用户不存在*/
    @Test
    void testUserLogin_withWrongEmail() {
        // Setup
        // Setup
        final Loginer loginer = new Loginer("email1", "password");

        // Configure UserMapper.findByEmail(...).
        final User user = new User();
        user.setUserId(0L);
        user.setAccount("account");
        user.setNickName("nickName");
        user.setPassword(SecurityUtils.encodePassword("password"));
        user.setEmail("email");
        when(mockUserMapper.findByEmail("email")).thenReturn(user);

        // Run the test
        boolean userLogin = loginServiceUnderTest.userLogin(loginer);
        assertThat(userLogin).isTrue();

    }
    /*密码不对*/
    @Test
    void testUserLogin_withWrongPassword() {
        // Setup
        final Loginer loginer = new Loginer("email", "password1");

        // Configure UserMapper.findByEmail(...).
        final User user = new User();
        user.setUserId(0L);
        user.setAccount("account");
        user.setNickName("nickName");
        user.setPassword(SecurityUtils.encodePassword("password"));
        user.setEmail("email");
        when(mockUserMapper.findByEmail("email")).thenReturn(user);

        // Run the test
        boolean userLogin = loginServiceUnderTest.userLogin(loginer);
        assertThat(userLogin).isTrue();


    }
    /*用户为空*/
    @Test
    void testUserLogin_withEmptyEmail() {
        // Setup
        final Loginer loginer = new Loginer("", "password");

        // Configure UserMapper.findByEmail(...).
        final User user = new User();
        user.setUserId(0L);
        user.setAccount("account");
        user.setNickName("nickName");
        user.setPassword(SecurityUtils.encodePassword("password"));
        user.setEmail("email");
        when(mockUserMapper.findByEmail("email")).thenReturn(user);

        // Run the test
        boolean userLogin = loginServiceUnderTest.userLogin(loginer);
        assertThat(userLogin).isTrue();


    }
    /*密码为空*/

    @Test
    void testUserLogin_withEmptyPassword() {
        // Setup
        final Loginer loginer = new Loginer("email", "");

        // Configure UserMapper.findByEmail(...).
        final User user = new User();
        user.setUserId(0L);
        user.setAccount("account");
        user.setNickName("nickName");
        user.setPassword(SecurityUtils.encodePassword("password"));
        user.setEmail("email");
        when(mockUserMapper.findByEmail("email")).thenReturn(user);

        // Run the test
        boolean userLogin = loginServiceUnderTest.userLogin(loginer);
        assertThat(userLogin).isTrue();


    }
}
