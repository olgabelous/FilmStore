package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Country;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface ICountryDAO {

    void save(Country country) throws DAOException;

    void update(Country country) throws DAOException;

    // false if not found
    boolean delete(int countryId) throws DAOException;

    List<Country> getAll() throws DAOException;


}
