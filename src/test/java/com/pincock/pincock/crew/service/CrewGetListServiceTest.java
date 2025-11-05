package com.pincock.pincock.crew.service;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.repository.CrewRepository;
import com.pincock.pincock.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewGetListServiceTest {

    @MockBean
    private CrewRepository crewRepository;

    @Autowired
    private CrewService crewService;

    @Test
    void getList() throws Exception {
        List<Crew> crews = List.of(
                Crew.builder()
                        .id(1L)
                        .name("코딩 크루")
                        .detail("환영")
                        .imageUrl("asdasds")
                        .build(),
                Crew.builder()
                        .id(2L)
                        .name("스터디")
                        .detail("아무나 오세여")
                        .imageUrl(null)
                        .build()
        );
        crewRepository.saveAll(crews);

        when(crewRepository.findAll()).thenReturn(crews);

        List<CrewResponseDTO> result = crewService.getCrewList();
        assertEquals(2, result.size());
        assertEquals("코딩 크루", result.get(0).getName());
        assertEquals("스터디", result.get(1).getName());

    }
}
