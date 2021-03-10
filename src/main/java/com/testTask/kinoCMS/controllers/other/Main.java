package com.testTask.kinoCMS.controllers.other;

import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import com.testTask.kinoCMS.services.movies.MovieService;
import com.testTask.kinoCMS.services.news.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")

@Tag(name = "Main")
public class Main {
    @Autowired
    private MovieService movieService;

    @Autowired
    private NewsService newsService;

    @Operation(summary = "Get list movies")
    @GetMapping
    public ResponseEntity<Object> getMainPage() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @Operation(
        summary = "Get movie data",
        responses = {
            @ApiResponse(responseCode = "200", description = "Will return movie data"),
            @ApiResponse(responseCode = "404")
        }
    )
    @GetMapping("/movie/{id}")
    public ResponseEntity<Object> getMovieCard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    @Operation(summary = "Get list news")
    @GetMapping("/aboutCinema/news")
    public ResponseEntity<Object> getInfo() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @Operation(
        summary = "Get news data",
        responses = {
            @ApiResponse(responseCode = "200", description = "Will return news data"),
            @ApiResponse(responseCode = "404")
        }
    )
    @GetMapping("/aboutCinema/news/{id}")
    public ResponseEntity<Object> getNews(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

}
