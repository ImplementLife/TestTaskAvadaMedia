package com.testTask.kinoCMS.services.news;

import com.testTask.kinoCMS.entity.news.News;
import com.testTask.kinoCMS.repositoryes.NewsDao;
import com.testTask.kinoCMS.repositoryes.files.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    private FileManager fileManager;

    private void saveFiles(News news) throws IOException {
        MultipartFile mainImage = news.getMainImage();
        if (mainImage != null) news.setMainImageName(fileManager.save(mainImage));

        MultipartFile[] images = news.getImages();
        if (images != null) news.setImagesNames(fileManager.save(images));
    }

    @Override
    public News create(News news) throws IOException {
        saveFiles(news);
        return newsDao.save(news);
    }

    @Override
    public boolean update(News news) throws IOException {
        if (!newsDao.existsById(news.getId())) return false;
        saveFiles(news);
        newsDao.save(news);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        if (newsDao.existsById(id)) {
            News news = getById(id);
            fileManager.delete(news.getMainImageName());
            fileManager.delete(news.getImagesNames());
            newsDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public News getById(Long id) {
        return newsDao.getOne(id);
    }

    @Override
    public Collection<News> getAll() {
        return newsDao.findAll();
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
