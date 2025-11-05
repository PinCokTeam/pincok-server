package com.pincock.pincock.crew.service;

import com.pincock.pincock.dto.crew.CrewUpdateRequestDTO;
import com.pincock.pincock.entity.*;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.repository.CrewRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CrewLeaveServiceTest {

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @MockBean
    private CrewRepository crewRepository;

    @Autowired
    private CrewService crewService;

    @Test
    void leaveCrew() throws Exception {
        Long userId = 1L;
        Long crewId = 1L;

        User user = User.builder()
                .id(userId)
                .name("seop")
                .nickname("seop")
                .build();

        Crew crew = Crew.builder()
                .id(crewId)
                .name("기존 크루")
                .detail("기존 설명")
                .imageUrl("oldUrl")
                .build();

        CrewMember member = CrewMember.builder()
                .id(new CrewMemberId(user.getId(), crew.getId()))
                .user(user)
                .crew(crew)
                .crewStatus(CrewStatus.USER)
                .build();

        when(crewMemberRepository.findByCrewIdAndUserId(crewId, userId))
                .thenReturn(Optional.of(member));

        crewService.leaveCrew(crewId, userId);

        org.mockito.Mockito.verify(crewMemberRepository).delete(member);
    }
}
