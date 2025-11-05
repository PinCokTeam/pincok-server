package com.pincock.pincock.crew.service;

import com.pincock.pincock.dto.crew.CrewCreateResponseDTO;
import com.pincock.pincock.dto.crew.CrewRequestDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.entity.CrewMember;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.repository.CrewRepository;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CrewCreateServiceTest {

    @MockBean
    private CrewRepository crewRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CrewService crewService;

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @Test
    void addCrew() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("seop")
                .nickname("seop")
                .build();
        userRepository.save(user);

        CrewRequestDTO crewRequestDTO = CrewRequestDTO.builder()
                .name("스터디")
                .detail("공부하자")
                .imageUrl("sadad")
                .build();

        Crew crew = Crew.builder()
                .id(1L)
                .name("스터디")
                .detail("공부하자")
                .imageUrl("sadad")
                .build();
        when(crewMemberRepository.save(any(CrewMember.class)))
                .thenReturn(CrewMember.createLeader(user, crew));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(crewRepository.save(any(Crew.class))).thenReturn(crew);

        CrewCreateResponseDTO result = crewService.addCrew(crewRequestDTO, userId);

        // then
        assertEquals("스터디", result.getName());
        assertEquals("공부하자", result.getDetail());
        assertEquals(userId, result.getUserId());

    }
}
