package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.ICountryDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 18.07.2016.
 */
public class CountryDAOImpl extends AbstractDAO implements ICountryDAO {

    private static final String INSERT_COUNTRY = "INSERT INTO country (country) VALUES(?)";
    private static final String SELECT_COUNTRY = "SELECT id, country FROM country WHERE id = ?";
    private static final String DELETE_COUNTRY = "DELETE FROM country WHERE id = ?";
    private static final String SELECT_ALL_COUNTRIES = "SELECT id, country FROM country";

    @Override
    public void save(Country country) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_COUNTRY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.executeUpdate();
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

    @Override
    public boolean delete(int countryId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
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

    @Override
    public Country get(int countryId) throws DAOException {
        Country country = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COUNTRY);

            preparedStatement.setInt(1, countryId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    country = new Country();
                    country.setId(rs.getInt(1));
                    country.setCountryName(rs.getString(2));
                }
                else{
                    throw new DAOException("Error getting country by id");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting country by id", e);
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
        return country;
    }

    @Override
    public List<Country> getAll() throws DAOException {
        List<Country> allCountries = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
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
