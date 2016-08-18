package by.epam.filmstore.service;

import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public interface IFilmMakerService {

    void save(FilmMaker filmMaker) throws ServiceException;

    // false if not found
    boolean delete(int id) throws ServiceException;

    // null if not found
    FilmMaker get(int id) throws ServiceException;

    List<FilmMaker> getAll(String order, int limit) throws ServiceException;
}
