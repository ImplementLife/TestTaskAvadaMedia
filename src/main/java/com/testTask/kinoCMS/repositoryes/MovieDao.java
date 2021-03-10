package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<Movie, Long> {
}
