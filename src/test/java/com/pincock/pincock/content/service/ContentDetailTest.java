package com.pincock.pincock.content.service;

import com.pincock.pincock.dto.content.ContentResponseDTO;
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
@ActiveProfiles("test")  // application-test.yml 사용
@Transactional          // 테스트 끝나면 롤백
public class ContentDetailTest {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentService contentService;

    @Test
    void contentDetailTest() {
        // 1) 유저 저장
        User user = User.builder()
                .name("섭")
                .nickname("hans")
                .build();
        userRepository.save(user);

        // 2) 컨텐츠 저장
        Content content = Content.builder()
                .title("커피 맛집")
                .detail("가격 저렴하고 맛좋은 커피 맛집")
                .longitude(123456D)
                .latitude(654321D)
                .user(user)
                .build();
        contentRepository.save(content);

        // 3) 서비스 호출
        ContentResponseDTO responseDTO = contentService.contentDetail(user.getId(), content.getId());

        // 4) 검증
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getTitle()).isEqualTo("커피 맛집");
        assertThat(responseDTO.getDetail()).isEqualTo("가격 저렴하고 맛좋은 커피 맛집");
    }
}
