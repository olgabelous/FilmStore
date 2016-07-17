package by.epam.filmstore.service;

import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.exception.ServiceException;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public interface IFilmService {

    void save(Film film) throws ServiceException;

}
