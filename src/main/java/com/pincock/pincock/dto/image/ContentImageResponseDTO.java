package com.pincock.pincock.dto.image;

import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.Content_Image;
import com.pincock.pincock.entity.ImageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentImageResponseDTO {

    private Long id;
    private Content content;
    private String imageUrl;
    private ImageStatus titleImage;

    public static ContentImageResponseDTO fromEntity(Content_Image contentImage) {
        return ContentImageResponseDTO.builder()
                .id(contentImage.getId())
                .content(contentImage.getContent())
                .imageUrl(contentImage.getImageUrl())
                .titleImage(contentImage.getTitleImage())
                .build();
    }
}
