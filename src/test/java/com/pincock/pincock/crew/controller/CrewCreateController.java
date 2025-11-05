package com.pincock.pincock.crew.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.crew.CrewCreateResponseDTO;
import com.pincock.pincock.dto.crew.CrewRequestDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewCreateController {

    @MockBean
    private CrewService crewService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createCrew() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("seop")
                .nickname("seop")
                .build();

        CrewRequestDTO crewRequestDTO = CrewRequestDTO.builder()
                .name("코린이 모집")
                .detail("어서오고")
                .imageUrl("sadasd")
                .build();

        CrewCreateResponseDTO crewCreateResponseDTO = CrewCreateResponseDTO.builder()
                .id(1L)
                .name("코린이 모집")
                .detail("어서오고")
                .imageUrl("sadasd")
                .userId(user.getId())
                .build();

        // Mock 설정
        when(userRepository.findByNickname("seop"))
                .thenReturn(Optional.of(user));
        when(crewService.addCrew(any(CrewRequestDTO.class), anyLong()))
                .thenReturn(crewCreateResponseDTO);

        String jsonRequest = objectMapper.writeValueAsString(crewRequestDTO);

        mockMvc.perform(post("/crews")
                        .with(SecurityMockMvcRequestPostProcessors.user("seop"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("코린이 모집"))
                .andExpect(jsonPath("$.detail").value("어서오고"))
                .andExpect(jsonPath("$.imageUrl").value("sadasd"))
                .andExpect(jsonPath("$.userId").value(user.getId()));
    }

}
