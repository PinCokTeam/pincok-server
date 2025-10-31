//package com.pincock.pincock.contentimage.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pincock.pincock.dto.image.ContentImageRequestDTO;
//import com.pincock.pincock.dto.image.ContentImageResponseDTO;
//import com.pincock.pincock.dto.user.UserResponseDTO;
//import com.pincock.pincock.entity.ImageStatus;
//import com.pincock.pincock.service.ContentImageService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ContentCreateImageControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ContentImageService contentImageService;
//
//    @Test
//    void shouldCreateImage() throws Exception {
//
//        List<ContentImageRequestDTO> requestDTOList = List.of(
//                ContentImageRequestDTO.builder()
//                        .imageUrl("image1.png")
//                        .titleImage(ImageStatus.NORMAL)
//                        .build(),
//                ContentImageRequestDTO.builder()
//                        .imageUrl("image2.png")
//                        .titleImage(ImageStatus.NORMAL)
//                        .build()
//        );
//
//        UserResponseDTO loginUser = new UserResponseDTO(1L, "한섭", "seop");
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("loginUser", loginUser);
//
//        List<ContentImageResponseDTO> mockResponse = List.of(
//                ContentImageResponseDTO.builder()
//                        .id(1L)
//                        .content(null)             // 테스트에서는 null 가능
//                        .imageUrl("image1.png")
//                        .titleImage(ImageStatus.NORMAL) // enum에 맞게 지정
//                        .build(),
//                ContentImageResponseDTO.builder()
//                        .id(2L)
//                        .content(null)
//                        .imageUrl("image2.png")
//                        .titleImage(ImageStatus.NORMAL)
//                        .build()
//        );
//        Mockito.when(contentImageService.addImages(
//                any(Long.class),
//                anyList()
//        )).thenReturn(mockResponse);
//
//        mockMvc.perform(post("/contents/{content_id}/images", 1L)
//                        .session(session)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(List.of(
//                                ContentImageRequestDTO.builder()
//                                        .imageUrl("image1.png")
//                                        .titleImage(ImageStatus.NORMAL)
//                                        .build(),
//                                ContentImageRequestDTO.builder()
//                                        .imageUrl("image2.png")
//                                        .titleImage(ImageStatus.NORMAL)
//                                        .build()
//                        ))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].imageUrl").value("image1.png"))
//                .andExpect(jsonPath("$[1].imageUrl").value("image2.png"));
//    }
//}
