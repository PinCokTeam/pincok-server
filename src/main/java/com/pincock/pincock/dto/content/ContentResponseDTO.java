package com.pincock.pincock.dto.content;

import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.Content_Image;
import com.pincock.pincock.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentResponseDTO {

    private Long id;
    private String title;
    private String detail;
    private Double latitude;
    private Double longitude;

    public static ContentResponseDTO fromEntity(Content content) {
        return ContentResponseDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .detail(content.getDetail())
                .latitude(content.getLatitude())
                .longitude(content.getLongitude())
                .build();
    }
}