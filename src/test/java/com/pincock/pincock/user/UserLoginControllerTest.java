package com.pincock.pincock.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.UserLoginRequestDTO;
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
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // JSON 직렬화

    @MockBean
    private UserService userService; // UserService를 MockBean으로 주입

    @Test
    void login_success() throws Exception {
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
        requestDTO.setNickname("seop");

        UserResponseDTO mockResponse = UserResponseDTO.builder()
                .id(1L)
                .name("한섭")
                .nickname("seop")
                .build();

        // 서비스가 호출되면 모의값 반환
        when(userService.loginUser(any(UserLoginRequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nickname").value("seop"));
    }

    @Test
    void login_failure_when_user_not_found() throws Exception {
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
        requestDTO.setNickname("unknown");

        // 로그인 시 예외 발생
        when(userService.loginUser(any(UserLoginRequestDTO.class)))
                .thenThrow(new RuntimeException("유저 없음"));

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }
}
