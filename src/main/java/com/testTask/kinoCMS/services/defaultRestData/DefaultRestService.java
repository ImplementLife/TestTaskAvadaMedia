package com.testTask.kinoCMS.services.defaultRestData;

import java.io.IOException;
import java.util.Collection;

public interface DefaultRestService<T> {
    T create(T news) throws IOException, AlreadyExistException;

    boolean update(T news) throws IOException;

    T getById(Long id) throws NotFoundException;

    Collection<T> getAll();

    boolean deleteById(Long id);
}
