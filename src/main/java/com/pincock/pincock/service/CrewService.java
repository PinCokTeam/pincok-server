package com.pincock.pincock.service;

import com.pincock.pincock.dto.crew.CrewCreateResponseDTO;
import com.pincock.pincock.dto.crew.CrewRequestDTO;
import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.*;
import com.pincock.pincock.repository.CrewMemberRepository;
import com.pincock.pincock.repository.CrewRepository;
import com.pincock.pincock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;
    private final UserRepository userRepository;
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

    @Transactional
    public CrewCreateResponseDTO addCrew(CrewRequestDTO crewRequestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        Crew crew = Crew.builder()
                .name(crewRequestDTO.getName())
                .detail(crewRequestDTO.getDetail())
                .imageUrl(crewRequestDTO.getImageUrl())
                .build();

        Crew savedCrew = crewRepository.save(crew);

        CrewMember crewMember = CrewMember.createLeader(user, savedCrew);
        crewMemberRepository.save(crewMember);
        return CrewCreateResponseDTO.fromEntity(savedCrew, userId);
    }
}
