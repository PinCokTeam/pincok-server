package com.pincock.pincock.crew.service;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.Crew;
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
public class CrewGetServiceTest {

    @MockBean
    private CrewRepository crewRepository; // Repository는 모킹

    @Autowired
    private CrewService crewService; // 실제 Service를 주입

    @Test
    void getCrew() {
        // given
        Crew crew = Crew.builder()
                .id(1L)
                .name("코딩")
                .detail("오세여")
                .imageUrl("sadda11")
                .build();

        when(crewRepository.findById(1L)).thenReturn(Optional.of(crew));

        // when
        CrewResponseDTO result = crewService.getCrew(1L);

        // then
        assertEquals("코딩", result.getName());
        assertEquals("오세여", result.getDetail());
        assertEquals("sadda11", result.getImageUrl());
    }
}