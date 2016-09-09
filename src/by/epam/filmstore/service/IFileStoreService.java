package by.epam.filmstore.service;

import java.io.IOException;

/**
 * Created by Olga Shahray on 07.09.2016.
 */
public interface IFileStoreService {

    /*String save(OutputStream file, String fileStorePath, String path) throws IOException;//Output

    InputStream get(String fileStorePath, String path) throws IOException;//InputStream*/

    String save(byte[] file, String fileStorePath, String path) throws IOException;//Output

    byte[] get(String fileStorePath, String path) throws IOException;//InputStream

}
