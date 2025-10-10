package com.pincock.pincock.service;

import com.pincock.pincock.dto.content.ContentCreateRequestDTO;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.content.ContentUpdateRequestDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.ContentRepository;
import com.pincock.pincock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public ContentResponseDTO addContent(ContentCreateRequestDTO contentCreateRequestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        Content content = Content.builder()
                .title(contentCreateRequestDTO.getTitle())
                .detail(contentCreateRequestDTO.getDetail())
                .latitude(contentCreateRequestDTO.getLatitude())
                .longitude(contentCreateRequestDTO.getLongitude())
                .user(user)
                .build();
        contentRepository.save(content);
        return ContentResponseDTO.fromEntity(content);
    }

    @Transactional
    public ContentResponseDTO updateContent(ContentUpdateRequestDTO updateRequestDTO, Long contentId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!content.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인 게시글만 수정할 수 있습니다.");
        }

        content.setTitle(updateRequestDTO.getTitle());
        content.setDetail(updateRequestDTO.getDetail());
        contentRepository.save(content);
        return ContentResponseDTO.fromEntity(content);
    }

    @Transactional
    public void deleteContent(Long contentId, Long userId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!content.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인 게시글만 삭제할 수 있습니다.");
        }

        contentRepository.delete(content);
    }
}
