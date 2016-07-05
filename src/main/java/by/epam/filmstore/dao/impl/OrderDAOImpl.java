package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.IOrderDAO;
import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class OrderDAOImpl implements IOrderDAO {

    private static final String INSERT_ORDER = "INSERT INTO orders (film_id, user_id, date_sale, sum, status) VALUES(?,?,?,?,?)";
    private static final String SELECT_ORDER = "SELECT id, film_id, user_id, date_sale, sum, status FROM orders WHERE orders.id =?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE orders.id=?";
    private static final String SELECT_ALL_ORDERS_OF_USER = "SELECT id, film_id, user_id, date_sale, sum, status " +
            "FROM orders WHERE user_id=?";
    private static final String SELECT_ALL_ORDERS_OF_FILM = "SELECT id, film_id, user_id, date_sale, sum, status " +
            "FROM orders WHERE film_id=?";
    private static final String SELECT_ALL_ORDERS = "SELECT id, film_id, user_id, date_sale, sum, status FROM orders";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=? WHERE orders.id=?";


    @Override
    public void save(Order order) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setInt(1, order.getFilm().getId());
            preparedStatement.setInt(2, order.getUser().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(order.getDateSale()));
            preparedStatement.setDouble(4, order.getSum());
            preparedStatement.setString(5, order.getStatus());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving oredr");
            }
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if(rs.next()) {
                    order.setId(rs.getInt(1));
                }
                else{
                    throw new DAOException("Error getting order id");
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving order", e);
        }
    }

    @Override
    public void update(Order order) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS)
        ) {
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setInt(2, order.getId());

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error changing order status");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error changing order status", e);
        }

    }

    @Override
    public boolean delete(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER)) {
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting order", e);
        }
    }

    @Override
    public Order get(int id) throws DAOException {
        Order order = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));
                    IUserDAO userDAO = DAOFactory.getMySqlDAOFactory().getIUserDAO();
                    IFilmDAO filmDAO = DAOFactory.getMySqlDAOFactory().getIFilmDAO();

                    Film film = filmDAO.get(rs.getInt(2));
                    User user = userDAO.get(rs.getInt(3));

                    order.setFilm(film);
                    order.setUser(user);
                    order.setDateSale(rs.getTimestamp(4).toLocalDateTime());
                    order.setSum(rs.getDouble(5));
                    order.setStatus(rs.getString(6));
                }
                else{
                    throw new DAOException("Error getting order by id");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting order by id", e);
        }
        return order;
    }

    @Override
    public List<Order> getAllOfUser(int userId) throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_OF_USER)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Order order = null;
                IUserDAO userDAO = DAOFactory.getMySqlDAOFactory().getIUserDAO();
                IFilmDAO filmDAO = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
                Film film = null;
                User user = null;
                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    film = filmDAO.get(rs.getInt(2));
                    user = userDAO.get(rs.getInt(3));

                    order.setFilm(film);
                    order.setUser(user);
                    order.setDateSale(rs.getTimestamp(4).toLocalDateTime());
                    order.setSum(rs.getDouble(5));
                    order.setStatus(rs.getString(6));

                    allOrders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all orders of user", e);
        }
        return allOrders;
    }

    @Override
    public List<Order> getAllOfFilm(int filmId) throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_OF_FILM)) {
            preparedStatement.setInt(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Order order = null;
                IUserDAO userDAO = DAOFactory.getMySqlDAOFactory().getIUserDAO();
                IFilmDAO filmDAO = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
                Film film = null;
                User user = null;
                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    film = filmDAO.get(rs.getInt(2));
                    user = userDAO.get(rs.getInt(3));

                    order.setFilm(film);
                    order.setUser(user);
                    order.setDateSale(rs.getTimestamp(4).toLocalDateTime());
                    order.setSum(rs.getDouble(5));
                    order.setStatus(rs.getString(6));

                    allOrders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all orders of film", e);
        }
        return allOrders;
    }

    @Override
    public List<Order> getAll() throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Order order = null;
                IUserDAO userDAO = DAOFactory.getMySqlDAOFactory().getIUserDAO();
                IFilmDAO filmDAO = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
                Film film = null;
                User user = null;
                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    film = filmDAO.get(rs.getInt(2));
                    user = userDAO.get(rs.getInt(3));

                    order.setFilm(film);
                    order.setUser(user);
                    order.setDateSale(rs.getTimestamp(4).toLocalDateTime());
                    order.setSum(rs.getDouble(5));
                    order.setStatus(rs.getString(6));

                    allOrders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all orders", e);
        }
        return allOrders;
    }
}
