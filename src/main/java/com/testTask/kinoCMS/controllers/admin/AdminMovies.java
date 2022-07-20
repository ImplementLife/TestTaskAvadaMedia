package com.testTask.kinoCMS.controllers.admin;

import com.testTask.kinoCMS.controllers.DefaultController;
import com.testTask.kinoCMS.entity.movie.Status;
import com.testTask.kinoCMS.entity.movie.dto.MovieRequestDTO;
import com.testTask.kinoCMS.entity.movie.dto.MovieResponseDTO;
import com.testTask.kinoCMS.repositoryes.StatusDao;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import com.testTask.kinoCMS.services.movies.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/admin/movies")
public class AdminMovies extends DefaultController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private StatusDao statusDao;

    @GetMapping("/statuses")
    public ResponseEntity<Object> getStatuses() {
        return ResponseEntity.ok(statusDao.findAll());
    }
    @PostMapping("/statuses")
    public ResponseEntity<Object> createStatuses(@ModelAttribute Status dto) {
        Status status = new Status();
        status.setName(dto.getName());
        status.setDefaultStatus(dto.isDefaultStatus());
        status = statusDao.saveAndFlush(status);
        return ResponseEntity.ok(super.just("statusId", status.getId()));
    }

    @GetMapping
    public ResponseEntity<Collection<MovieResponseDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMovie(@PathVariable long id) {
        try {
            return ResponseEntity.ok(movieService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(super.problem("Data not found"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createMovie(
        @ModelAttribute MovieRequestDTO movie
    ) {
        try {
            MovieResponseDTO movieInDb = movieService.create(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(super.just("movieId", movieInDb.getId()));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(super.problem("Already exist"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateMovie(
            @Valid @ModelAttribute MovieRequestDTO movie
    ) {
        try {
            boolean isUpdated = false;
            isUpdated = movieService.update(movie);
            String response = isUpdated ? "update successful" : "fail update";
            return ResponseEntity.ok(super.just("", response));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable long id) {
        boolean isDeleted = movieService.deleteById(id);
        String response = isDeleted ? "deleteById successful" : "fail deleteById";
        return ResponseEntity.ok(response);
    }
}
