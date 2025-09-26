package com.pincock.pincock.controller;

import com.pincock.pincock.dto.image.ContentImageResponseDTO;
import com.pincock.pincock.service.ContentImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

}
