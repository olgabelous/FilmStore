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
 * <p>Class encapsulates the business logic for the entity Comment, performing validation,
 * controlling transactions and coordinating responses in the implementation of its operations.</p>
 *
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class CommentServiceImpl implements ICommentService {
    private final static String ID_MUST_BE_POSITIVE = "Id must be positive number";

    /**
     * Method validates params, creates instance Comment and pass to dao to save
     * @param user who writes comment
     * @param filmId - id of film
     * @param mark - user marks film from 1 to 5
     * @param commentText - text of comment
     * @throws ServiceException
     */
    @Override
    public void save(User user, int filmId, int mark, String commentText) throws ServiceException {
        //validation
        if(ServiceValidation.isNotPositive(filmId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(mark < 1 || mark > 5 ){
            throw new ServiceValidationException("Mark must be 1 - 5");
        }
        //validation end
        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        Film film = new Film();
        film.setId(filmId);

        Comment comment = new Comment(user, film, mark, commentText, LocalDateTime.now(), CommentStatus.NEW);
        try {
            DAOHelper.execute(() -> {
                dao.save(comment);
                return  null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates params, creates instance Comment and pass to dao to update with given status
     * @param id of comment
     * @param status for updating
     * @throws ServiceException
     */
    @Override
    public void update(int id, CommentStatus status) throws ServiceException {
        //validation
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(status == null){
            throw new ServiceValidationException("Status must not be null");
        }
        //validation end

        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            DAOHelper.execute(() -> {
                dao.update(id, status);
                return  null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param id of comment
     * @return boolean result if comment was deleted
     * @throws ServiceException
     */
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

    /**
     * @param userId - id of user
     * @return a {@code List<Comment>} all comments written by user
     * @throws ServiceException
     */
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

    /**
     * Return DTO object that contains {@code List<Comment>} displaying on page and count
     * of all comments of given status.
     * @param status of comment
     * @param offset - is a start number of selection
     * @param count - is a count of required records
     * @return {@code PagingListDTO<Comment>}
     * @throws ServiceException
     *
     * @see by.epam.filmstore.domain.dto.PagingListDTO
     */
    @Override
    public PagingListDTO<Comment> getByStatus(CommentStatus status, int offset, int count) throws ServiceException {
        //validation
        if(status == null){
            throw new ServiceValidationException("Status must not be null");
        }
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        //validation end

        ICommentDAO dao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        try {
            return DAOHelper.transactionExecute(() -> {
                List<Comment> list = dao.getByStatus(status, offset, count);
                int countComment = dao.getCount(status);
                return new PagingListDTO<Comment>(countComment, list);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param status of comment
     * @return count of all comments of given status
     * @throws ServiceException
     */
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
