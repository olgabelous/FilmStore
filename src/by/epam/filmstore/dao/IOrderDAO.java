package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.OrderStatus;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface IOrderDAO {

    void save(Order order) throws DAOException;

    void updateStatus(int orderId, OrderStatus status) throws DAOException;

    // false if not found
    boolean delete(int id) throws DAOException;

    // null if not found
    Order get(int id) throws DAOException;

    List<Order> getOrdersOfUser(int userId, OrderStatus status) throws DAOException;

    List<Order> getAllOfFilm(int filmId) throws DAOException;

    List<Order> getAll() throws DAOException;

    double getTotalAmount(int userId) throws DAOException;

}
