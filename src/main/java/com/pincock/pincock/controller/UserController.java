package com.pincock.pincock.controller;

import com.pincock.pincock.dto.user.UserCreateRequestDTO;
import com.pincock.pincock.dto.user.UserLoginRequestDTO;
import com.pincock.pincock.dto.user.UserResponseDTO;
import com.pincock.pincock.service.UserService;
import com.pincock.pincock.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 유저 생성 (닉네임이 유니크한지 검증)
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userCreateRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> userLogin(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.loginUser(userLoginRequestDTO);

            String token = jwtUtil.generateToken(userResponseDTO.getNickname());

            userResponseDTO.setToken(token);

            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
