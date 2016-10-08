package by.epam.filmstore.service;

import java.io.IOException;

/**
 * @author Olga Shahray
 */
public interface IFileStoreService {

    String save(byte[] file, String fileStorePath, String path) throws IOException;

    boolean save(byte[] file, String fullPath) throws IOException;

    byte[] get(String fileStorePath, String path) throws IOException;

    byte[] get(String fullPath) throws IOException;

    void delete(String fileStorePath, String path) throws IOException;

    void delete(String fullPath) throws IOException;

}
