package com.pincock.pincock.content.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.controller.ContentController;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.content.ContentUpdateRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.ContentRepository;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ContentRepository contentRepository;

    @Test
    @DisplayName("게시글 수정 성공")
    void updateContent_success() throws Exception {
        User user = User.builder()
                .name("seop")
                .nickname("seop")
                .build();
        userRepository.save(user);

        Content content = Content.builder()
                .title("맛집")
                .detail("어서오세여")
                .latitude(123D)
                .longitude(421421D)
                .user(user)
                .build();
        contentRepository.save(content);

        ContentUpdateRequestDTO contentUpdateRequestDTO = ContentUpdateRequestDTO.builder()
                .title("수정된 제목")
                .detail("수정된 내용")
                .build();

        when(userRepository.findByNickname("seop"))
                .thenReturn(Optional.of(User.builder().id(1L).nickname("seop").build()));

        Long contentId = 1L;

        ContentResponseDTO mockResponse = ContentResponseDTO.builder()
                .id(content.getId())
                .title("수정된 제목")
                .detail("수정된 내용")
                .latitude(37.5665)
                .longitude(126.978)
                .build();

        when(contentService.updateContent(
                any(ContentUpdateRequestDTO.class),
                eq(contentId),
                anyLong()
        )).thenReturn(mockResponse);

        mockMvc.perform(put("/contents/{content_id}", contentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contentUpdateRequestDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user(
                                new org.springframework.security.core.userdetails.User(
                                        "seop", "", new ArrayList<>()
                                )
                        ))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // ← CSRF 토큰 추가
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.detail").value("수정된 내용"));
    }

}
