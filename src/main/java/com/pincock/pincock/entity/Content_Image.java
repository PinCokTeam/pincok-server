package com.pincock.pincock.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="content_image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content_Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_image_id")
    private Long id;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "title_image")
    private ImageStatus titleImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;
}
