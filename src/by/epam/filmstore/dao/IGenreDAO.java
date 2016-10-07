package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Genre;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface IGenreDAO {

    void save(Genre genre) throws DAOException;

    void update(Genre genre) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    List<Genre> getAll() throws DAOException;

}
