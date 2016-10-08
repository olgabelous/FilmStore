package by.epam.filmstore.service;

import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface IDiscountService {

    void save(double sumFrom, double value) throws ServiceException;

    void update(int id, double sumFrom, double value) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Discount> getAll() throws ServiceException;

    double getDiscount(int userId) throws ServiceException;
}
