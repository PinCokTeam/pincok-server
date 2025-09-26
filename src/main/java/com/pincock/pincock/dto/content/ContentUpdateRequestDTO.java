package com.pincock.pincock.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentUpdateRequestDTO {
    private String title;
    private String detail;
}
