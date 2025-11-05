package com.pincock.pincock.crew.controller;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewGetDetailController {

    @MockBean
    private CrewService crewService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrewRepository crewRepository;

    @Test
    void getDetail() throws Exception {
        CrewResponseDTO mockCrew = CrewResponseDTO.builder()
                .id(1L)
                .name("코린이")
                .detail("어서오고")
                .imageUrl("sadljnlqwe")
                .build();

        when(crewService.getCrew(1L)).thenReturn(mockCrew);

        mockMvc.perform(get("/crews/{crew_id}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.user(
                        new org.springframework.security.core.userdetails.User(
                                "seop", "", new ArrayList<>()
                        )
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("코린이"))
                .andExpect(jsonPath("$.detail").value("어서오고"))
                .andExpect(jsonPath("$.imageUrl").value("sadljnlqwe"));
    }
}
