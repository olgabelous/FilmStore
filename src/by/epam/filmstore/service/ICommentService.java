package by.epam.filmstore.service;

import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public interface ICommentService {

    void save(User user, int filmId, int mark, String commentText) throws ServiceException;

    void update(int filmId, int userId, CommentStatus status)  throws ServiceException;

    boolean delete(int userId, int filmId) throws ServiceException;

    List<Comment> getAllOfUser(int userId) throws ServiceException;

    List<Comment> getByStatus(CommentStatus status) throws ServiceException;
}
