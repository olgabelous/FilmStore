package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.domain.dto.PagingListDTO;
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
    private final static String ID_MUST_BE_POSITIVE = "Id must be positive number";

    @Override
    public void save(User user, int filmId, int mark, String commentText) throws ServiceException {
        if(ServiceValidation.isNotPositive(filmId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(mark < 1 || mark > 5 ){
            throw new ServiceValidationException("Mark must be 1 - 5");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        Film film = new Film();
        film.setId(filmId);
        Comment comment = new Comment(user, film, mark, commentText, LocalDateTime.now(), CommentStatus.NEW);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(comment);
                return  null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(int id, CommentStatus status) throws ServiceException {
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(status == null){
            throw new ServiceValidationException("Status must not be null");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(id, status);
                return  null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> getAllOfUser(int userId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.getAllOfUser(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PagingListDTO<Comment> getByStatus(CommentStatus status, int offset, int count) throws ServiceException {
        if(status == null){
            throw new ServiceValidationException("Status must not be null");
        }
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> {
                List<Comment> list = dao.getByStatus(status, offset, count);
                int countComment = dao.getCount(status);
                return new PagingListDTO<Comment>(countComment, list);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getCount(CommentStatus status) throws ServiceException {
        if(status == null){
            throw new ServiceValidationException("Status must not be null");
        }
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.execute(() -> dao.getCount(status));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
