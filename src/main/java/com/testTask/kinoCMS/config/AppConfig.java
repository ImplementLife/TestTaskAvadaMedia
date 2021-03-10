package com.testTask.kinoCMS.config;

import com.testTask.kinoCMS.repositoryes.files.ImageFileManagerImpl;
import com.testTask.kinoCMS.services.movies.MovieService;
import com.testTask.kinoCMS.services.movies.MovieServiceImpl;
import com.testTask.kinoCMS.services.news.NewsService;
import com.testTask.kinoCMS.services.news.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MovieService getMovieService(@Autowired ImageFileManagerImpl imageFileManager) {
        MovieServiceImpl movieService = new MovieServiceImpl();
        movieService.setFileManager(imageFileManager);
        return movieService;
    }

    @Bean
    public NewsService getNewsService(@Autowired ImageFileManagerImpl imageFileManager) {
        NewsServiceImpl newsService = new NewsServiceImpl();
        newsService.setFileManager(imageFileManager);
        return newsService;
    }
}
