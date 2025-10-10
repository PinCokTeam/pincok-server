package com.pincock.pincock.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequestDTO {
    private Long id;
    private String nickname;
}
