package by.epam.filmstore.service;

import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public interface IFilmService {

    void save(String[] genres, String[] filmMakers, Object... params) throws ServiceException;

    void update(int id, String[] genres, String[] filmMakers, Object... params) throws ServiceException;

    Film get(int id) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Film> getByGenre(String genre) throws ServiceException;

    List<Film> getByYear(String year, int offset, int count) throws ServiceException;

    //int countFilms()  throws ServiceException;

    List<Film> getAll(String order, int limit) throws ServiceException;

}
