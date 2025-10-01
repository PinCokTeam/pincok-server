package com.pincock.pincock.repository;

import com.pincock.pincock.entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

    List<CrewMember> findByUserId(Long userId);
}
