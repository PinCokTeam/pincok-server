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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CrewDeleteServiceTest {

    @Autowired
    private CrewService crewService;

    @MockBean
    private CrewRepository crewRepository;

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @Test
    void deleteCrew() {
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
                .crewStatus(CrewStatus.LEADER)
                .build();

        when(crewMemberRepository.findByCrewIdAndUserId(crewId, userId)).thenReturn(Optional.of(member));
        when(crewMemberRepository.findAllByCrewId(crewId))
                .thenReturn(List.of(member));
        when(crewRepository.findById(crewId))
                .thenReturn(Optional.of(crew));

        crewService.deleteCrew(crewId, userId);

        org.mockito.Mockito.verify(crewMemberRepository).deleteAll(List.of(member));
        org.mockito.Mockito.verify(crewRepository).delete(crew);
    }

}
