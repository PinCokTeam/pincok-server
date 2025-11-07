package com.pincock.pincock.crew.service;

import com.pincock.pincock.dto.crew.CrewRequestDTO;
import com.pincock.pincock.dto.crew.CrewUpdateRequestDTO;
import com.pincock.pincock.dto.crew.CrewUpdateResponseDTO;
import com.pincock.pincock.entity.*;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.repository.CrewRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CrewUpdateServiceTest {

    @MockBean
    private CrewRepository crewRepository;

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @Autowired
    private CrewService crewService;

    @Test
    void updateCrew() {
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

        CrewUpdateRequestDTO dto = CrewUpdateRequestDTO.builder()
                .name("스터디")
                .detail("공부하자")
                .imageUrl("sadad")
                .newLeaderId(userId) // 같은 사람에게 위임
                .build();

        CrewMember member = CrewMember.builder()
                .id(new CrewMemberId(user.getId(), crew.getId()))
                .user(user)
                .crew(crew)
                .crewStatus(CrewStatus.LEADER)
                .build();

        when(crewRepository.findById(crewId)).thenReturn(Optional.of(crew));
        when(crewMemberRepository.findByCrewIdAndUserId(crewId, userId)).thenReturn(Optional.of(member));

        CrewUpdateResponseDTO result = crewService.updateCrew(crewId, userId, dto);

        assertEquals("스터디", result.getName());
        assertEquals("공부하자", result.getDetail());
        assertEquals(userId, result.getLeader_id());
    }
}
