package com.pincock.pincock.contentimage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.image.ContentImageResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.ImageStatus;
import com.pincock.pincock.service.ContentImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContentGetImagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContentImageService contentImageService;

    @Test
    void getImages() throws Exception {

        ContentImageResponseDTO mockResponse = new ContentImageResponseDTO(
                1L,
                null,
                "테스트_URL",
                ImageStatus.NORMAL
        );

        UserResponseDTO loginUser = new UserResponseDTO(1L, "한섭", "seop");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUser", loginUser);

        List<ContentImageResponseDTO> mockResponseList = List.of(mockResponse);

        Mockito.when(contentImageService.getImage(anyLong()))
                .thenReturn(mockResponseList);

        mockMvc.perform(get("/contents/{content_id}/images", 1L)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].imageUrl").value("테스트_URL"));
    }
}