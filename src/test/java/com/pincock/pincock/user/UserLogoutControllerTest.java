package com.pincock.pincock.user;

import com.pincock.pincock.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void logout_success() throws Exception {
        mockMvc.perform(post("/users/logout"))
                .andExpect(status().isNoContent()); // 204 반환 확인
    }
}
