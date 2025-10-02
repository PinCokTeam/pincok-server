package com.pincock.pincock.repository;

import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.entity.CrewMember;
import com.pincock.pincock.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

    List<CrewMember> findByUserId(Long userId);

    boolean existsByUserAndCrew(User user, Crew crew);

    Optional<CrewMember> findByCrewIdAndUserId(Long crewId, Long userId);

    List<CrewMember> findAllByCrewId(Long crewId);
}
