package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.news.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDao extends JpaRepository<News, Long> {
}
