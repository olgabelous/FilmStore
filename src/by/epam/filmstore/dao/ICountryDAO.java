package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Country;

import java.util.List;

/**
 * Created by Olga Shahray on 18.07.2016.
 */
public interface ICountryDAO {

    void save(Country country) throws DAOException;

    void update(Country country) throws DAOException;

    // false if not found
    boolean delete(int countryId) throws DAOException;

    // null if not found
    Country get(int countryId) throws DAOException;

    List<Country> getAll() throws DAOException;


}
