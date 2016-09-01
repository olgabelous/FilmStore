package by.epam.filmstore.service;

import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public interface IFilmService {

    Film save(String[] genres, String[] filmMakers, Object... param) throws ServiceException;

    Film get(int id) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Film> getByGenre(String genre) throws ServiceException;

    List<Film> getByYear(String year, int limit) throws ServiceException;

    List<Film> getAll(String order, int limit) throws ServiceException;

}
