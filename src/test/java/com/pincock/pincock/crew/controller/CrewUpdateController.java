package com.pincock.pincock.crew.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pincock.pincock.dto.crew.CrewUpdateRequestDTO;
import com.pincock.pincock.dto.crew.CrewUpdateResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.CrewRepository;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewUpdateController {

    @MockBean
    private CrewService crewService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CrewRepository crewRepository;

    @Test
    void updateCrew() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("seop")
                .nickname("seop")
                .build();

        Crew crew = Crew.builder()
                .id(1L)
                .name("코린이")
                .detail("환영")
                .imageUrl("asdasde")
                .build();

        CrewUpdateRequestDTO crewUpdateRequestDTO = CrewUpdateRequestDTO.builder()
                .name("코린이")
                .detail("어서오고")
                .imageUrl("asdasde")
                .build();

        CrewUpdateResponseDTO responseDTO = CrewUpdateResponseDTO.builder()
                .name("코린이")
                .detail("어서오고")
                .imageUrl("asdasde")
                .leader_id(user.getId())
                .build();

        when(userRepository.findByNickname("seop")).thenReturn(Optional.of(user));
        when(crewService.updateCrew(eq(1L), eq(user.getId()), any(CrewUpdateRequestDTO.class)))
                .thenReturn(responseDTO);
        String jsonRequest = objectMapper.writeValueAsString(crewUpdateRequestDTO);

        mockMvc.perform(put("/crews/{crew_id}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.user(user.getNickname()).password(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("코린이"))
                .andExpect(jsonPath("$.detail").value("어서오고"))
                .andExpect(jsonPath("$.imageUrl").value("asdasde"));
    }
}
