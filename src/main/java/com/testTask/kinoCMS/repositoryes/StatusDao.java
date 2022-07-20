package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.movie.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDao extends JpaRepository<Status, Long> {
}
