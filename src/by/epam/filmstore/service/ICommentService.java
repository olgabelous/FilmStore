package by.epam.filmstore.service;

import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface ICommentService {

    void save(User user, int filmId, int mark, String commentText) throws ServiceException;

    void update(int id, CommentStatus status)  throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Comment> getAllOfUser(int userId) throws ServiceException;

    PagingListDTO<Comment> getByStatus(CommentStatus status, int offset, int count) throws ServiceException;

    int getCount(CommentStatus status) throws ServiceException;
}
