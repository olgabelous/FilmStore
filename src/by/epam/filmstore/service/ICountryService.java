package by.epam.filmstore.service;

import by.epam.filmstore.domain.Country;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * @author Olga Shahray
 */
public interface ICountryService {

    void save(String countryName) throws ServiceException;

    void update(int id, String countryName) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Country> getAll() throws ServiceException;
}
