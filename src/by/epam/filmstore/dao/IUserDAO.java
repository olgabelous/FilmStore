package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.domain.User;

import java.util.List;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public interface IUserDAO {

    User save(User user) throws DAOException;

    User authorize(String email, String password) throws DAOException;

    void update(User user) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    //Only for testing
    boolean deleteByEmail(String email) throws DAOException;

    // null if not found
    User get(int id) throws DAOException;

    // null if not found
    User getByEmail(String email) throws DAOException;

    List<User> getAll(int limit) throws DAOException;

    List<Genre> getFavoriteGenresOfUser(int userId) throws DAOException;



}
