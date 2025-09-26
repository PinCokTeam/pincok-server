package com.pincock.pincock.service;

import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    // 게시글 상세 조회
    public ContentResponseDTO contentDetail(Long contentId, Long userId) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!content.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인 게시글만 조회할 수 있습니다.");
        }

        return ContentResponseDTO.fromEntity(content);
    }

    public List<ContentResponseDTO> contentGetAll(Long userId) {

        List<Content> contents = contentRepository.findAllByUserId(userId);
        return contents.stream()
                .map(ContentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
