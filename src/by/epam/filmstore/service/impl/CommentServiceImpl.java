package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public class CommentServiceImpl implements ICommentService {

    @Override
    public boolean delete(int userId, int filmId) throws ServiceException {
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        if(userId <= 0 || filmId <= 0){
            throw new ServiceException("Id must be positive number!");
        }
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
    public List<Comment> getByStatus(String status) throws ServiceException {
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.getByStatus(status));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
