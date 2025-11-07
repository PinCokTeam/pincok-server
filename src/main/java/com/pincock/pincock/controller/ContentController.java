package com.pincock.pincock.controller;

import com.pincock.pincock.dto.content.ContentCreateRequestDTO;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.content.ContentUpdateRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final UserRepository userRepository;

    // 내가 쓴 게시글 상세 조회
    @GetMapping("/contents/{content_id}")
    public ResponseEntity<ContentResponseDTO> getContent(
            @PathVariable Long content_id,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();
        ContentResponseDTO contentResponseDTO = contentService.contentDetail(content_id, userId);

        return ResponseEntity.ok(contentResponseDTO);
    }

    // 내가 쓴 게시글 전체 조회
    @GetMapping("/contents")
    public ResponseEntity<List<ContentResponseDTO>> getContents(
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();

        List<ContentResponseDTO> contentResponseDTOS = contentService.contentGetAll(userId);
        return ResponseEntity.ok().body(contentResponseDTOS);
    }

    // 게시글 생성
    @PostMapping("/contents")
    public ResponseEntity<ContentResponseDTO> createContent(
            @RequestBody ContentCreateRequestDTO contentCreateRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();
        ContentResponseDTO contentResponseDTO = contentService.addContent(contentCreateRequestDTO, userId);

        return ResponseEntity.ok().body(contentResponseDTO);
    }

    // 내가 쓴 게시글 수정
    @PutMapping("/contents/{content_id}")
    public ResponseEntity<ContentResponseDTO> changeContent(
            @PathVariable Long content_id,
            @RequestBody ContentUpdateRequestDTO updateRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();
        ContentResponseDTO contentResponseDTO = contentService.updateContent(updateRequestDTO, content_id, userId);

        return ResponseEntity.ok().body(contentResponseDTO);
    }

    // 내가 쓴 게시글 삭제
    @DeleteMapping("/contents/{content_id}")
    public ResponseEntity<?> deleteContent(
            @PathVariable Long content_id,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        try {
            contentService.deleteContent(content_id, user.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // or 404/403
        }
    }

}
