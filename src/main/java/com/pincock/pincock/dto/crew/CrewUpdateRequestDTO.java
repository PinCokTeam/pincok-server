package com.pincock.pincock.dto.crew;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewUpdateRequestDTO {
    private String name;
    private String detail;
    private String imageUrl;
    private Long newLeaderId;
}
