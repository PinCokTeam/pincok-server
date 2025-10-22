package com.pincock.pincock.contentimage.service;

import com.pincock.pincock.dto.image.ContentImageResponseDTO;
import com.pincock.pincock.entity.Content;
import com.pincock.pincock.entity.Content_Image;
import com.pincock.pincock.entity.ImageStatus;
import com.pincock.pincock.entity.User;
import com.pincock.pincock.repository.ContentImageRepository;
import com.pincock.pincock.repository.ContentRepository;
import com.pincock.pincock.repository.UserRepository;
import com.pincock.pincock.service.ContentImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ImageGetTest {

    @Autowired
    private ContentImageService contentImageService;

    @Autowired
    private ContentImageRepository contentImageRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getImage() {
        User user = User.builder()
                .name("섭")
                .nickname("hans")
                .build();
        userRepository.save(user);

        Content content = Content.builder()
                .title("국밥")
                .detail("점심엔 국밥")
                .latitude(123123D)
                .longitude(213123D)
                .user(user)
                .build();
        contentRepository.save(content);

        Content_Image contentImage = Content_Image.builder()
                .content(content)
                .imageUrl("https://example.com/image1.jpg")
                .titleImage(ImageStatus.TITLE)
                .build();

        Content_Image contentImage2 = Content_Image.builder()
                .content(content)
                .imageUrl("https://example.com/image2.jpg")
                .titleImage(ImageStatus.NORMAL)
                .build();

        Content_Image contentImage3 = Content_Image.builder()
                .content(content)
                .imageUrl("https://example.com/image3.jpg")
                .titleImage(ImageStatus.NORMAL)
                .build();
        contentImageRepository.save(contentImage);
        contentImageRepository.save(contentImage2);
        contentImageRepository.save(contentImage3);

        List<ContentImageResponseDTO> responseDTOs = contentImageService.getImage(content.getId());

        assertThat(responseDTOs.size()).isEqualTo(3);
        assertThat(responseDTOs.getFirst().getTitleImage()).isEqualTo(ImageStatus.TITLE);
    }
}
