package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICountryDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.service.ICountryService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class CountryServiceImpl implements ICountryService {

    @Override
    public void save(String countryName) throws ServiceException {
        if(ServiceValidation.isNullOrEmpty(countryName)){
            throw new ServiceValidationException("Country name must not be empty");
        }
        ICountryDAO dao = DAOFactory.getMySqlDAOFactory().getICountryDAO();
        Country country = new Country(countryName);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(country);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(int id, String countryName) throws ServiceException {
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException("Country id must be positive number!");
        }
        if(ServiceValidation.isNullOrEmpty(countryName)){
            throw new ServiceValidationException("Country name must not be empty");
        }
        ICountryDAO dao = DAOFactory.getMySqlDAOFactory().getICountryDAO();
        Country country = new Country(id, countryName);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(country);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException("Country id must be positive number!");
        }
        ICountryDAO dao = DAOFactory.getMySqlDAOFactory().getICountryDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Country> getAll() throws ServiceException {
        ICountryDAO dao = DAOFactory.getMySqlDAOFactory().getICountryDAO();
        try {
            return DAOHelper.execute(dao::getAll);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
