package com.pincock.pincock.content.service;

import com.pincock.pincock.dto.content.ContentResponseDTO;
import com.pincock.pincock.dto.content.ContentUpdateRequestDTO;
import com.pincock.pincock.entity.Content;
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
public class ContentUpdateTest {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void updateContent() {
        // 1) 유저 저장
        User user = User.builder()
                .name("섭")
                .nickname("hans")
                .build();
        userRepository.save(user);

        Content content = Content.builder()
                .title("신발 파는곳")
                .detail("캐쥬얼 신발 팔아요")
                .latitude(123123D)
                .longitude(123123D)
                .user(user)
                .build();
        contentRepository.save(content);
        String contentTitle = content.getTitle();
        ContentUpdateRequestDTO updateRequest = ContentUpdateRequestDTO.builder()
                .title("악세 서리 파는곳")
                .detail("신발도 있고 다양하게 많아요")
                .build();

        ContentResponseDTO responseDTO = contentService.updateContent(updateRequest, content.getId(), user.getId());

        assertThat(responseDTO.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(responseDTO.getTitle()).isNotEqualTo(contentTitle);

    }
}
