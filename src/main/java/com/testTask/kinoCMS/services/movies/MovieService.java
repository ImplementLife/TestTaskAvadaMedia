package com.testTask.kinoCMS.services.movies;

import com.testTask.kinoCMS.entity.movie.dto.MovieRequestDTO;
import com.testTask.kinoCMS.entity.movie.dto.MovieResponseDTO;
import com.testTask.kinoCMS.entity.movie.Movie;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import com.testTask.kinoCMS.services.defaultRestData.DefaultRestService;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface MovieService extends DefaultRestService<Movie> {
    MovieResponseDTO create(MovieRequestDTO dto) throws IOException, AlreadyExistException;
    boolean update(MovieRequestDTO dto) throws IOException;
    List<MovieResponseDTO> getAllDTO();

    MovieResponseDTO getDTOById(Long id) throws NotFoundException;
}
