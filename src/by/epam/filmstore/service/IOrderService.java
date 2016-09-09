package by.epam.filmstore.service;

import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 15.08.2016.
 */
public interface IOrderService {

    void save(int filmId, double price, User user) throws ServiceException;

    boolean delete(int orderId) throws ServiceException;

    void updateStatus(int orderId, OrderStatus status) throws ServiceException;

    List<Order> getAllOfUser(int userId) throws ServiceException;

    List<Order> getOrdersInCart(int userId) throws ServiceException;

    double getTotalAmount(int userId) throws ServiceException;
}
