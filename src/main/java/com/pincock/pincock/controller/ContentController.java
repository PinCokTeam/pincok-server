package com.pincock.pincock.controller;

import com.pincock.pincock.dto.content.ContentCreateRequestDTO;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.content.ContentUpdateRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.service.ContentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    // 내가 쓴 게시글 상세 조회
    @GetMapping("/contents/{content_id}")
    public ResponseEntity<ContentResponseDTO> getContent(@PathVariable Long content_id, HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long userId = userResponseDTO.getId();

        ContentResponseDTO contentResponseDTO = contentService.contentDetail(content_id, userId);
        return ResponseEntity.ok().body(contentResponseDTO);
    }

    // 내가 쓴 게시글 전체 조회
    @GetMapping("/contents")
    public ResponseEntity<List<ContentResponseDTO>> getContents(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long userId = userResponseDTO.getId();

        List<ContentResponseDTO> contentResponseDTOS = contentService.contentGetAll(userId);
        return ResponseEntity.ok().body(contentResponseDTOS);
    }

    @PostMapping("/contents")
    public ResponseEntity<ContentResponseDTO> createContent(@RequestBody ContentCreateRequestDTO contentCreateRequestDTO, HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();
        ContentResponseDTO contentResponseDTO = contentService.addContent(contentCreateRequestDTO, userId);
        return ResponseEntity.ok().body(contentResponseDTO);
    }

    @PutMapping("/contents/{content_id}")
    public ResponseEntity<ContentResponseDTO> changeContent(@PathVariable Long content_id,
                                                            @RequestBody ContentUpdateRequestDTO updateRequestDTO,
                                                            HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("loginUser");
        if(userResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userResponseDTO.getId();
        ContentResponseDTO contentResponseDTO = contentService.updateContent(updateRequestDTO, content_id, userId);
        return ResponseEntity.ok().body(contentResponseDTO);
    }
}
