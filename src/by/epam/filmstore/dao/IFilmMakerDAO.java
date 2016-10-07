package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.FilmMaker;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface IFilmMakerDAO {

    void save(FilmMaker filmMaker) throws DAOException;

    void update(FilmMaker filmMaker) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    // null if not found
    FilmMaker get(int id) throws DAOException;

    List<FilmMaker> getAll(int offset, int count) throws DAOException;

    List<FilmMaker> getAll() throws DAOException;

    int getCountFilmMakers() throws DAOException;
}
