package com.pincock.pincock.content.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.content.ContentCreateRequestDTO;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ContentCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContentService contentService;

    @Test
    void createContent_success() throws Exception {
        // given
        ContentCreateRequestDTO requestDTO = ContentCreateRequestDTO.builder()
                .title("테스트 게시글")
                .detail("게시글 내용입니다")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        String content = objectMapper.writeValueAsString(requestDTO);

        ContentResponseDTO mockResponse = ContentResponseDTO.builder()
                .id(1L)
                .title("테스트 게시글")
                .detail("게시글 내용입니다")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        when(contentService.addContent(any(ContentCreateRequestDTO.class), any(Long.class)))
                .thenReturn(mockResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/contents")
                        .with(SecurityMockMvcRequestPostProcessors.user(
                                new org.springframework.security.core.userdetails.User(
                                        "seop", "", new ArrayList<>()
                                )
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());


        verify(contentService, times(1))
                .addContent(any(ContentCreateRequestDTO.class), any(Long.class));
    }

}
