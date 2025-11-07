package com.pincock.pincock.crew.controller;

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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewDeleteControllerTest {

    @MockBean
    private CrewService crewService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void DeleteCrew() throws Exception {

        User user = User.builder()
                .id(1L)
                .name("seop")
                .nickname("seop")
                .build();

        when(userRepository.findByNickname("seop"))
                .thenReturn(Optional.of(user));
        doNothing().when(crewService).deleteCrew(anyLong(), anyLong());

        mockMvc.perform(delete("/crews/{crew_id}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.user("seop")))
                .andExpect(status().isNoContent());
    }
}
