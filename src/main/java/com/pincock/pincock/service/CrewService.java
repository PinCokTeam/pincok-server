package com.pincock.pincock.service;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.entity.CrewMember;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;

    private final CrewMemberRepository crewMemberRepository;

    public List<CrewResponseDTO> getCrewList() {
        List<Crew> crews = crewRepository.findAll();

        return crews.stream()
                .map(CrewResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CrewResponseDTO> getUserCrewList(Long userId) {
        List<CrewMember> crewMembers = crewMemberRepository.findByUserId(userId);

        return crewMembers.stream()
                .map(CrewMember::getCrew) // Crew 엔티티 뽑기
                .map(CrewResponseDTO::fromEntity) // DTO 변환
                .toList();
    }

    public CrewResponseDTO getCrew(Long crewId) {
        Crew crew = crewRepository.findById(crewId).orElseThrow();
        return CrewResponseDTO.fromEntity(crew);
    }
}
