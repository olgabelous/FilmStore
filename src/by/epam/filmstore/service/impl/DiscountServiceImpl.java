package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IDiscountDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.IDiscountService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public class DiscountServiceImpl implements IDiscountService {

    @Override
    public void save(double sumFrom, double value) throws ServiceException {
        if(ServiceValidation.isNotPositive(value) && sumFrom < 0){
            throw new ServiceValidationException("Invalid arguments");
        }
        IDiscountDAO dao = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        Discount discount = new Discount(sumFrom, value);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(discount);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(int id, double sumFrom, double value) throws ServiceException {
        if(ServiceValidation.isNotPositive(id) || ServiceValidation.isNotPositive(value) || sumFrom < 0){
            throw new ServiceValidationException("Invalid arguments");
        }
        IDiscountDAO dao = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        Discount discount = new Discount(id, sumFrom, value);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(discount);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        IDiscountDAO dao = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        if(id <= 0){
            throw new ServiceValidationException("Discount id must be positive number!");
        }
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Discount> getAll() throws ServiceException {
        IDiscountDAO dao = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        try {
            return DAOHelper.execute(() -> dao.getDiscountsList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public double getDiscount(int userId) throws ServiceException {
        IDiscountDAO dao = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        try {
            return DAOHelper.transactionExecute(() -> dao.getUserDiscount(userId).getValue());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
