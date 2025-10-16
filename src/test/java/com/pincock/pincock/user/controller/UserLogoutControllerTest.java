package com.pincock.pincock.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
