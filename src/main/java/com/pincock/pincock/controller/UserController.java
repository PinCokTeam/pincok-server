package com.pincock.pincock.controller;

import com.pincock.pincock.dto.UserCreateRequestDTO;
import com.pincock.pincock.dto.UserLoginRequestDTO;
import com.pincock.pincock.dto.UserResponseDTO;
import com.pincock.pincock.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    // 유저 생성 (닉네임이 유니크한지 검증)
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userCreateRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> useLogin(@RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpSession session) {
        try {
            UserResponseDTO userResponseDTO = userService.loginUser(userLoginRequestDTO);
            session.setAttribute("loginUser", userResponseDTO);
            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
