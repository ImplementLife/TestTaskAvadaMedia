package com.testTask.kinoCMS.services.movies;

import com.testTask.kinoCMS.entity.image.Image;
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
        if (images != null) {
            movie.setImagesNames(fileManager.save(images));
            Image[] imagesPaths = new Image[movie.getImagesNames().length];
            for (int i = 0; i < imagesPaths.length; i++) {
                imagesPaths[i] = new Image();
                imagesPaths[i].setName(movie.getImagesNames()[i]);
            }
            movie.setImagesPaths(imagesPaths);
        }
    }

    private Movie initPathImages(Movie movie) {
        Image[] images = movie.getImagesPaths();
        String[] namesImages = new String[images.length];
        for (int i = 0; i < images.length; i++) namesImages[i] = images[i].getName();
        movie.setImagesNames(namesImages);
        return movie;
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
        return initPathImages(movieDao.getOne(id));
    }

    @Override
    public Collection<Movie> getAll() {
        Collection<Movie> result = movieDao.findAll();
        for (Movie movie : result) initPathImages(movie);
        return result;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
