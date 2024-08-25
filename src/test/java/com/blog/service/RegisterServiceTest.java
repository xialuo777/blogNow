package com.blog.service;

import com.blog.util.SecurityUtils;
import com.blog.vo.Register;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private UserMapper mockUserMapper;
    @InjectMocks
    private RegisterService registerServiceUnderTest;


    private static MockHttpSession mockHttpSession;
    @BeforeAll
    public static void setupMockMvc() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("email", "2436056388@qq.com");
        mockHttpSession.setAttribute("code", "passcd");
    }



    @Test
    void testUserRegister() {

        final Register register = new Register();
        register.setAccount("123456");
        register.setNickName("juniTest");
        register.setPassword("123456789");
        register.setCheckPassword("123456789");
        register.setEmail("2436056388@qq.com");
        register.setPhone("18539246184");
        register.setEmailCode("passcd");


        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);


        assertThat(result).isNotNull();


        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }

    @Test
    void testUserRegister_withInvalidAccount() {
        // Setup
        final Register register = new Register();
        register.setAccount("junit");
        register.setNickName("juniTest");
        register.setPassword("123456789");
        register.setCheckPassword("123456789");
        register.setEmail("2436056388@qq.com");
        register.setPhone("18539246184");
        register.setEmailCode("passcd");



        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);


        assertThat(result).isNotNull();


        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }
    @Test
    void testUserRegister_withWrongPassword() {

        final Register register = new Register();
        register.setAccount("123456");
        register.setNickName("juniTest");
        register.setPassword("12345");
        register.setCheckPassword("12345");
        register.setEmail("2436056388@qq.com");
        register.setPhone("18539246184");
        register.setEmailCode("passcd");



        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);


        assertThat(result).isNotNull();


        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }
    @Test
    void testUserRegister_withWrongCheckPassword() {
        // Setup
        final Register register = new Register();
        register.setAccount("123456");
        register.setNickName("juniTest");
        register.setPassword("123456789");
        register.setCheckPassword("12345678");
        register.setEmail("2436056388@qq.com");
        register.setPhone("18539246184");
        register.setEmailCode("passcd");



        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);


        assertThat(result).isNotNull();


        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }
    @Test
    void testUserRegister_withWrongEmail() {

        final Register register = new Register();
        register.setAccount("123456");
        register.setNickName("juniTest");
        register.setPassword("123456789");
        register.setCheckPassword("123456789");
        register.setEmail("2436056388@qq.co");
        register.setPhone("18539246184");
        register.setEmailCode("passcd");



        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);


        assertThat(result).isNotNull();


        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }
    @Test
    void testUserRegister_withWrongPhone() {
        // Setup
        final Register register = new Register();
        register.setAccount("123456");
        register.setNickName("juniTest");
        register.setPassword("123456789");
        register.setCheckPassword("123456789");
        register.setEmail("2436056388@qq.com");
        register.setPhone("392464");
        register.setEmailCode("passcd");


        // Run the test
        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);

        // Verify the results
        assertThat(result).isNotNull();

        // Confirm UserMapper.insertUser(...).
        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }

    @Test
    void testUserRegister_withWrongEmailCode() {
        // Setup
        final Register register = new Register();
        register.setAccount("123456");
        register.setNickName("juniTest");
        register.setPassword("123456789");
        register.setCheckPassword("123456789");
        register.setEmail("2436056388@qq.com");
        register.setPhone("18539246184");
        register.setEmailCode("pass");


        // Run the test
        final User result = registerServiceUnderTest.userRegister(register, mockHttpSession);

        // Verify the results
        assertThat(result).isNotNull();

        // Confirm UserMapper.insertUser(...).
        final User user = new User();
        long id = result.getUserId();
        user.setUserId(id);
        user.setAccount(result.getAccount());
        user.setNickName(result.getNickName());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        verify(mockUserMapper).insertUser(user);
    }
}
