package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IDiscountDAO;
import by.epam.filmstore.dao.IOrderDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.IDiscountService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Olga Shahray on 16.08.2016.
 */
public class DiscountServiceImpl implements IDiscountService {

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
                while (iter.hasPrevious()) {
                    Discount discount = iter.previous();
                    if (sum > discount.getSumFrom()) {
                        discount.getValue();
                    }
                }
                return 0.0;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
