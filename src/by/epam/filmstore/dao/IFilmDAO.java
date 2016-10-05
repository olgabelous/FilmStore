package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.Genre;

import java.util.List;
import java.util.Map;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public interface IFilmDAO {

    void save(Film film) throws DAOException;

    void saveFilmGenres(int filmId, List<Genre> genres) throws DAOException;

    void saveFilmMakers(int filmId, List<FilmMaker> filmMakers) throws DAOException;

    void update(Film film) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    //only for testing
    boolean deleteByTitle(String title) throws DAOException;

    // null if not found
    Film get(int id) throws DAOException;

    List<Film> getFilteredFilms(Map<String, List<String>> filterParams, String orderBy, int offset, int count)  throws DAOException;

    List<Film> getByYear(int year, int offset, int count) throws DAOException;

    List<Genre> getAllGenresOfFilm(int filmId) throws DAOException;

    List<FilmMaker> getMakersOfFilm(int filmId) throws DAOException;

    int getCountFilm(Map<String, List<String>> filterParams) throws DAOException;

    List<Film> getFavoriteFilms(int id, int offset, int count) throws DAOException;

    int getCountFavoriteFilm(int id) throws DAOException;

    void saveFavoriteFilm(int userId, int filmId) throws DAOException;

    boolean deleteFavoriteFilm(int userId, int filmId) throws DAOException;

    List<Film> search(String[] keywords) throws DAOException;

    boolean isFavoriteFilm(int userId, int filmId)throws DAOException;
}
