package com.pincock.pincock.crew.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CrewGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrewService crewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnCrewList() throws Exception {

        List<CrewResponseDTO> mockCrewList = List.of(
                CrewResponseDTO.builder()
                        .id(1L)
                        .name("코딩크루")
                        .detail("개발자 스터디 그룹")
                        .build(),
                CrewResponseDTO.builder()
                        .id(2L)
                        .name("AI러너스")
                        .detail("AI를 공부하는 팀")
                        .build()
        );

        when(crewService.getCrewList()).thenReturn(mockCrewList);

        // when & then
        mockMvc.perform(get("/crews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("코딩크루"))
                .andExpect(jsonPath("$[1].name").value("AI러너스"));
    }
}
