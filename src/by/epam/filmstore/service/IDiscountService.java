package by.epam.filmstore.service;

import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public interface IDiscountService {

    boolean delete(int id) throws ServiceException;

    List<Discount> getAll() throws ServiceException;

    double getDiscount(int userId) throws ServiceException;
}
