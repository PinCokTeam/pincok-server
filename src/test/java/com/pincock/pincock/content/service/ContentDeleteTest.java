package com.pincock.pincock.content.service;

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
public class ContentDeleteTest {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deleteContent() {
        // 1) 유저 저장
        User user = User.builder()
                .name("섭")
                .nickname("hans")
                .build();
        userRepository.save(user);

        Content content = Content.builder()
                .title("맛집")
                .detail("점심 시간 맛집임")
                .latitude(21312D)
                .longitude(21322D)
                .user(user)
                .build();
        contentRepository.save(content);

        contentService.deleteContent(content.getId(), user.getId());

        boolean exists = contentRepository.existsById(content.getId());
        assertThat(exists).isFalse();
    }
}
