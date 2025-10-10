package com.pincock.pincock.content;

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

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentListControllerTest {

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
    @DisplayName("게시글 전체 조회 성공")
    void getContents_success() throws Exception {
        List<ContentResponseDTO> list = List.of(
                new ContentResponseDTO(1L, "제목1", "내용1", 37.5665, 126.978),
                new ContentResponseDTO(2L, "제목2", "내용2", 37.5651, 126.989)
        );

        Mockito.when(contentService.contentGetAll(anyLong())).thenReturn(list);

        mockMvc.perform(get("/contents").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("제목1"))
                .andExpect(jsonPath("$[0].detail").value("내용1"))
                .andExpect(jsonPath("$[0].latitude").value(37.5665))
                .andExpect(jsonPath("$[0].longitude").value(126.978))
                .andExpect(jsonPath("$[1].title").value("제목2"))
                .andExpect(jsonPath("$[1].detail").value("내용2"))
                .andExpect(jsonPath("$[1].latitude").value(37.5651))
                .andExpect(jsonPath("$[1].longitude").value(126.989));
    }

    @Test
    @DisplayName("로그인 안 한 상태에서 게시글 전체 조회 실패")
    void getContents_unauthorized() throws Exception {
        mockMvc.perform(get("/contents"))
                .andExpect(status().isUnauthorized());
    }
}
