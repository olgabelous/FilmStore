package by.epam.filmstore.service;

import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public interface IDiscountService {

    void save(double sumFrom, double value) throws ServiceException;

    void update(int id, double sumFrom, double value) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Discount> getAll() throws ServiceException;

    double getDiscount(int userId) throws ServiceException;
}
