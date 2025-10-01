package com.pincock.pincock.dto.crew;

import com.pincock.pincock.entity.Crew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrewCreateResponseDTO {

    private Long id;
    private String name;
    private String detail;
    private String imageUrl;
    private Long userId;

    public static CrewCreateResponseDTO fromEntity(Crew crew, Long userId) {
        return CrewCreateResponseDTO.builder()
                .id(crew.getId())
                .name(crew.getName())
                .detail(crew.getDetail())
                .imageUrl(crew.getImageUrl())
                .userId(userId)
                .build();
    }
}
