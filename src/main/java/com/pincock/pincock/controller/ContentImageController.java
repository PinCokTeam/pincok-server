package com.pincock.pincock.controller;

import com.pincock.pincock.dto.image.ContentImageRequestDTO;
import com.pincock.pincock.dto.image.ContentImageResponseDTO;
import com.pincock.pincock.service.ContentImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentImageController {

    private final ContentImageService contentImageService;

    // 게시글 이미지 목록 조회
    @GetMapping("/contents/{content_id}/images")
    public ResponseEntity<List<ContentImageResponseDTO>> getContentImages(@PathVariable Long content_id) {
        List<ContentImageResponseDTO> contentImageResponseDTOs = contentImageService.getImage(content_id);
        return ResponseEntity.ok(contentImageResponseDTOs);
    }

    // 게시글 이미지 생성
    @PostMapping("/contents/{content_id}/images")
    public ResponseEntity<List<ContentImageResponseDTO>> addContentImage(@PathVariable Long content_id,
                                                                   @RequestBody List<ContentImageRequestDTO> contentImageRequestDTOs) {
        List<ContentImageResponseDTO> contentImageResponseDTOs = contentImageService.addImages(content_id, contentImageRequestDTOs);
        return ResponseEntity.ok(contentImageResponseDTOs);
    }
}
