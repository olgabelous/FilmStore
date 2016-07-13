package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public class CommentDAOImpl implements ICommentDAO {

    private static final String INSERT_COMMENT = "INSERT INTO comments (film_id, user_id, mark, comment, date_com, status) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_COMMENT = "SELECT * FROM comments WHERE user_id=? AND film_id=?";
    private static final String DELETE_COMMENT = "DELETE FROM comments WHERE user_id=? AND film_id=?";
    private static final String SELECT_ALL_COMMENTS_OF_USER = "SELECT film_id, user_id, mark, comment, dare_com, status " +
            "FROM comments WHERE user_id=?";
    private static final String SELECT_ALL_COMMENTS_OF_FILM = "SELECT film_id, user_id, mark, comment, dare_com, status " +
            "FROM comments WHERE film_id=?";
    private static final String UPDATE_COMMENT = "UPDATE comments SET status=? WHERE film_id=? and user_id=?";


    @Override
    public void save(int userId, int filmId, Comment comment) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENT)){
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, comment.getMark());
            preparedStatement.setString(4, comment.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(comment.getDateComment()));
            preparedStatement.setString(6, comment.getStatus());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving comment");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving comment", e);
        }
    }

    @Override
    public void update(Comment comment) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENT)
        ) {
            preparedStatement.setString(1, comment.getStatus());
            preparedStatement.setInt(2, comment.getFilm().getId());
            preparedStatement.setInt(3, comment.getUser().getId());

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error updating comment");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating comment", e);
        }
    }

    @Override
    public boolean delete(int userId, int filmId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENT)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);
            int row = preparedStatement.executeUpdate();
            return  row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting comment", e);
        }
    }

    @Override
    public Comment get(int userId, int filmId) throws DAOException {
        Comment comment = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENT)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(3));
                    comment.setText(rs.getString(4));
                    comment.setDateComment(rs.getTimestamp(5).toLocalDateTime());
                    comment.setStatus(rs.getString(6));
                }
                else{
                    throw new DAOException("Error getting comment");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting comment", e);
        }
        return comment;
    }

    @Override
    public List<Comment> getAllOfUser(int userId) throws DAOException {
        List<Comment> allComments = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_OF_USER)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Comment comment = null;

                while (rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(3));
                    comment.setText(rs.getString(4));
                    comment.setDateComment(rs.getTimestamp(5).toLocalDateTime());
                    comment.setStatus(rs.getString(6));

                    allComments.add(comment);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all comments", e);
        }
        return allComments;
    }

    @Override
    public List<Comment> getAllOfFilm(int filmId) throws DAOException {
        List<Comment> allComments = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_OF_FILM)) {
            preparedStatement.setInt(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Comment comment = null;

                while (rs.next()) {
                    comment = new Comment();

                    comment.setMark(rs.getInt(3));
                    comment.setText(rs.getString(4));
                    comment.setDateComment(rs.getTimestamp(5).toLocalDateTime());
                    comment.setStatus(rs.getString(6));

                    allComments.add(comment);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all comments", e);
        }
        return allComments;
    }
}
