package com.pincock.pincock.controller;

import com.pincock.pincock.dto.crew.CrewResponseDTO;
import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.service.CrewService;
import lombok.RequiredArgsConstructor;
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
}
