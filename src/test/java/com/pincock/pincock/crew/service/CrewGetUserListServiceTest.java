package com.pincock.pincock.crew.service;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.*;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.repository.CrewRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewGetUserListServiceTest {

    @MockBean
    private CrewRepository crewRepository;

    @Autowired
    private CrewService crewService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @Test
    void getUserList() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("seop")
                .nickname("seop")
                .build();

        Crew crew1 = Crew.builder()
                .id(1L)
                .name("코딩")
                .detail("아무나")
                .imageUrl("sadsad")
                .build();

        Crew crew2 = Crew.builder()
                .id(2L)
                .name("스터디")
                .detail("오세요")
                .imageUrl("saasdadsad")
                .build();

        CrewMember member1 = CrewMember.builder()
                .id(new CrewMemberId(user.getId(), crew1.getId()))
                .user(user)
                .crew(crew1)
                .crewStatus(CrewStatus.USER)
                .build();

        CrewMember member2 = CrewMember.builder()
                .id(new CrewMemberId(user.getId(), crew2.getId()))
                .user(user)
                .crew(crew2)
                .crewStatus(CrewStatus.LEADER)
                .build();

        List<CrewMember> crewMembers = List.of(member1, member2);

        when(crewMemberRepository.findByUserId(user.getId())).thenReturn(crewMembers);

        List<CrewResponseDTO> result = crewService.getUserCrewList(user.getId());

        assertEquals(2, result.size());
        assertEquals("코딩", result.get(0).getName());
        assertEquals("스터디", result.get(1).getName());
    }
}
