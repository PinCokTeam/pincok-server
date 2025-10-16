package com.pincock.pincock.service;

import com.pincock.pincock.dto.user.UserCreateRequestDTO;
import com.pincock.pincock.dto.user.UserLoginRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(UserCreateRequestDTO requestDTO) {

        // 1) 닉네임 중복 검증
        if (userRepository.existsByNickname(requestDTO.getNickname())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 사용 중인 닉네임입니다."
            );
        }

        User user = User.builder()
                .name(requestDTO.getName())
                .nickname(requestDTO.getNickname())
                .build();
        User saved = userRepository.save(user);

        return UserResponseDTO.fromEntity(saved);
    }

    public UserResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO) {
        Long userId = userLoginRequestDTO.getId();

        if (!userRepository.existsByNickname(userLoginRequestDTO.getNickname())) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return UserResponseDTO.fromEntity(user);
    }
}
