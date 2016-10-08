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
 * <p>Class encapsulates the business logic for the entity Order, performing validation,
 * controlling transactions and coordinating responses in the implementation of its operations.</p>
 *
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class OrderServiceImpl implements IOrderService {
    private final static String ID_MUST_BE_POSITIVE = "Id must be positive number";
    private static final int _100PERCENT = 100;

    /**
     * Method validates params, creates instance Order and passes to dao to save
     * @param filmId - id of film
     * @param price of film
     * @param user who has created order
     * @throws ServiceException
     */
    @Override
    public void save(int filmId, double price, User user) throws ServiceException {
        //validation
        if(ServiceValidation.isNotPositive(filmId) || ServiceValidation.isNotPositive(price)){
            throw new ServiceValidationException("Invalid arguments");
        }
        if(user == null){
            throw new ServiceAuthException("Access denied. Please log in");
        }
        //validation end

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

    /**
     * @param orderId - if of order
     * @return boolean result if order was deleted
     * @throws ServiceException
     */
    @Override
    public boolean delete(int orderId) throws ServiceException {
        if(ServiceValidation.isNotPositive(orderId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(orderId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates params and passes to dao to update status of order
     * @param orderId - id of order
     * @param status for updating
     * @throws ServiceException
     */
    @Override
    public void updateStatus(int orderId, OrderStatus status) throws ServiceException {
        if(ServiceValidation.isNotPositive(orderId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
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

    /**
     * Method validates params and passes to dao to update status of orders
     * @param orderIdArr - array of order's id
     * @param status for updating
     * @throws ServiceException
     */
    @Override
    public void updateStatus(int[] orderIdArr, OrderStatus status) throws ServiceException {
        for(int orderId : orderIdArr){
            if(ServiceValidation.isNotPositive(orderId)){
                throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
            }
        }
        if(status == null){
            throw new ServiceValidationException("Order status must not be null");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            DAOHelper.transactionExecute(() -> {
                for(int orderId : orderIdArr){
                    dao.updateStatus(orderId, status);
                }
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method returns all orders of given user and status
     * @param userId - id of user
     * @param status of order
     * @return a {@code List<Order>}
     * @throws ServiceException
     */
    @Override
    public List<Order> getUserOrdersByStatus(int userId, OrderStatus status) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(status == null){
            throw new ServiceValidationException("Order status must not be null");
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getOrdersOfUser(userId, status));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param userId - id of user
     * @return total amount of paid orders of user
     * @throws ServiceException
     */
    @Override
    public double getTotalAmount(int userId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.getTotalAmount(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param orderId - id of order
     * @return order
     * @throws ServiceException
     */
    @Override
    public Order get(int orderId) throws ServiceException {
        if(ServiceValidation.isNotPositive(orderId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IOrderDAO dao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        try {
            return DAOHelper.execute(() -> dao.get(orderId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
