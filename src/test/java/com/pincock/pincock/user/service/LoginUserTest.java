package com.pincock.pincock.user.service;

import com.pincock.pincock.dto.user.UserLoginRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class LoginUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void login_success() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO("hans");

        User user = User.builder()
                .id(1L)
                .name("섭")
                .nickname("hans")
                .build();

        // findByNickname을 Mocking
        given(userRepository.findByNickname("hans")).willReturn(Optional.of(user));

        UserResponseDTO responseDTO = userService.loginUser(userLoginRequestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getNickname()).isEqualTo("hans");
    }


    @Test
    void login_userNotFound() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO("hans");

        given(userRepository.findByNickname("hans")).willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser(userLoginRequestDTO)
        );
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 사용자입니다.");
    }


    @Test
    void login_nicknameNotFound() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO("hans");

        given(userRepository.findByNickname("hans")).willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser(userLoginRequestDTO)
        );

        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 사용자입니다.");
    }

}
