package com.pincock.pincock.crew.controller;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.User;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewGetUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrewService crewService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUserCrew() throws Exception {
        User user = User.builder()
                .name("seop")
                .nickname("seop")
                .build();
        userRepository.save(user);
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
        when(userRepository.findByNickname("seop"))
                .thenReturn(Optional.of(User.builder().id(1L).nickname("seop").build()));

        when(crewService.getUserCrewList(1L)).thenReturn(mockCrewList);

        mockMvc.perform(get("/users/me/crews")
                .with(SecurityMockMvcRequestPostProcessors.user(
                        new org.springframework.security.core.userdetails.User(
                                "seop", "", new ArrayList<>()
                        )
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("코딩크루"))
                .andExpect(jsonPath("$[1].name").value("AI러너스"));
    }
}
