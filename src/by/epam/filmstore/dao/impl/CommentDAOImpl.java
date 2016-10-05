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
    private static final String SELECT_COMMENT = "SELECT * FROM comments WHERE id=?";
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

    @Override
    @PartOfTransaction
    public void update(int id, CommentStatus status) throws DAOException {
        PreparedStatement preparedStatement = null;

        try{
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_COMMENT);

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, id);

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
    public boolean delete(int id) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_COMMENT);

            preparedStatement.setInt(1, id);
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
    public Comment get(int id) throws DAOException {
        Comment comment = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_COMMENT);

            preparedStatement.setInt(1, id);
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

    @Override
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
