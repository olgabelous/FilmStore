package by.epam.filmstore.service;

import by.epam.filmstore.domain.Country;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public interface ICountryService {

    void save(String countryName) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<Country> getAll() throws ServiceException;
}
