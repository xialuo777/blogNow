package com.blog.controller;
import com.blog.service.MailService;
import com.blog.service.UserService;
import com.blog.util.bo.HttpSessionBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMybatis
@RunWith(SpringRunner.class)
@Import({MailService.class, UserService.class})
class UserControllerTest {
    @MockBean
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HttpSessionBO sessionBO;

    @BeforeEach
    void setUp() {
        sessionBO = new HttpSessionBO<>("2436056388@qq.com","tested");
    }
    @Test
    @Sql(statements = "insert into users (user_id, account, nick_name, password, email, phone) " +
            "values ('123414131L','accountTest','SuperMan','passwordTest','2436056388@qq.com','18539246184')",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testRegister() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"accountTest\",\"nickName\":\"SuperMan\",\"password\":\"passwordTest\",\"checkPassword\":\"passwordTest\",\"email\":\"2436056388@qq.com\",\"emailCode\":\"tested\",\"phone\":\"18539246184\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCode() throws Exception {
        mockMvc.perform(get("/users/email_code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"2436056388@qq.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
