package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public class UserDAOImpl extends AbstractDAO implements IUserDAO {

    private static final String INSERT_USER = "INSERT INTO users (name, email, password, phone, photo, date_reg, role) VALUES(?,?,?,?,?,?,?)";
    private static final String INSERT_FAVORITE_GENRE = "INSERT INTO preferences (user_id, genre_id) VALUES(?,?)";
    private static final String DELETE_FAVORITE_GENRE = "DELETE FROM preferences WHERE user_id=?";
    private static final String SELECT_FAVORITE_GENRES = "SELECT DISTINCT preferences.genre_id, allgenres.genre FROM preferences, allgenres" +
            " WHERE allgenres.id = preferences.genre_id AND preferences.user_id= ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE users.id=?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE users.email=?";
    private static final String DELETE_USER_BY_EMAIL = "DELETE FROM users WHERE email=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER = "UPDATE users SET name=?, email=?, password=?, phone=?, photo=?, date_reg=?, role=? WHERE id=?";

    @Override
    public void save(User user) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPass());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPhoto());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(user.getDateRegistration()));
            preparedStatement.setString(7, user.getRole().name());

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error saving user");
            }
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                } else {
                    throw new DAOException("Error getting user id");
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving user", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
    }

    public void saveFavoriteGenres(int userId, List<Genre> genreList) throws DAOException {
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_FAVORITE_GENRE);
            preparedStatement1 = connection.prepareStatement(DELETE_FAVORITE_GENRE);

            preparedStatement1.setInt(1, userId);
            preparedStatement.executeUpdate();

            for (Genre genre : genreList) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, genre.getId());
                preparedStatement.addBatch();
            }
            int[] rows = preparedStatement.executeBatch();
            for (int row : rows) {
                if (row == 0) {
                    throw new DAOException("Error saving favorite genre");
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving favorite genre", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
            if(preparedStatement1 != null){
                try {
                    preparedStatement1.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
    }

    @Override
    public void update(User user) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPass());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPhoto());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(user.getDateRegistration()));
            preparedStatement.setString(7, user.getRole().name());
            preparedStatement.setInt(8, user.getId());

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error updating user");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating user", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER);

            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting user", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
    }

    //Only for testing
    @Override
    public boolean deleteByEmail(String email) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER_BY_EMAIL);

            preparedStatement.setString(1, email);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting user", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
    }

    @Override
    public User get(int id) throws DAOException {
        User user = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);

            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setPass(rs.getString(4));
                    user.setPhone(rs.getString(5));
                    user.setPhoto(rs.getString(6));
                    user.setDateRegistration(rs.getTimestamp(7).toLocalDateTime());
                    user.setRole(Role.valueOf(rs.getString(8).toUpperCase()));

                } else {
                    throw new DAOException("Error getting user by id");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("error", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
        return user;
    }

    @Override
    public User getByEmail(String email) throws DAOException {
        User user = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);

            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setPass(rs.getString(4));
                    user.setPhone(rs.getString(5));
                    user.setPhoto(rs.getString(6));
                    user.setDateRegistration(rs.getTimestamp(7).toLocalDateTime());
                    user.setRole(Role.valueOf(rs.getString(8).toUpperCase()));

                } else {
                    throw new DAOException("Error getting user id");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting user id", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DAOException {
        List<User> allUsers = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                User user = null;
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setPass(rs.getString(4));
                    user.setPhone(rs.getString(5));
                    user.setPhoto(rs.getString(6));
                    user.setDateRegistration(rs.getTimestamp(7).toLocalDateTime());
                    user.setRole(Role.valueOf(rs.getString(8).toUpperCase()));

                    allUsers.add(user);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all users", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
        return allUsers;
    }

    @Override
    public List<Genre> getFavoriteGenresOfUser(int userId) throws DAOException {
        List<Genre> favoriteGenres = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_FAVORITE_GENRES);

            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Genre genre = null;
                while (rs.next()) {
                    genre = new Genre();
                    genre.setId(rs.getInt(1));
                    genre.setGenreName(rs.getString(2));
                    favoriteGenres.add(genre);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting getting favorite genres of user", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in user dao", e);
                }
            }
        }
        return favoriteGenres;
    }

}
