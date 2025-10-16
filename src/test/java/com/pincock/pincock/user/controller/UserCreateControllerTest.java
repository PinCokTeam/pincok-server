package com.pincock.pincock.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.UserCreateRequestDTO;
import com.pincock.pincock.dto.UserResponseDTO;
import com.pincock.pincock.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void addUser_success() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO();
        requestDTO.setName("한섭");
        requestDTO.setNickname("seop");

        UserResponseDTO mockResponse = UserResponseDTO.builder()
                .id(1L)
                .name("한섭")
                .nickname("seop")
                .build();

        // 서비스 호출 시 mockResponse 반환
        when(userService.createUser(any(UserCreateRequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("한섭"))
                .andExpect(jsonPath("$.nickname").value("seop"));
    }

    @Test
    void addUser_failure_when_name_blank() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO();
        requestDTO.setName(""); // 이름 비어있음
        requestDTO.setNickname("seop");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUser_failure_when_nickname_blank() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO();
        requestDTO.setName("한섭");
        requestDTO.setNickname(""); // 닉네임 비어있음

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }
}
