package com.pincock.pincock.repository;

import com.pincock.pincock.entity.Content_Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentImageRepository extends JpaRepository<Content_Image, Long> {
    List<Content_Image> findByContentId(Long contentId);
}
