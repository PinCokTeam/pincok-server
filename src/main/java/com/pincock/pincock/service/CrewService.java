package com.pincock.pincock.service;

import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.crew.*;
import com.pincock.pincock.entity.*;
import com.pincock.pincock.repository.ContentRepository;
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
    private final ContentRepository contentRepository;

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

    public void joinCrew(Long crewId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new RuntimeException("해당 크루가 존재하지 않습니다."));

        boolean exists = crewMemberRepository.existsByUserAndCrew(user, crew);
        if (exists) {
            throw new RuntimeException("이미 해당 크루에 가입되어 있습니다.");
        }

        CrewMember crewMember = CrewMember.joinUser(user, crew);

        crewMemberRepository.save(crewMember);
    }

    @Transactional
    public CrewUpdateResponseDTO updateCrew(Long crewId, Long userId, CrewUpdateRequestDTO crewUpdateRequestDTO) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new RuntimeException("크루가 존재하지 않습니다."));

        CrewMember leader = crewMemberRepository.findByCrewIdAndUserId(crewId, userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 크루에 속해있지 않습니다."));

        if (leader.getCrewStatus() != CrewStatus.LEADER) {
            throw new RuntimeException("크루장만 수정할 수 있습니다.");
        }

        if (crewUpdateRequestDTO.getName() != null) crew.setName(crewUpdateRequestDTO.getName());
        if (crewUpdateRequestDTO.getDetail() != null) crew.setDetail(crewUpdateRequestDTO.getDetail());
        if (crewUpdateRequestDTO.getImageUrl() != null) crew.setImageUrl(crewUpdateRequestDTO.getImageUrl());

        CrewMember currentLeader = leader;

        if (crewUpdateRequestDTO.getNewLeaderId() != null) {
            CrewMember newLeader = crewMemberRepository.findByCrewIdAndUserId(crewId, crewUpdateRequestDTO.getNewLeaderId())
                    .orElseThrow(() -> new RuntimeException("위임 대상이 크루에 속해있지 않습니다."));

            leader.setCrewStatus(CrewStatus.USER);
            newLeader.setCrewStatus(CrewStatus.LEADER);
            currentLeader = newLeader;
        }
        return CrewUpdateResponseDTO.fromEntity(crew, currentLeader);
    }

    @Transactional
    public void deleteCrew(Long crewId, Long userId) {
        CrewMember leader = crewMemberRepository.findByCrewIdAndUserId(crewId, userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 크루에 속해있지 않습니다."));

        if (leader.getCrewStatus() != CrewStatus.LEADER) {
            throw new RuntimeException("크루장만 삭제할 수 있습니다.");
        }

        List<CrewMember> members = crewMemberRepository.findAllByCrewId(crewId);
        crewMemberRepository.deleteAll(members);

        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new RuntimeException("크루가 존재하지 않습니다."));

        crewRepository.delete(crew);
    }

    @Transactional
    public void leaveCrew(Long crewId, Long userId) {
        CrewMember User = crewMemberRepository.findByCrewIdAndUserId(crewId, userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 크루에 속해있지 않습니다."));

        if (User.getCrewStatus() != CrewStatus.USER) {
            throw new RuntimeException("일반 유저만 탈퇴할 수 있습니다.");
        }
        crewMemberRepository.delete(User);
    }

    @Transactional(readOnly = true)
    public List<ContentResponseDTO> getCrewContents(Long crewId, Long userId) {
        crewMemberRepository.findByCrewIdAndUserId(crewId, userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 크루에 속해있지 않습니다."));

        List<Long> userIds = crewMemberRepository.findByCrewId(crewId)
                .stream()
                .map(cm -> cm.getUser().getId())
                .toList();

        List<Content> contents = contentRepository.findAllByUserIdIn(userIds);
        return contents.stream()
                .map(ContentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
