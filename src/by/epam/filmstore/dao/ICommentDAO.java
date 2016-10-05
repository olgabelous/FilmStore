package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;

import java.util.List;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public interface ICommentDAO {

    void save(Comment comment) throws DAOException;

    void update(int id, CommentStatus status) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    // null if not found
    Comment get(int id) throws DAOException;

    List<Comment> getAllOfUser(int userId) throws DAOException;

    List<Comment> getAllOfFilm(int filmId) throws DAOException;

    List<Comment> getByStatus(CommentStatus status, int offset, int count) throws DAOException;

    int getCount(CommentStatus status) throws DAOException;
}
