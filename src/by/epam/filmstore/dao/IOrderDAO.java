package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Order;

import java.util.List;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public interface IOrderDAO {

    void save(Order order) throws DAOException;

    void update(Order order) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    // null if not found
    Order get(int id) throws DAOException;

    List<Order> getAllOfUser(int userId) throws DAOException;

    List<Order> getAllOfFilm(int filmId) throws DAOException;

    List<Order> getAll() throws DAOException;

}
