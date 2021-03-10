package com.testTask.kinoCMS.repositoryes.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManager {

    String save(MultipartFile file) throws IOException;

    String[] save(MultipartFile[] file) throws IOException;

    /**
     * @param pathFile name file for delete
     * @return success delete
     */
    boolean delete(String pathFile);

    /**
     * @param pathFiles names files for delete
     * @return names files that not success delete
     */
    String[] delete(String[] pathFiles);
}
