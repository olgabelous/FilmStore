package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.User;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface IUserDAO {

    User save(User user) throws DAOException;

    User authorize(String email, String password) throws DAOException;

    User update(User user) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    //Only for testing
    boolean deleteByEmail(String email) throws DAOException;

    // null if not found
    User get(int id) throws DAOException;

    // null if not found
    User getByEmail(String email) throws DAOException;

    List<User> getAll(int offset, int count) throws DAOException;

    int checkIfEmailExist(String email)  throws DAOException;

    int getCountUsers()  throws DAOException;
}
