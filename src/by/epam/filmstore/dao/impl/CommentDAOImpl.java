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
 * Created by Olga Shahray on 18.06.2016.
 *
 * Класс CommentDAOImpl позволяет совершить CRUD операции с сущностью Комментарий.
 * В каждом методе используется Connection, полученный из DAOHelper (см. AbstractDAO и DAOHelper).
 * Возврат Connection в пул происходит в DAOHelper
 */
public class CommentDAOImpl extends AbstractDAO implements ICommentDAO {

    private static final String INSERT_COMMENT = "INSERT INTO comments (film_id, user_id, mark, comment, date_com, status) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_COMMENT = "SELECT * FROM comments WHERE user_id=? AND film_id=?";
    private static final String DELETE_COMMENT = "DELETE FROM comments WHERE user_id=? AND film_id=?";
    private static final String SELECT_ALL_COMMENTS_OF_USER = "SELECT comments.film_id, films.title, comments.mark, comments.comment, " +
            "comments.date_com, comments.status FROM comments INNER JOIN Films ON comments.film_id = films.id WHERE user_id=?";
    private static final String SELECT_ALL_COMMENTS_OF_FILM = "SELECT mark, comment, date_com, status, user_id, " +
            "users.name FROM comments INNER JOIN users ON comments.user_id = users.id WHERE film_id=? AND status = 'checked'";
    private static final String UPDATE_COMMENT = "UPDATE comments SET status=? WHERE film_id=? and user_id=?";
    private static final String SELECT_COMMENTS_BY_STATUS = "SELECT mark, comment, date_com, status, user_id, " +
            "users.name, film_id, films.title FROM comments INNER JOIN users ON comments.user_id = users.id " +
            "INNER JOIN films ON comments.film_id = films.id WHERE status = ?";


    @Override
    @PartOfTransaction
    public void save(int userId, int filmId, Comment comment) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_COMMENT);

            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userId);
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

    @Override
    @PartOfTransaction
    public void update(int filmId, int userId, CommentStatus status) throws DAOException {
        PreparedStatement preparedStatement = null;

        try{
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_COMMENT);

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, filmId);
            preparedStatement.setInt(3, userId);

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

    @Override
    @PartOfTransaction
    public boolean delete(int userId, int filmId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_COMMENT);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);
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

    @Override
    @PartOfTransaction
    public Comment get(int userId, int filmId) throws DAOException {
        Comment comment = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_COMMENT);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(3));
                    comment.setText(rs.getString(4));
                    comment.setDateComment(rs.getTimestamp(5).toLocalDateTime());
                    comment.setStatus(CommentStatus.valueOf(rs.getString(6).toUpperCase()));
                }
                else{
                    throw new DAOException("Error getting comment");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting comment", e);
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
        return comment;
    }

    @Override
    @PartOfTransaction
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

                    comment.setFilm(new Film(rs.getInt(1), rs.getString(2)));
                    comment.setMark(rs.getInt(3));
                    comment.setText(rs.getString(4));
                    comment.setDateComment(rs.getTimestamp(5).toLocalDateTime());
                    comment.setStatus(CommentStatus.valueOf(rs.getString(6).toUpperCase()));

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
                    comment.setUser(new User(rs.getInt(5), rs.getString(6)));

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

    @Override
    @PartOfTransaction
    public List<Comment> getByStatus(CommentStatus status) throws DAOException {
        List<Comment> allComments = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_STATUS);

            preparedStatement.setString(1, status.name());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Comment comment = null;

                while (rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(1));
                    comment.setText(rs.getString(2));
                    comment.setDateComment(rs.getTimestamp(3).toLocalDateTime());
                    comment.setStatus(CommentStatus.valueOf(rs.getString(4).toUpperCase()));
                    comment.setUser(new User(rs.getInt(5), rs.getString(6)));
                    comment.setFilm(new Film(rs.getInt(7), rs.getString(8)));

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
}
