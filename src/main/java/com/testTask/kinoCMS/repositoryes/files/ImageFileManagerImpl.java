package com.testTask.kinoCMS.repositoryes.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

@Component
public class ImageFileManagerImpl implements FileManager {
    @Value("${path.images}")
    private String pathImages;
    private int num = 1;
    private boolean single = true;

    @Override
    public String[] save(MultipartFile[] files) throws IOException {
        String[] result = new String[files.length];
        synchronized (this) {
            single = false;
            num = 1;
            for (int i = 0; i < files.length; i++) {
                num = i;
                result[i] = save(files[i]);
            }
            single = true;
        }
        return result;
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String name = "";
            try {
                String exe = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                if (single) {
                    name = System.nanoTime() + exe;
                } else {
                    name = System.nanoTime() + "(" + num + ")" + exe;
                }
                File downFile = new File(pathImages + name);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(downFile));
                stream.write(bytes);
                stream.close();
                return name;
            } catch (IOException e) {
                String exceptionMessage = String.format("Ошибка записи файла '%s', new name: '%s', PATH: '%s';",
                        file.getOriginalFilename(), name, pathImages);
                throw new IOException(exceptionMessage);
            }
        } else {
            throw new IOException("Файл: " + file.getOriginalFilename() + " пустой");
        }
    }

    @Override
    public boolean delete(String pathFile) {
        File file = new File(pathImages + pathFile);
        return file.delete();
    }

    @Override
    public String[] delete(String[] pathFiles) {
        LinkedList<String> res = new LinkedList<>();
        for (String s : pathFiles) if (!delete(s)) res.addLast(s);
        return res.toArray(new String[0]);
    }
}
