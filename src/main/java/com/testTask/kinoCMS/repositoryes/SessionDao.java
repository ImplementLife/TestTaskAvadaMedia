package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.movie.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDao extends JpaRepository<Session, Long> {
}
