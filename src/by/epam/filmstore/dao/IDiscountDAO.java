package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Discount;

import java.util.List;

/**
 * Created by Olga Shahray on 27.06.2016.
 */
public interface IDiscountDAO {

    void save(Discount discount) throws DAOException;

    Discount get(int discountId) throws DAOException;

    // false if not found
    boolean delete(int discountId) throws DAOException;

    List<Discount> getDiscountsList() throws DAOException;


}