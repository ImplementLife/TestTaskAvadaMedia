package com.testTask.kinoCMS.controllers.admin;

import com.testTask.kinoCMS.entity.movie.Movie;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import com.testTask.kinoCMS.services.movies.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/admin/movies")

@Tag(name = "Admin Movies", description = "Access only for user with the 'admin' role.")
@SecurityRequirement(name = "Session cookie", scopes = {"JSESSIONID"})
@ApiResponse(
    responseCode = "403",
    content = {@Content(schema = @Schema(hidden = true))}
)
public class AdminMovies {
    @Autowired
    private MovieService movieService;

    //==================================//
    @Operation(
        summary = "Get list with all movies data",
        description = "Use to get data for all movies",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            )
        },
        responses = {@ApiResponse(responseCode = "200")}
    )
    @GetMapping
    public ResponseEntity<Collection<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAll());
    }

    //==================================//
    @Operation(
        summary = "Get movie data",
        description = "Use to get movie data",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Will return movie data"),
            @ApiResponse(responseCode = "404")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMovie(@PathVariable long id) {
        try {
            return ResponseEntity.ok(movieService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    //==================================//
    @Operation(
        summary = "Create new movie",
        description = "Use to create movie data from the server",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            ),
            @Parameter(schema = @Schema(allOf = Movie.class)),
        },
        responses = {
            @ApiResponse(responseCode = "201", description = "Will return id new movie"),
            @ApiResponse(responseCode = "409", description = "Will return 'already exist'"),
            @ApiResponse(responseCode = "500", description = "File Save Exception")
        }
    )
    @PostMapping
    public ResponseEntity<Object> createMovie(
            @Valid @ModelAttribute Movie movie,
            @Valid @ModelAttribute SeoBlock seoBlock
    ) {
        movie.setSeoBlock(seoBlock);
        try {
            Movie movieInDb = movieService.create(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(movieInDb.getId());
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already exist");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //==================================//
    @Operation(
        summary = "Update movie data",
        description = "Use to update movie data from the server",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Will return 'update successful' or 'fail update'"),
            @ApiResponse(responseCode = "500", description = "File Save Exception")
        }
    )
    @PutMapping
    public ResponseEntity<String> updateMovie(
            @Valid @ModelAttribute Movie movie,
            @Valid @ModelAttribute SeoBlock seoBlock
    ) {
        movie.setSeoBlock(seoBlock);
        boolean isUpdated = false;
        try {
            isUpdated = movieService.update(movie);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        String response = isUpdated ? "update successful" : "fail update";
        return ResponseEntity.ok(response);
    }

    //==================================//
    @Operation(
        summary = "Delete movie by id",
        description = "Use to deleteById movie data from the server",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Will return 'deleteById successful' or 'fail deleteById'")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable long id) {
        boolean isDeleted = movieService.deleteById(id);
        String response = isDeleted ? "deleteById successful" : "fail deleteById";
        return ResponseEntity.ok(response);
    }
}
