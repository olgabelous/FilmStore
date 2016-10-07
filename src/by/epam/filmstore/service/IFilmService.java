package by.epam.filmstore.service;

import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public interface IFilmService {

    void save(String[] genres, String[] filmMakers, Object... params) throws ServiceException;

    void update(int id, String[] genres, String[] filmMakers, Object... params) throws ServiceException;

    Film get(int id) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Film> getByYear(int year, int offset, int count) throws ServiceException;

    PagingListDTO<Film> getFilteredFilms(Map<String, List<String>> filterParams, String order, int offset, int count)  throws ServiceException;

    PagingListDTO<Film> getAll(String order, int offset, int count) throws ServiceException;

    PagingListDTO<Film> getFavoriteFilms(int id, int offset, int count) throws ServiceException;

    void saveFavoriteFilm(int id, int filmId) throws ServiceException;

    boolean deleteFavoriteFilm(int id, int filmId) throws ServiceException;

    List<Film> search(String searchLine)throws ServiceException;

    boolean isFavoriteFilm(int id, int filmId) throws ServiceException;
}
