package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.PartOfTransaction;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class allows to perform CRUD operations with entity User.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class UserDAOImpl extends AbstractDAO implements IUserDAO {

    private static final String INSERT_USER = "INSERT INTO users (name, email, password, phone, photo, date_reg, role) VALUES(?,?,?,?,?,?,?)";
    private static final String INSERT_FAVORITE_GENRE = "INSERT INTO preferences (user_id, genre_id) VALUES(?,?)";
    private static final String DELETE_FAVORITE_GENRE = "DELETE FROM preferences WHERE user_id=?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE users.id=?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE users.email=?";
    private static final String SELECT_USER_BY_EMAIL_PASS = "SELECT id, name, email, password, phone, photo, date_reg, role " +
            "FROM users WHERE users.email=? and users.password =?";
    private static final String DELETE_USER_BY_EMAIL = "DELETE FROM users WHERE email=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT id, name, email, password, phone, photo, date_reg, role FROM users LIMIT ?, ?";
    private static final String COUNT_ALL_USERS = "SELECT COUNT(id) FROM users";
    private static final String UPDATE_USER = "UPDATE users SET name=?, email=?, password=?, phone=?, photo=?, date_reg=?, role=? WHERE id=?";
    private static final String SELECT_EMAIL_COUNT = "SELECT COUNT(1) FROM users WHERE email = ?";

    /**
     * Method saves @param user in database
     * @param user
     * @return saved user
     * @throws DAOException
     */
    @Override
    public User save(User user) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
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
            return user;
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

    /**
     * Method returns user from db on basis of given email and password
     * @param email of user
     * @param password of user
     * @return user
     * @throws DAOException
     */
    @Override
    public User authorize(String email, String password) throws DAOException {
        User user = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_PASS);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
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

                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting user", e);
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

    /**
     * Method updates user in db
     * @param user for updating
     * @return updated user
     * @throws DAOException
     */
    @Override
    public User update(User user) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
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
            return user;
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

    /**
     * @param id of user
     * @return boolean result if user was deleted
     * @throws DAOException
     */
    @Override
    public boolean delete(int id) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
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
            Connection connection = getConnectionFromThreadLocal();
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

    /**
     * Method gets user from database
     * @param id of user
     * @return user
     * @throws DAOException
     */
    @Override
    public User get(int id) throws DAOException {
        User user = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
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

    /**
     * Method returns a {@code List<User>}
     * @param offset - is a start number of selection in db
     * @param count - is a count of required records from db
     * @return a {@code List<User>}
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<User> getAll(int offset, int count) throws DAOException {
        List<User> allUsers = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, count);
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

    /**
     * @return count of users in database
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public int getCountUsers() throws DAOException {
        int count = 0;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(COUNT_ALL_USERS);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            return count;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting count users", e);
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

    /**
     * Method checks if email exist in database
     * @param email for check
     * @return count of emails in database
     * @throws DAOException
     */
    @Override
    public int checkIfEmailExist(String email)  throws DAOException{
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_EMAIL_COUNT);

            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DAOException("Error check if email exist");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error check if email exist", e);
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

}
