package by.epam.filmstore.service.impl;

import by.epam.filmstore.service.IFileStoreService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class encapsulates the business logic of file managing.
 *
 * @author Olga Shahray
 */
public class FileStoreServiceImpl implements IFileStoreService {

    /**
     * Method saves given file by @param fileStorePath and @param fileName
     * @param file to save
     * @param fileStorePath - path to save
     * @param fileName - name of file
     * @return name of saved file
     * @throws IOException
     */
    @Override
    public String save(byte[] file, String fileStorePath, String fileName) throws IOException {
        Files.write(Paths.get(fileStorePath, fileName), file);
        return Paths.get(fileName).toString();
    }

    /**
     * Method saves given file by @param fullPath
     * @param file to save
     * @param fullPath - full path to save file
     * @return boolean result if file was saved
     * @throws IOException
     */
    @Override
    public boolean save(byte[] file, String fullPath) throws IOException {
        Files.write(Paths.get(fullPath), file);
        return true;
    }

    /**
     * Method returns file by @param fileStorePath and @param fileName
     * @param fileStorePath - path where file was saved
     * @param fileName - name of file
     * @return file
     * @throws IOException
     */
    @Override
    public byte[] get(String fileStorePath, String fileName) throws IOException {

        return Files.readAllBytes(Paths.get(fileStorePath, fileName));
    }

    /**
     * Method returns file by @param fullPath
     * @param fullPath - path where file was saved including file name
     * @return file
     * @throws IOException
     */
    @Override
    public byte[] get(String fullPath) throws IOException {

        return Files.readAllBytes(Paths.get(fullPath));
    }

    /**
     * Method removes file by @param fileStorePath and @param fileName
     * @param fileStorePath - path where file was saved
     * @param fileName - name of file
     * @throws IOException
     */
    @Override
    public void delete (String fileStorePath, String fileName) throws IOException {

        Files.delete(Paths.get(fileStorePath, fileName));
    }

    /**
     * Method removes file by @param fullPath
     * @param fullPath - path where file was saved including file name
     * @throws IOException
     */
    @Override
    public void delete(String fullPath) throws IOException {

        Files.delete(Paths.get(fullPath));
    }

}
