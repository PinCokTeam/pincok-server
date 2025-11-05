package com.pincock.pincock.crew.service;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CrewJoinServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CrewRepository crewRepository;

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @Autowired
    private CrewService crewService;

    @Test
    void joinCrew() {
        Long userId = 1L;
        Long crewId = 1L;

        User user = User.builder()
                .id(userId)
                .name("seop")
                .nickname("seop")
                .build();

        Crew crew = Crew.builder()
                .id(crewId)
                .name("코딩 테스트")
                .detail("공부하자")
                .imageUrl("sadasd")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(crewRepository.findById(crewId)).thenReturn(Optional.of(crew));
        when(crewMemberRepository.existsByUserAndCrew(user, crew)).thenReturn(false);
        when(crewMemberRepository.save(any(CrewMember.class))).thenAnswer(i -> i.getArgument(0));

        crewService.joinCrew(crewId, userId);

        verify(crewMemberRepository).save(any(CrewMember.class));
    }
}
