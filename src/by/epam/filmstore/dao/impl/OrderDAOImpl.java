package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IOrderDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class allows to perform CRUD operations with entity Order.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class OrderDAOImpl extends AbstractDAO implements IOrderDAO {

    private static final String INSERT_ORDER = "INSERT INTO orders (film_id, user_id, date_sale, sum, status) VALUES(?,?,?,?,?)";
    private static final String SELECT_ORDER = "SELECT orders.id, orders.film_id, films.title, films.poster, films.price, films.video, orders.date_sale, orders.sum, " +
            "orders.status, users.id, users.name FROM orders INNER JOIN films ON orders.film_id = films.id " +
            "INNER JOIN users ON orders.user_id = users.id WHERE orders.id =?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE orders.id=?";
    private static final String SELECT_ALL_ORDERS_OF_USER = "SELECT orders.id, orders.film_id, films.title, films.poster, films.price, films.video, orders.date_sale, orders.sum, " +
            "orders.status FROM orders INNER JOIN films ON orders.film_id = films.id WHERE orders.user_id=? AND orders.status = ?";
    private static final String SELECT_ALL_ORDERS_OF_FILM = "SELECT id, film_id, user_id, date_sale, sum, status " +
            "FROM orders WHERE film_id=?";
    private static final String SELECT_ALL_ORDERS = "SELECT id, film_id, user_id, date_sale, sum, status FROM orders";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=? WHERE orders.id=?";
    private static final String SELECT_TOTAL_AMOUNT_OF_USER = "SELECT SUM(orders.sum) FROM orders WHERE orders.user_id = ? " +
            "AND orders.status = 'paid'";


    @Override
    public void save(Order order) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, order.getFilm().getId());
            preparedStatement.setInt(2, order.getUser().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(order.getDateSale()));
            preparedStatement.setDouble(4, order.getSum());
            preparedStatement.setString(5, order.getStatus().name());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving order");
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
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
    }

    @Override
    public void updateStatus(int orderId, OrderStatus status) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS);

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, orderId);

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error changing order status");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error changing order status", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }

    }

    @Override
    public boolean delete(int id) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_ORDER);

            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting order", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
    }

    @Override
    public Order get(int id) throws DAOException {
        Order order = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ORDER);

            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));
                    order.setFilm(new Film(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getString(6)));
                    order.setDateSale(rs.getTimestamp(7).toLocalDateTime());
                    order.setSum(rs.getDouble(8));
                    order.setStatus(OrderStatus.valueOf(rs.getString(9).toUpperCase()));
                    order.setUser(new User(rs.getInt(10), rs.getString(11)));
                }
                else{
                    throw new DAOException("Error getting order by id");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting order by id", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
        return order;
    }

    @Override
    public List<Order> getOrdersOfUser(int userId, OrderStatus status) throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_OF_USER);

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, status.name());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                Order order = null;

                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));
                    order.setFilm(new Film(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getString(6)));
                    order.setDateSale(rs.getTimestamp(7).toLocalDateTime());
                    order.setSum(rs.getDouble(8));
                    order.setStatus(OrderStatus.valueOf(rs.getString(9).toUpperCase()));

                    allOrders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all orders of user", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
        return allOrders;
    }

    @Override
    public List<Order> getAllOfFilm(int filmId) throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_OF_FILM);

            preparedStatement.setInt(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Order order = null;

                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    order.setDateSale(rs.getTimestamp(4).toLocalDateTime());
                    order.setSum(rs.getDouble(5));
                    order.setStatus(OrderStatus.valueOf(rs.getString(6).toUpperCase()));

                    allOrders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all orders of film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
        return allOrders;
    }

    @Override
    public List<Order> getAll() throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                Order order = null;

                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    order.setDateSale(rs.getTimestamp(4).toLocalDateTime());
                    order.setSum(rs.getDouble(5));
                    order.setStatus(OrderStatus.valueOf(rs.getString(6).toUpperCase()));

                    allOrders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all orders", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
        return allOrders;
    }

    @Override
    public double getTotalAmount(int userId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_TOTAL_AMOUNT_OF_USER);

            preparedStatement.setInt(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return rs.getDouble(1);
                }
                else{
                    throw new DAOException("Error getting total amount of orders");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting total amount of orders", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in order dao", e);
                }
            }
        }
    }
}
