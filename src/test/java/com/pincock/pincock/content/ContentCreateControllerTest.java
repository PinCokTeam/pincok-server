package com.pincock.pincock.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.content.ContentCreateRequestDTO;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        UserResponseDTO loginUser = new UserResponseDTO(1L, "한섭", "seop");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUser", loginUser);

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
        mockMvc.perform(post("/contents")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("테스트 게시글"))
                .andExpect(jsonPath("$.detail").value("게시글 내용입니다"));
    }

    @Test
    void createContent_unauthorized_when_no_session() throws Exception {
        // given
        ContentCreateRequestDTO requestDTO = ContentCreateRequestDTO.builder()
                .title("테스트 게시글")
                .detail("게시글 내용입니다")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        // when & then
        mockMvc.perform(post("/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnauthorized());
    }
}
