package by.epam.filmstore.service;

import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public interface IFilmService {

    void save(Film film) throws ServiceException;

    Film get(int id) throws ServiceException;

    List<Film> getByGenre(String genre) throws ServiceException;

    List<Film> getByYear(String year) throws ServiceException;

    List<Film> getAll() throws ServiceException;

}
