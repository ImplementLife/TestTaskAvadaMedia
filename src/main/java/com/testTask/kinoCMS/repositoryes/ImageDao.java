package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Long> {
}
