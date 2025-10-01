package com.pincock.pincock.dto.image;

import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.ImageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentImageRequestDTO {

    private String imageUrl;
    private ImageStatus titleImage;
}
