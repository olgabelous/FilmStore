package by.epam.filmstore.service.impl;

import by.epam.filmstore.service.IFileStoreService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Olga Shahray on 07.09.2016.
 */
public class FileStoreServiceImpl implements IFileStoreService {
    @Override
    public String save(byte[] file, String fileStorePath, String fileName) throws IOException {
        Files.write(Paths.get(fileStorePath, fileName), file);
        return Paths.get(fileName).toString();
    }

    @Override
    public boolean save(byte[] file, String fullPath) throws IOException {
        Files.write(Paths.get(fullPath), file);
        return true;
    }

    @Override
    public byte[] get(String fileStorePath, String fileName) throws IOException {

        return Files.readAllBytes(Paths.get(fileStorePath, fileName));
    }

    @Override
    public byte[] get(String fullPath) throws IOException {

        return Files.readAllBytes(Paths.get(fullPath));
    }

    @Override
    public void delete (String fileStorePath, String fileName) throws IOException {

        Files.delete(Paths.get(fileStorePath, fileName));
    }

    @Override
    public void delete(String fullPath) throws IOException {

        Files.delete(Paths.get(fullPath));
    }

    /*@Override
    public String save(OutputStream file, String fileStorePath, String fileName) throws IOException {

        //Files.write(Paths.get(fileStorePath, fileName), file);
        Files.copy(Paths.get(fileStorePath, fileName), file);
        return Paths.get(fileName).toString();
    }

    @Override
    public InputStream get(String fileStorePath, String path) throws IOException {

        return Files.newInputStream(Paths.get(fileStorePath, path));

    }*/
}
