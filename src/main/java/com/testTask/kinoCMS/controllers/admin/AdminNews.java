package com.testTask.kinoCMS.controllers.admin;

import com.testTask.kinoCMS.entity.news.News;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import com.testTask.kinoCMS.services.news.NewsService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/admin/news")

@Tag(name = "Admin News", description = "Access only for the user with the 'admin' role.")
@SecurityRequirement(name = "Session cookie", scopes = {"JSESSIONID"})
@ApiResponse(
        responseCode = "403",
        content = {@Content(schema = @Schema(hidden = true))}
)
public class AdminNews {
    @Autowired
    private NewsService newsService;

    //==================================//
    @Operation(
        summary = "Get list with all news data",
        description = "Use to get data for all news",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            )
        },
        responses = {@ApiResponse(responseCode = "200")}
    )
    @GetMapping
    public ResponseEntity<Collection<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAll());
    }

    //==================================//
    @Operation(
        summary = "Get news data",
        description = "Use to get news data",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Will return news data"),
            @ApiResponse(responseCode = "404")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getNews(@PathVariable long id) {
        try {
            return ResponseEntity.ok(newsService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    //==================================//
    @Operation(
        summary = "Create new news",
        description = "Use to create news data from the server",
        parameters = {
            @Parameter(
                name = "JSESSIONID", description = "Session cookie", in = ParameterIn.COOKIE
            ),
            @Parameter(schema = @Schema(allOf = News.class))
        },
        responses = {
            @ApiResponse(responseCode = "201", description = "Will return id new news"),
            @ApiResponse(responseCode = "409", description = "Will return 'already exist'"),
            @ApiResponse(responseCode = "500", description = "File Save Exception")
        }
    )
    @PostMapping
    public ResponseEntity<Object> createNews(
            @Valid @ModelAttribute News news,
            @Valid @ModelAttribute SeoBlock seoBlock
    ) {
        news.setSeoBlock(seoBlock);
        try {
            News newsInDb = newsService.create(news);
            return ResponseEntity.status(HttpStatus.CREATED).body(newsInDb.getId());
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already exist");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //==================================//
    @Operation(
        summary = "Update news data",
        description = "Use to update news data from the server",
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
    public ResponseEntity<Object> updateNews(
            @Valid @ModelAttribute News news,
            @Valid @ModelAttribute SeoBlock seoBlock
    ) {
        news.setSeoBlock(seoBlock);
        boolean isUpdated = false;
        try {
            isUpdated = newsService.update(news);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        String response = isUpdated ? "update successful" : "fail update";
        return ResponseEntity.ok(response);
    }

    //==================================//
    @Operation(
            summary = "Delete news by id",
            description = "Use to deleteById news data from the server",
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
    public ResponseEntity<String> deleteNews(@PathVariable long id) {
        boolean isDeleted = newsService.deleteById(id);
        String response = isDeleted ? "deleteById successful" : "fail deleteById";
        return ResponseEntity.ok(response);
    }
}
