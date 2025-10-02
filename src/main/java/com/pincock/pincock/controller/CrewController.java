package com.pincock.pincock.controller;

import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.crew.*;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.service.CrewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    // 생성되어 있는 크루 리스트
    @GetMapping("/crews")
    public ResponseEntity<List<CrewResponseDTO>> getCrews() {
        List<CrewResponseDTO> crewResponseDTOS = crewService.getCrewList();
        return ResponseEntity.ok(crewResponseDTOS);
    }

    // 내가 속한 크루 리스트
    @GetMapping("/users/me/crews")
    public ResponseEntity<List<CrewResponseDTO>> getUserCrews(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();
        List<CrewResponseDTO> crewResponseDTOS = crewService.getUserCrewList(userId);

        return ResponseEntity.ok(crewResponseDTOS);
    }

    // 크루 상세 조회
    @GetMapping("/crews/{crew_id}")
    public ResponseEntity<CrewResponseDTO> getCrew(@PathVariable Long crew_id) {
        CrewResponseDTO crewResponseDTO = crewService.getCrew(crew_id);
        return ResponseEntity.ok(crewResponseDTO);
    }

    // 크루 생성
    @PostMapping("/crews")
    public ResponseEntity<CrewCreateResponseDTO> createCrew(@RequestBody CrewRequestDTO crewRequestDTO,
                                                            HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();

        CrewCreateResponseDTO crewCreateResponseDTO = crewService.addCrew(crewRequestDTO, userId);
        return ResponseEntity.ok(crewCreateResponseDTO);
    }

    // 크루 입장
    @PostMapping("/crews/{crew_id}/join")
    public ResponseEntity<Void> joinCrew(@PathVariable Long crew_id,
                                         HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();
        crewService.joinCrew(crew_id, userId);
        return ResponseEntity.ok(null);
    }

    // 크루 수정
    @PutMapping("/crews/{crew_id}")
    public ResponseEntity<CrewUpdateResponseDTO> updateCrew(@PathVariable Long crew_id,
                                                      @RequestBody CrewUpdateRequestDTO crewUpdateRequestDTO,
                                                      HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();

        CrewUpdateResponseDTO crewUpdateResponseDTO = crewService.updateCrew(crew_id, userId, crewUpdateRequestDTO);
        return ResponseEntity.ok(crewUpdateResponseDTO);
    }

    // 크루 삭제
    @DeleteMapping("/crews/{crew_id}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long crew_id,
                                           HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();

        crewService.deleteCrew(crew_id, userId);
        return ResponseEntity.noContent().build();
    }

    // 크루 탈퇴
    @DeleteMapping("/users/me/crews/{crew_id}")
    public ResponseEntity<Void> leaveCrew(@PathVariable Long crew_id,
                                          HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();
        crewService.leaveCrew(crew_id, userId);
        return ResponseEntity.noContent().build();
    }

    // 속한 크루 게시글 전체 조회
    @GetMapping("/crews/{crew_id}/contents")
    public ResponseEntity<List<ContentResponseDTO>> getCrewContents(@PathVariable Long crew_id,
                                                                    HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();

        List<ContentResponseDTO> contentResponseDTOS = crewService.getCrewContents(crew_id, userId);
        return ResponseEntity.ok(contentResponseDTOS);
    }
}
