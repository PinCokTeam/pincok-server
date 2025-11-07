package com.pincock.pincock.content.controller;

import com.pincock.pincock.controller.ContentController;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("게시글 삭제 성공")
    void deleteContent_success() throws Exception {
        User user = User.builder()
                .name("seop")
                .nickname("seop")
                .build();

        Long contentId = 1L;

        // Mock 설정
        when(userRepository.findByNickname("seop"))
                .thenReturn(Optional.of(user));
        doNothing().when(contentService).deleteContent(contentId, user.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/contents/{content_id}", contentId)
                        .with(SecurityMockMvcRequestPostProcessors.user(
                                new org.springframework.security.core.userdetails.User(
                                        "seop", "", new ArrayList<>()
                                )
                        ))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("로그인 안 한 상태에서 게시글 삭제 실패")
    void deleteContent_unauthorized() throws Exception {
        Long contentId = 1L;
        mockMvc.perform(delete("/contents/{content_id}",contentId)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
