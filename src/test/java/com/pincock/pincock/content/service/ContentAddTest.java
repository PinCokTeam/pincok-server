package com.pincock.pincock.content.service;

import com.pincock.pincock.dto.content.ContentCreateRequestDTO;
import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.ContentRepository;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ContentAddTest {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void addContent() {
        // 1) 유저 저장
        User user = User.builder()
                .name("섭")
                .nickname("hans")
                .build();
        userRepository.save(user);

        ContentCreateRequestDTO contentCreateRequestDTO = ContentCreateRequestDTO.builder()
                .title("횟집")
                .detail("광어 회 맛집")
                .latitude(2131213D)
                .longitude(1241421D)
                .build();

        ContentResponseDTO responseDTO = contentService.addContent(contentCreateRequestDTO, user.getId());

        assertThat(responseDTO.getTitle()).isEqualTo(contentCreateRequestDTO.getTitle());
        assertThat(responseDTO.getDetail()).isEqualTo(contentCreateRequestDTO.getDetail());
    }

}
