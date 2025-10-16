package com.pincock.pincock.user.service;

import com.pincock.pincock.dto.UserCreateRequestDTO;
import com.pincock.pincock.dto.UserResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("✅ 닉네임이 중복되지 않으면 유저를 정상 생성한다")
    void createUser_Success() {
        // given
        UserCreateRequestDTO request = new UserCreateRequestDTO("한서", "hans");
        given(userRepository.existsByNickname("hans")).willReturn(false);

        User savedUser = User.builder()
                .id(1L)
                .name("한서")
                .nickname("hans")
                .build();

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // when
        UserResponseDTO response = userService.createUser(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("한서");
        assertThat(response.getNickname()).isEqualTo("hans");

        verify(userRepository, times(1)).existsByNickname("hans");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("❌ 닉네임이 중복되면 예외를 던진다")
    void createUser_Fail_DuplicateNickname() {
        // given
        UserCreateRequestDTO request = new UserCreateRequestDTO("한서", "hans");
        given(userRepository.existsByNickname("hans")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("이미 사용 중인 닉네임입니다.");

        verify(userRepository, times(1)).existsByNickname("hans");
        verify(userRepository, never()).save(any(User.class));
    }
}
