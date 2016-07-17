package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Comment;

import java.util.List;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public interface ICommentDAO {

    void save(int userId, int filmId, Comment comment) throws DAOException;

    void update(Comment comment) throws DAOException;

    // false if not found
    boolean delete(int userId, int filmId) throws DAOException;

    // null if not found
    Comment get(int userId, int filmId) throws DAOException;

    List<Comment> getAllOfUser(int userId) throws DAOException;

    List<Comment> getAllOfFilm(int filmId) throws DAOException;
}
