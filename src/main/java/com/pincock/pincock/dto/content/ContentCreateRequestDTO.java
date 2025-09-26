package com.pincock.pincock.dto.content;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentCreateRequestDTO {
    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
}
