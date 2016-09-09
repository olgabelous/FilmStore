package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IDiscountDAO;
import by.epam.filmstore.dao.IOrderDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.exception.ServiceAuthException;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Olga Shahray on 15.08.2016.
 */
public class OrderServiceImpl implements IOrderService {

    private static final int _100PERCENT = 100;

    @Override
    public void save(int filmId, double price, User user) throws ServiceException {
        if(ServiceValidation.isNotPositive(filmId) || ServiceValidation.isNotPositive(price)){
            throw new ServiceValidationException("Invalid arguments");
        }
        if(user == null){
            throw new ServiceAuthException("Access denied. Please log in");
        }
        IOrderDAO orderDAO = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        IDiscountDAO discountDAO = DAOFactory.getMySqlDAOFactory().getIDiscountDAO();
        Film film = new Film();
        film.setId(filmId);
        try {
            DAOHelper.transactionExecute(() -> {
                double userDiscount = discountDAO.getUserDiscount(user.getId()).getValue();
                double sum = (_100PERCENT - userDiscount)*price/_100PERCENT;
                Order order = new Order(film, user, LocalDateTime.now(), sum, OrderStatus.UNPAID);
                orderDAO.save(order);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int orderId) throws ServiceException {
        if(ServiceValidation.isNotPositive(orderId)){
            throw new ServiceValidationException("Invalid arguments");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(orderId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatus(int orderId, OrderStatus status) throws ServiceException {
        if(ServiceValidation.isNotPositive(orderId)){
            throw new ServiceValidationException("Invalid arguments");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            DAOHelper.execute(() -> {
                dao.updateStatus(orderId, status);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getAllOfUser(int userId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException("Invalid arguments");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getOrdersOfUser(userId, OrderStatus.PAID));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersInCart(int userId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException("Invalid arguments");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getOrdersOfUser(userId, OrderStatus.UNPAID));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public double getTotalAmount(int userId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException("Invalid arguments");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getTotalAmount(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
