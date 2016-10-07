package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IDiscountDAO;
import by.epam.filmstore.dao.PartOfTransaction;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class allows to perform CRUD operations with entity Discount.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class DiscountDAOImpl extends AbstractDAO implements IDiscountDAO {

    private static final String INSERT_DISCOUNT = "INSERT INTO discount (sum_from, value) VALUES (?,?)";
    private static final String SELECT_ALL_DISCOUNTS = "SELECT discount_id, discount.sum_from, discount.value " +
            "FROM discount ORDER BY value";
    private static final String DELETE_DISCOUNT = "DELETE FROM discount WHERE discount_id = ?";
    private static final String UPDATE_DISCOUNT = "UPDATE discount SET discount.sum_from = ?, discount.value = ? WHERE discount_id = ?";
    private static final String SELECT_USER_DISCOUNT = "SELECT discount_id, discount.sum_from, discount.value FROM filmstore.discount " +
            " WHERE sum_from <= (SELECT COALESCE((SELECT SUM(orders.sum) FROM orders WHERE orders.user_id = ? AND orders.status = 'paid' " +
            "),0)) ORDER BY sum_from DESC LIMIT 1";

    /**
     * Method saves @param discount in database
     * @param discount
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void save(Discount discount) throws DAOException {
        PreparedStatement preparedStatement = null;

        try{
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_DISCOUNT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDouble(1, discount.getSumFrom());
            preparedStatement.setDouble(2, discount.getValue());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving discount");
            }
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if(rs.next()) {
                    discount.setId(rs.getInt(1));
                }
                else{
                    throw new DAOException("Error getting discount id");
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving discount", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }

    /**
     * Method updates existing discount
     * @param discount
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void update(Discount discount) throws DAOException {
        PreparedStatement preparedStatement = null;

        try{
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_DISCOUNT);

            preparedStatement.setDouble(1, discount.getSumFrom());
            preparedStatement.setDouble(2, discount.getValue());
            preparedStatement.setInt(3, discount.getId());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error updating discount");
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating discount", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }

    /**
     * @param userId
     * @return Discount calculated on basis of paid user's orders
     * @throws DAOException
     */
    @Override
    public Discount getUserDiscount(int userId) throws DAOException {
        Discount discount = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_USER_DISCOUNT);

            preparedStatement.setInt(1, userId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    discount = new Discount();
                    discount.setId(rs.getInt(1));
                    discount.setSumFrom(rs.getDouble(2));
                    discount.setValue(rs.getDouble(3));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting discount", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
        return discount;
    }

    /**
     * @param discountId
     * @return boolean result if discount was deleted
     * @throws DAOException
     */
    @Override
    public boolean delete(int discountId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_DISCOUNT);

            preparedStatement.setInt(1, discountId);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting discount", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
    }

    /**
     * @return a {@code List<Discount>} all discounts from database
     * @throws DAOException
     */
    @Override
    public List<Discount> getAll() throws DAOException {
        List<Discount> discountList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_DISCOUNTS);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                Discount discount = null;
                while (rs.next()) {
                    discount = new Discount();
                    discount.setId(rs.getInt(1));
                    discount.setSumFrom(rs.getDouble(2));
                    discount.setValue(rs.getDouble(3));

                    discountList.add(discount);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all discounts", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in comment dao", e);
                }
            }
        }
        return discountList;
    }
}
