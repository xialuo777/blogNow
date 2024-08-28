package com.blog.controller;

import com.blog.BlogNowApplication;
import com.blog.mapper.UserMapper;
import com.blog.service.MailService;
import com.blog.service.UserService;
import com.blog.util.bo.HttpSessionBO;
import com.blog.vo.Loginer;
import com.blog.vo.Register;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Transactional
@SpringBootTest(classes = BlogNowApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class UserControllerTest2 {

    @Autowired
    private MockMvc mockMvc;
    private UserMapper userMapper;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private MailService mockMailService;



    @Test
    @Sql(statements = "insert into users (user_id, account, nick_name, password, email, phone) " +
            "values (123414131L,'accountTest','SuperMan','passwordTest','2436056388@qq.com','18539246184')",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testRegister() throws Exception {

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"accountTest\",\"nickName\":\"SuperMan\",\"password\":\"passwordTest\",\"checkPassword\":\"passwordTest\",\"email\":\"2436056388@qq.com\",\"emailCode\":\"tested\",\"phone\":\"18539246184\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        final Register register = new Register();
        register.setAccount("accountTest");
        register.setNickName("SuperMan");
        register.setPassword("passwordTest");
        register.setCheckPassword("passwordTest");
        register.setEmail("2436056388@qq.com");
        register.setPhone("18539246184");
        register.setEmailCode("tested");

        verify(mockUserService).userRegister(register, new HttpSessionBO<>("2436056388@qq.com","tested"));
    }

    @Test
    void testGetCode() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/users/email_code")
                        .param("email", "email")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockMailService).sendCodeMailMessage("email", new HttpSessionBO<>("email", "code"));
    }

    @Test
    void testLogin() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/users/login")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockUserService).userLogin(new Loginer("email", "password"));
    }
}
