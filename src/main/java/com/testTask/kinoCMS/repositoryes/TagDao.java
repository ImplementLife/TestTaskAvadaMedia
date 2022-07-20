package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.movie.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag, Long> {
}
