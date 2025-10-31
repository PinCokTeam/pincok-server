package com.pincock.pincock.content.controller;

import com.pincock.pincock.controller.ContentController;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.ContentRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ContentRepository contentRepository;

    @Test
    @DisplayName("게시글 전체 조회 성공")
    void getContents_success() throws Exception {
        User user = User.builder()
                .name("seop")
                .nickname("seop")
                .build();
        userRepository.save(user);

        List<Content> contents = List.of(
                Content.builder()
                        .title("1번 맛집")
                        .detail("오시죠")
                        .latitude(1231D)
                        .longitude(213D)
                        .user(user)
                        .build(),
                Content.builder()
                        .title("2번 맛집")
                        .detail("여기도 맛있어요")
                        .latitude(567D)
                        .longitude(890D)
                        .user(user)
                        .build()
        );
        contentRepository.saveAll(contents);

        List<ContentResponseDTO> mockResponseList = List.of(
                new ContentResponseDTO(1L, "1번 맛집", "오시죠", 1231D, 213D),
                new ContentResponseDTO(2L, "2번 맛집", "여기도 맛있어요", 567D, 890D)
        );

        when(userRepository.findByNickname("seop"))
                .thenReturn(Optional.of(User.builder().id(1L).nickname("seop").build()));


        when(contentService.contentGetAll(1L)).thenReturn(mockResponseList);

        mockMvc.perform(get("/contents")
                .with(SecurityMockMvcRequestPostProcessors.user(
                        new org.springframework.security.core.userdetails.User(
                                "seop", "", new ArrayList<>()
                        )
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("1번 맛집"))
                .andExpect(jsonPath("$[0].detail").value("오시죠"))
                .andExpect(jsonPath("$[1].title").value("2번 맛집"))
                .andExpect(jsonPath("$[1].detail").value("여기도 맛있어요"));
    }

    @Test
    @DisplayName("로그인 안 한 상태에서 게시글 전체 조회 실패")
    void getContents_unauthorized() throws Exception {
        mockMvc.perform(get("/contents"))
                .andExpect(status().isUnauthorized());
    }
}
