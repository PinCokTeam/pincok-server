package com.pincock.pincock.dto.crew;

import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.Crew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrewResponseDTO {

    private Long id;
    private String name;
    private String detail;
    private String imageUrl;

    public static CrewResponseDTO fromEntity(Crew crew) {
        return CrewResponseDTO.builder()
                .id(crew.getId())
                .name(crew.getName())
                .detail(crew.getDetail())
                .imageUrl(crew.getImageUrl())
                .build();
    }
}
