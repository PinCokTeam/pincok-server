package com.pincock.pincock.controller;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.service.CrewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
