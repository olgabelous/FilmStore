package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IOrderDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 15.08.2016.
 */
public class OrderServiceImpl implements IOrderService {
    @Override
    public List<Order> getAllOfUser(int userId) throws ServiceException {
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getAllOfUser(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public double getTotalAmount(int userId) throws ServiceException {
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getTotalAmount(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
