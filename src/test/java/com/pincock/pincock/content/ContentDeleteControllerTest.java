package com.pincock.pincock.content;

import com.pincock.pincock.controller.ContentController;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentDeleteControllerTest {

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
    @DisplayName("게시글 삭제 성공")
    void deleteContent_success() throws Exception {
        mockMvc.perform(delete("/contents/1").session(session))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("로그인 안 한 상태에서 게시글 삭제 실패")
    void deleteContent_unauthorized() throws Exception {
        mockMvc.perform(delete("/contents/1"))
                .andExpect(status().isUnauthorized());
    }
}
