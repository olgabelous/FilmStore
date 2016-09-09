package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public class CommentServiceImpl implements ICommentService {

    @Override
    public void save(User user, int filmId, int mark, String commentText) throws ServiceException {
        if(ServiceValidation.isNotPositive(filmId) || mark < 1 || mark > 10 ){
            throw new ServiceValidationException("Invalid arguments");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        Film film = new Film();
        film.setId(filmId);
        Comment comment = new Comment(user, film, mark, commentText, LocalDateTime.now(), CommentStatus.NEW);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(user.getId(), filmId, comment);
                return  null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(int filmId, int userId, CommentStatus status) throws ServiceException {
        if(ServiceValidation.isNotPositive(filmId, userId)){
            throw new ServiceValidationException("Invalid arguments");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(filmId, userId, status);
                return  null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int userId, int filmId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId, filmId)){
            throw new ServiceValidationException("Id must be positive number!");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(userId, filmId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> getAllOfUser(int userId) throws ServiceException {
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.getAllOfUser(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> getByStatus(CommentStatus status) throws ServiceException {
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.getByStatus(status));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
