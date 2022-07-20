package com.testTask.kinoCMS.controllers.other;

import com.testTask.kinoCMS.controllers.DefaultController;
import com.testTask.kinoCMS.entity.movie.dto.MovieResponseDTO;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import com.testTask.kinoCMS.services.movies.MovieService;
import com.testTask.kinoCMS.services.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Main extends DefaultController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/main/movies")
    public ResponseEntity<List<MovieResponseDTO>> getMainPage() {
        return ResponseEntity.ok(movieService.getAllDTO());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Object> getMovieCard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.getDTOById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(super.problem(String.format("Movie with id=%s is not found", id)));
        }
    }


    @GetMapping("/aboutCinema/news")
    public ResponseEntity<Object> getInfo() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/aboutCinema/news/{id}")
    public ResponseEntity<Object> getNews(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

}
