package com.pincock.pincock.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDTO {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    @Size(max = 10 , message = "이름은 10글자 이하로 작성할 수 있습니다.")
    private String name;

    @NotBlank(message = "닉네임은 비어있을 수 없습니다.")
    @Size(max = 10 , message = "닉네임은 10글자 이하로 작성할 수 있습니다.")
    private String nickname;
}
