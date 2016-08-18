package by.epam.filmstore.service;

import by.epam.filmstore.domain.Order;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 15.08.2016.
 */
public interface IOrderService {

    List<Order> getAllOfUser(int userId) throws ServiceException;

    double getTotalAmount(int userId) throws ServiceException;
}
