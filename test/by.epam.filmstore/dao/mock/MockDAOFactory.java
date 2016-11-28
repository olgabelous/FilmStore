package by.epam.filmstore.dao.mock;

import by.epam.filmstore.dao.ICountryDAO;

/**
 * @author Olga Shahray
 */
public class MockDAOFactory {

    private static MockDAOFactory mockDAOFactory = new MockDAOFactory();

    public static MockDAOFactory getInstance(){
        return mockDAOFactory;
    }

    private ICountryDAO countryDAO = new MockCountryDAOImpl();

    public ICountryDAO getCountryDAO(){ return countryDAO; }

}
