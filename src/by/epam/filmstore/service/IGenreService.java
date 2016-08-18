package by.epam.filmstore.service;

import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public interface IGenreService {

    List<Genre> getAll() throws ServiceException;
}
