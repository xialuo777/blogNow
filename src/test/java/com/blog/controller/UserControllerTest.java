package com.blog.controller;
import com.blog.service.MailService;
import com.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
class UserControllerTest {

    @MockBean
    @Qualifier("userService")
    private UserService mockUserService;

    @MockBean
    @Qualifier("mailService")
    private MailService mockMailService;

    @InjectMocks
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(statements = "insert into users (user_id, account, nick_name, password, email, phone) " +
            "values (123414131L,'accountTest','SuperMan','passwordTest','2436056388@qq.com','18539246184')",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testRegister() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"accountTest\",\"nickName\":\"SuperMan\",\"password\":\"passwordTest\",\"checkPassword\":\"passwordTest\",\"email\":\"2436056388@qq.com\",\"emailCode\":\"tested\",\"phone\":\"18539246184\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
