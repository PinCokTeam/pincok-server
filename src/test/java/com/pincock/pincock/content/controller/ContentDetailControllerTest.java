package com.pincock.pincock.content.controller;

import com.pincock.pincock.controller.ContentController;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("loginUser", new UserResponseDTO(1L, "testUser", "닉네임"));
    }

    @Test
    @DisplayName("게시글 상세 조회 성공")
    void getContent_success() throws Exception {
        ContentResponseDTO mockResponse = new ContentResponseDTO(
                1L,
                "제목",
                "내용",
                37.5665,
                126.978
        );
        Mockito.when(contentService.contentDetail(anyLong(), anyLong())).thenReturn(mockResponse);

        mockMvc.perform(get("/contents/1").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.detail").value("내용"))
                .andExpect(jsonPath("$.latitude").value(37.5665))
                .andExpect(jsonPath("$.longitude").value(126.978));
    }

    @Test
    @DisplayName("로그인 안 한 상태에서 게시글 상세 조회 실패")
    void getContent_unauthorized() throws Exception {
        mockMvc.perform(get("/contents/1"))
                .andExpect(status().isUnauthorized());
    }
}
