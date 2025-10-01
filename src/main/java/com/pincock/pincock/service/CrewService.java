package com.pincock.pincock.service;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;

    public List<CrewResponseDTO> getCrewList() {
        List<Crew> crews = crewRepository.findAll();

        return crews.stream()
                .map(CrewResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
