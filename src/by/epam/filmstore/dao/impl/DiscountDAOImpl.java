package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IDiscountDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 27.06.2016.
 *
 * Класс DiscountDAOImpl позволяет совершить CRUD операции с сущностью Скидка.
 * В каждом методе используется Connection, полученный из DAOHelper (см. AbstractDAO и DAOHelper).
 * Возврат Connection в пул происходит в DAOHelper
 */
public class DiscountDAOImpl extends AbstractDAO implements IDiscountDAO {

    private static final String INSERT_DISCOUNT = "INSERT INTO discount (sum_from, value) VALUES (?,?)";
    private static final String SELECT_DISCOUNT = "SELECT discount_id, discount.sum_from, discount.value FROM discount " +
            "WHERE discount_id=?";
    private static final String SELECT_ALL_DISCOUNTS = "SELECT discount_id, discount.sum_from, discount.value " +
            "FROM discount ORDER BY value";
    private static final String DELETE_DISCOUNT = "DELETE FROM discount WHERE discount_id = ?";
    private static final String UPDATE_DISCOUNT = "UPDATE discount SET discount.sum_from = ?, discount.value = ? WHERE discount_id = ?";
    private static final String SELECT_USER_DISCOUNT = "SELECT discount_id, discount.sum_from, discount.value FROM filmstore.discount " +
            "WHERE sum_from <= (SELECT SUM(Orders.sum) FROM Orders WHERE Orders.user_id = ? AND Orders.status = 'paid') " +
            "ORDER BY sum_from DESC LIMIT 1";


    @Override
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

    @Override
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

    @Override
    public Discount get(int discountId) throws DAOException {
        Discount discount = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_DISCOUNT);

            preparedStatement.setInt(1, discountId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    discount = new Discount();
                    discount.setId(rs.getInt(1));
                    discount.setSumFrom(rs.getDouble(2));
                    discount.setValue(rs.getDouble(3));
                }
                else{
                    throw new DAOException("Error getting discount");
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

    @Override
    public List<Discount> getDiscountsList() throws DAOException {
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
