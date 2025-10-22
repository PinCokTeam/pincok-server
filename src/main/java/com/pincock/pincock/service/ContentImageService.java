package com.pincock.pincock.service;

import com.pincock.pincock.dto.image.ContentImageRequestDTO;
import com.pincock.pincock.dto.image.ContentImageResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.Content_Image;
import com.pincock.pincock.repository.ContentImageRepository;
import com.pincock.pincock.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentImageService {

    private final ContentRepository contentRepository;

    private final ContentImageRepository contentImageRepository;

    public List<ContentImageResponseDTO> getImage(Long contentId) {
        List<Content_Image> images = contentImageRepository.findByContentId(contentId);
        return images.stream()
                .map(ContentImageResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ContentImageResponseDTO> addImages(Long contentId, List<ContentImageRequestDTO> contentImageRequestDTOs) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        List<Content_Image> images = contentImageRequestDTOs.stream()
                .map(dto -> Content_Image.builder()
                        .imageUrl(dto.getImageUrl())
                        .titleImage(dto.getTitleImage())
                        .content(content)
                        .build())
                .toList();

        contentImageRepository.saveAll(images);

        return images.stream()
                .map(ContentImageResponseDTO::fromEntity)
                .toList();
    }
}
