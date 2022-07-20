package com.testTask.kinoCMS.services.movies;

import com.testTask.kinoCMS.entity.movie.dto.MovieRequestDTO;
import com.testTask.kinoCMS.entity.movie.dto.MovieResponseDTO;
import com.testTask.kinoCMS.entity.image.Image;
import com.testTask.kinoCMS.entity.movie.Movie;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import com.testTask.kinoCMS.repositoryes.MovieDao;
import com.testTask.kinoCMS.repositoryes.StatusDao;
import com.testTask.kinoCMS.repositoryes.files.FileManager;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private StatusDao statusDao;

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

    //region Convert dto
    private Movie convert(Movie movie, MovieRequestDTO dto) {
        movie.setId(dto.getId());
        movie.setName(dto.getName());
        movie.setMainImage(dto.getMainImage());
        movie.setImages(dto.getImages());

        movie.setDescription(dto.getDescription());
        movie.setTrailerURL(dto.getTrailerURL());
        movie.setShowInMain(dto.isShowInMain());
        movie.setStatus(dto.getStatus());

        movie.setDateCreate(new Date(dto.getDateCreate()));
        movie.setDateUpdate(new Date(dto.getDateUpdate()));
        movie.setDatePublic(new Date(dto.getDatePublic()));
        movie.setDateHide(new Date(dto.getDateHide()));

        movie.setStatus(statusDao.findById(dto.getStatusId())
            .orElseThrow(() -> new RuntimeException(String.format("Status with id=%s not found", dto.getId())))
        );
        movie.setSessions(dto.getSessions());
        if (dto.getSeoBlock() != null) {
            movie.setSeoBlock(dto.getSeoBlock());
        } else {
            movie.setSeoBlock(new SeoBlock());
        }
        return movie;
    }

    private Movie convert(MovieRequestDTO dto) {
        return convert(new Movie(), dto);
    }

    private MovieResponseDTO convert(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();

        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setDescription(movie.getDescription());
        dto.setMainImageName(movie.getMainImageName());

        dto.setImagesNames(movie.getImagesNames());
        dto.setTrailerURL(movie.getTrailerURL());
        dto.setShowInMain(movie.isShowInMain());
        dto.setSeoBlock(movie.getSeoBlock());

        dto.setStatus(movie.getStatus());
        dto.setSessions(movie.getSessions());

        if (movie.getDateCreate() != null) {
            dto.setDateCreate(movie.getDateCreate().getTime());
        }
        if (movie.getDateUpdate() != null) {
            dto.setDateUpdate(movie.getDateUpdate().getTime());
        }
        if (movie.getDatePublic() != null) {
            dto.setDatePublic(movie.getDatePublic().getTime());
        }
        if (movie.getDateHide() != null) {
            dto.setDateHide(movie.getDateHide().getTime());
        }
        return dto;
    }

    //endregion

    public void isValidFilesSize(MovieRequestDTO dto) {
        if (dto.getMainImage().getSize() > 500_000) {
            throw new ValidationException(String.format("Too large [main image] file, can`t be %s > 500 kb", dto.getMainImage().getSize()));
        }
    }

    @Override
    public List<MovieResponseDTO> getAllDTO() {
        return this.getAll().stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO create(MovieRequestDTO dto) throws IOException {
        isValidFilesSize(dto);
        dto.setDateCreate(new Date().getTime());
        return convert(create(convert(dto)));
    }

    @Override
    public Movie create(Movie movie) throws IOException {
        saveFiles(movie);
        return movieDao.save(movie);
    }

    @Override
    public boolean update(MovieRequestDTO dto) throws IOException {
        isValidFilesSize(dto);
        Movie movieInDb = movieDao.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException(String.format("Movie with id=%s not found", dto.getId())));
        dto.setDateCreate(movieInDb.getDateCreate().getTime());
        dto.setDateUpdate(new Date().getTime());
        return this.update(convert(movieInDb, dto));
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
    public MovieResponseDTO getDTOById(Long id) throws NotFoundException {
        return convert(initPathImages(movieDao.findById(id).orElseThrow(NotFoundException::new)));
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
