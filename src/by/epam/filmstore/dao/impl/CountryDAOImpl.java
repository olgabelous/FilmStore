package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.ICountryDAO;
import by.epam.filmstore.dao.PartOfTransaction;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class allows to perform CRUD operations with entity Country.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class CountryDAOImpl extends AbstractDAO implements ICountryDAO {

    private static final String INSERT_COUNTRY = "INSERT INTO country (country) VALUES(?)";
    private static final String DELETE_COUNTRY = "DELETE FROM country WHERE id = ?";
    private static final String SELECT_ALL_COUNTRIES = "SELECT id, country FROM country";
    private static final String UPDATE_COUNTRY = "UPDATE country SET country = ? WHERE id = ?";

    /**
     *  Method saves @param country in database
     *
     * @param country
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void save(Country country) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_COUNTRY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, country.getCountryName());
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error saving country");
            }
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    country.setId(rs.getInt(1));
                } else {
                    throw new DAOException("Error getting country id");
                }
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving country", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in country dao", e);
                }
            }
        }
    }

    /**
     * Method updates existing country
     *
     * @param country
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void update(Country country) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_COUNTRY);

            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.setInt(2, country.getId());
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error updating country");
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating country", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in country dao", e);
                }
            }
        }
    }
    /**
     *
     * @param countryId
     * @return boolean result if country was deleted
     * @throws DAOException
     */
    @Override
    public boolean delete(int countryId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_COUNTRY);

            preparedStatement.setInt(1, countryId);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting country", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in country dao", e);
                }
            }
        }
    }

    /**
     * @return a {@code List<Country>} all countries from database
     * @throws DAOException
     */
    @Override
    public List<Country> getAll() throws DAOException {
        List<Country> allCountries = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_COUNTRIES);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                Country country = null;
                while(rs.next()) {
                    country = new Country();
                    country.setId(rs.getInt(1));
                    country.setCountryName(rs.getString(2));

                    allCountries.add(country);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all countries", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in country dao", e);
                }
            }
        }
        return allCountries;
    }
}
