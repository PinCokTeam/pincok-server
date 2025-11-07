package com.pincock.pincock.crew.service;

import com.pincock.pincock.entity.*;
import com.pincock.pincock.repository.ContentRepository;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CrewGetContentsServiceTest {

    @MockBean
    private CrewMemberRepository crewMemberRepository;

    @MockBean
    private ContentRepository contentRepository;

    @Autowired
    private CrewService crewService;

    @Test
    void crewGetContents() {
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

        Content content1 = Content.builder()
                .id(1L)
                .title("게시글")
                .detail("내용")
                .longitude(12312D)
                .latitude(21321412D)
                .user(user)
                .images(null)
                .build();

        Content content2 = Content.builder()
                .id(2L)
                .title("게시글")
                .detail("내용")
                .longitude(12312D)
                .latitude(21321412D)
                .user(user)
                .images(null)
                .build();

        CrewMember member = CrewMember.builder()
                .id(new CrewMemberId(user.getId(), crew.getId()))
                .user(user)
                .crew(crew)
                .crewStatus(CrewStatus.USER)
                .build();

        when(crewMemberRepository.findByCrewIdAndUserId(crewId, userId))
                .thenReturn(Optional.of(member));

        when(crewMemberRepository.findByCrewId(crewId))
                .thenReturn(List.of(member));

        when(contentRepository.findAllByUserIdIn(List.of(userId)))
                .thenReturn(List.of(content1, content2));

        var result = crewService.getCrewContents(crewId, userId);
        org.junit.jupiter.api.Assertions.assertEquals(2, result.size());
        org.junit.jupiter.api.Assertions.assertEquals(content1.getId(), result.get(0).getId());
        org.junit.jupiter.api.Assertions.assertEquals(content2.getId(), result.get(1).getId());

    }
}
