package com.pincock.pincock.dto.crew;


import com.pincock.pincock.entity.Crew;
import com.pincock.pincock.entity.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrewUpdateResponseDTO {

    private Long id;
    private String name;
    private String detail;
    private String imageUrl;
    private Long leader_id;

    public static CrewUpdateResponseDTO fromEntity(Crew crew, CrewMember leader) {
        return CrewUpdateResponseDTO.builder()
                .id(crew.getId())
                .name(crew.getName())
                .detail(crew.getDetail())
                .imageUrl(crew.getImageUrl())
                .leader_id(leader.getUser().getId())
                .build();
    }
}
