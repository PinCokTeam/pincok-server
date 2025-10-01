package com.pincock.pincock.dto.crew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewRequestDTO {

    private String name;
    private String detail;
    private String imageUrl;
}
