package com.pincock.pincock.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void logout_success() throws Exception {
        mockMvc.perform(post("/users/logout")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(
                                new org.springframework.security.core.userdetails.User(
                                        "seop", "", new ArrayList<>()
                                )
                        )))
                .andExpect(status().isNoContent()); // 204 반환 확인
    }

}
