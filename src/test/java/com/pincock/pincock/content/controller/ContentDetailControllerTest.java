package com.pincock.pincock.content.controller;

import com.pincock.pincock.controller.ContentController;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("게시글 상세 조회 성공")
    void getContent_success() throws Exception {
        User user = User.builder()
                .name("seop")
                .nickname("seop")
                .build();
        userRepository.save(user);

        Long contentId = 1L;
        Long userId = 1L;
        ContentResponseDTO mockResponse = ContentResponseDTO.builder()
                .id(contentId)
                .title("제목")
                .detail("내용")
                .latitude(213D)
                .longitude(213D)
                .build();

        when(userRepository.findByNickname("seop")).thenReturn(Optional.of(
                User.builder().id(1L).nickname("seop").build()
        ));
        when(contentService.contentDetail(contentId, userId)).thenReturn(mockResponse);

        mockMvc.perform(get("/contents/{content_id}", contentId)
                        .with(SecurityMockMvcRequestPostProcessors.user(
                                new org.springframework.security.core.userdetails.User(
                                        "seop", "", new ArrayList<>()
                                )
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.detail").value("내용"));
    }

    @Test
    @DisplayName("로그인 안 한 상태에서 게시글 상세 조회 실패")
    void getContent_unauthorized() throws Exception {
        mockMvc.perform(get("/contents/1"))
                .andExpect(status().isUnauthorized());
    }
}
