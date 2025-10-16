package com.pincock.pincock.user.service;

import com.pincock.pincock.dto.user.UserCreateRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // application-test.yml 적용
@Transactional          // 테스트 끝나면 롤백
class CreateUserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("✅ 닉네임이 중복되지 않으면 유저를 정상 생성한다")
    void createUser_Success() {
        UserCreateRequestDTO request = new UserCreateRequestDTO("한서", "hans");

        UserResponseDTO response = userService.createUser(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("한서");
        assertThat(response.getNickname()).isEqualTo("hans");

        // 실제 DB에 저장됐는지 확인
        assertThat(userRepository.existsByNickname("hans")).isTrue();
    }

    @Test
    @DisplayName("❌ 닉네임이 중복되면 예외를 던진다")
    void createUser_Fail_DuplicateNickname() {
        // 미리 DB에 저장
        userService.createUser(new UserCreateRequestDTO("한서", "hans"));

        // 동일 닉네임으로 생성 시도
        UserCreateRequestDTO duplicate = new UserCreateRequestDTO("한서2", "hans");

        assertThatThrownBy(() -> userService.createUser(duplicate))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("이미 사용 중인 닉네임입니다.");
    }
}
