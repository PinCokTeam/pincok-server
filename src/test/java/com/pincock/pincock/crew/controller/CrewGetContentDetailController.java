package com.pincock.pincock.crew.controller;

import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.Crew;
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
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewGetContentDetailController {

    @MockBean
    private CrewService crewService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getContentDetail() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("seop")
                .nickname("seop")
                .build();

        Crew crew = Crew.builder()
                .id(1L)
                .name("코린이")
                .detail("환영")
                .imageUrl("sadas")
                .build();

        Content content = Content.builder()
                .id(1L)
                .title("맛집")
                .detail("공유")
                .latitude(123214D)
                .longitude(123132D)
                .build();

        ContentResponseDTO contentResponseDTO = ContentResponseDTO.builder()
                .id(1L)
                .title("맛집")
                .detail("공유")
                .latitude(123214D)
                .longitude(123132D)
                .build();

        when(userRepository.findByNickname("seop")).thenReturn(Optional.of(user));
        when(crewService.getCrewContent(crew.getId(), content.getId(), user.getId()))
                .thenReturn(contentResponseDTO);

        mockMvc.perform(get("/crews/{crew_id}/contents/{content_id}", crew.getId(), content.getId())
                .with(SecurityMockMvcRequestPostProcessors.user(
                        new org.springframework.security.core.userdetails.User(
                                "seop", "", new ArrayList<>()
                        )
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$.title").value("맛집"))
                .andExpect(jsonPath("$.detail").value("공유"));
    }
}
