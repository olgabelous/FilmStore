package by.epam.filmstore.service;

import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public interface ICommentService {

    boolean delete(int userId, int filmId) throws ServiceException;

    List<Comment> getAllOfUser(int userId) throws ServiceException;

    List<Comment> getByStatus(String status) throws ServiceException;
}
