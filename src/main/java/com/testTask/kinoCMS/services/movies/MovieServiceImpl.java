package com.testTask.kinoCMS.services.movies;

import com.testTask.kinoCMS.entity.movie.Movie;
import com.testTask.kinoCMS.repositoryes.MovieDao;
import com.testTask.kinoCMS.repositoryes.files.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    private FileManager fileManager;

    private void saveFiles(Movie movie) throws IOException {
        MultipartFile mainImage = movie.getMainImage();
        if (mainImage != null) movie.setMainImageName(fileManager.save(mainImage));

        MultipartFile[] images = movie.getImages();
        if (images != null) movie.setImagesNames(fileManager.save(images));
    }

    @Override
    public Movie create(Movie movie) throws IOException {
        saveFiles(movie);
        return movieDao.save(movie);
    }

    @Override
    public boolean update(Movie movie) throws IOException {
        if (!movieDao.existsById(movie.getId())) return false;
        saveFiles(movie);
        movieDao.save(movie);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        if (movieDao.existsById(id)) {
            Movie movie = getById(id);
            fileManager.delete(movie.getMainImageName());
            fileManager.delete(movie.getImagesNames());
            movieDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Movie getById(Long id) {
        return movieDao.getOne(id);
    }

    @Override
    public Collection<Movie> getAll() {
        return movieDao.findAll();
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
