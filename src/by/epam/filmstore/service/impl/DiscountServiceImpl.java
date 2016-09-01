package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IDiscountDAO;
import by.epam.filmstore.dao.IOrderDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.IDiscountService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.util.ServiceHelper;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public class DiscountServiceImpl implements IDiscountService {

    @Override
    public void save(double sumFrom, double value) throws ServiceException {
        if(ServiceHelper.isNotPositive(value) && sumFrom < 0){
            throw new ServiceException("Invalid arguments");
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
    public boolean delete(int id) throws ServiceException {
        IDiscountDAO dao = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        if(id <= 0){
            throw new ServiceException("Discount id must be positive number!");
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
        IOrderDAO orderDAO = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        IDiscountDAO discountDAO = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();

        try {
            return DAOHelper.transactionExecute(() -> {
                double sum = orderDAO.getTotalAmount(userId);
                List<Discount> discountList = discountDAO.getDiscountsList();
                ListIterator<Discount> iter = discountList.listIterator(discountList.size());
                double discounValue = 0.0;
                while (iter.hasPrevious()) {
                    Discount discount = iter.previous();
                    if (sum > discount.getSumFrom()) {
                        discounValue = discount.getValue();
                    }
                }
                return discounValue;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
