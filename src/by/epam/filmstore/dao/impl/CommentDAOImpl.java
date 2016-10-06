package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.PartOfTransaction;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class allows to perform CRUD operations with entity Comment.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class CommentDAOImpl extends AbstractDAO implements ICommentDAO {

    private static final String INSERT_COMMENT = "INSERT INTO comments (film_id, user_id, mark, comment, date_com, status) VALUES(?,?,?,?,?,?)";
    private static final String DELETE_COMMENT = "DELETE FROM comments WHERE id=?";
    private static final String SELECT_ALL_COMMENTS_OF_USER = "SELECT comments.film_id, films.title, films.poster, comments.mark, comments.comment, " +
            "comments.date_com, comments.status, comments.id  FROM comments INNER JOIN films ON comments.film_id = films.id WHERE user_id=?";
    private static final String SELECT_ALL_COMMENTS_OF_FILM = "SELECT mark, comment, date_com, status, user_id, " +
            "users.name, users.photo FROM comments INNER JOIN users ON comments.user_id = users.id WHERE film_id=? AND status = 'checked'";
    private static final String UPDATE_COMMENT = "UPDATE comments SET status=? WHERE id=?";
    private static final String SELECT_COMMENTS_BY_STATUS = "SELECT mark, comment, date_com, status, user_id, " +
            "users.name, film_id, films.title, films.poster, comments.id FROM comments INNER JOIN users ON comments.user_id = users.id " +
            "INNER JOIN films ON comments.film_id = films.id WHERE status = ? LIMIT ?, ?";
    private static final String COUNT_COMMENTS_BY_STATUS = "SELECT COUNT(id) FROM comments WHERE status = ?";

    /**
     * Method saves @param comment in database
     *
     * @param comment
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void save(Comment comment) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_COMMENT);

            preparedStatement.setInt(1, comment.getFilm().getId());
            preparedStatement.setInt(2, comment.getUser().getId());
            preparedStatement.setInt(3, comment.getMark());
            preparedStatement.setString(4, comment.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(comment.getDateComment()));
            preparedStatement.setString(6, comment.getStatus().name());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving comment");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving comment", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }

    /**
     * Method updates status of comment in database
     *
     * @param commentId
     * @param status
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void update(int commentId, CommentStatus status) throws DAOException {
        PreparedStatement preparedStatement = null;

        try{
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_COMMENT);

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, commentId);

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error updating comment");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating comment", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }

    /**
     *
     * @param commentId
     * @return boolean result if comment was deleted
     * @throws DAOException
     */
    @Override
    public boolean delete(int commentId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_COMMENT);

            preparedStatement.setInt(1, commentId);
            int row = preparedStatement.executeUpdate();
            return  row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting comment", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }

    /**
     * Returns all comments belonging to user with required user id
     *
     * @param userId
     * @return a {@code List<Comment>}
     * @throws DAOException
     */
    @Override
    public List<Comment> getAllOfUser(int userId) throws DAOException {
        List<Comment> allComments = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_OF_USER);

            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Comment comment = null;

                while (rs.next()) {
                    comment = new Comment();

                    comment.setFilm(new Film(rs.getInt(1), rs.getString(2), rs.getString(3)));
                    comment.setMark(rs.getInt(4));
                    comment.setText(rs.getString(5));
                    comment.setDateComment(rs.getTimestamp(6).toLocalDateTime());
                    comment.setStatus(CommentStatus.valueOf(rs.getString(7).toUpperCase()));
                    comment.setId(rs.getInt(8));

                    allComments.add(comment);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all comments", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
        return allComments;
    }

    /**
     * Returns all comments of film with required id
     *
     * @param filmId
     * @return a {@code List<Comment>}
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<Comment> getAllOfFilm(int filmId) throws DAOException {
        List<Comment> allComments = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_OF_FILM);

            preparedStatement.setInt(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Comment comment = null;

                while (rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(1));
                    comment.setText(rs.getString(2));
                    comment.setDateComment(rs.getTimestamp(3).toLocalDateTime());
                    comment.setStatus(CommentStatus.valueOf(rs.getString(4).toUpperCase()));
                    comment.setUser(new User(rs.getInt(5), rs.getString(6), rs.getString(7)));

                    allComments.add(comment);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all comments", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
        return allComments;
    }

    /**
     * Returns all comments with specified status
     *
     * @param status
     * @param offset - is a start number of selection in db
     * @param count - is a count of required records from db
     * @return a {@code List<Comment>}
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<Comment> getByStatus(CommentStatus status, int offset, int count) throws DAOException {
        List<Comment> allComments = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_STATUS);

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, count);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Comment comment = null;

                while (rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(1));
                    comment.setText(rs.getString(2));
                    comment.setDateComment(rs.getTimestamp(3).toLocalDateTime());
                    comment.setStatus(CommentStatus.valueOf(rs.getString(4).toUpperCase()));
                    comment.setUser(new User(rs.getInt(5), rs.getString(6)));
                    comment.setFilm(new Film(rs.getInt(7), rs.getString(8), rs.getString(9)));
                    comment.setId(rs.getInt(10));

                    allComments.add(comment);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting comments by status", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
        return allComments;
    }

    /**
     *
     * @param status
     * @return count af all comments with specifies status
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public int getCount(CommentStatus status) throws DAOException {
        int count = 0;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(COUNT_COMMENTS_BY_STATUS);

            preparedStatement.setString(1, status.name());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            return count;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting count comments by status", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }
}
