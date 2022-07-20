package com.testTask.kinoCMS.controllers.admin;

import com.testTask.kinoCMS.entity.news.News;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import com.testTask.kinoCMS.services.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/admin/news")
public class AdminNews {
    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<Collection<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNews(@PathVariable long id) {
        try {
            return ResponseEntity.ok(newsService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable long id) {
        boolean isDeleted = newsService.deleteById(id);
        String response = isDeleted ? "deleteById successful" : "fail deleteById";
        return ResponseEntity.ok(response);
    }
}
