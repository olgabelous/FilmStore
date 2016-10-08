package by.epam.filmstore.controller.listener;

import by.epam.filmstore.domain.Country;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.IConnectionPoolManager;
import by.epam.filmstore.service.ICountryService;
import by.epam.filmstore.service.IGenreService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Listener that initializes pool connection before web application is started
 * and destroys it when web application is ended.
 *
 * @author Olga Shahray
 */
public class FilmstoreServletContextListener implements ServletContextListener {
    private static final String GENRE_LIST = "genreList";
    private static final String COUNTRY_LIST = "countryList";

    private static final Logger LOG = LogManager.getLogger(FilmstoreServletContextListener.class);

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        IConnectionPoolManager poolManager = ServiceFactory.getInstance().getPoolManager();
        IGenreService genreService = ServiceFactory.getInstance().getGenreService();
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();

        ServletContext servletContext = arg0.getServletContext();

        try {
            poolManager.init();

            List<Genre> genreList = genreService.getAll();
            servletContext.setAttribute(GENRE_LIST, genreList);

            List<Country> countryList = countryService.getAll();
            servletContext.setAttribute(COUNTRY_LIST, countryList);

        } catch (ServiceConnectionPoolManagerException e) {
            LOG.error("Connection pool is not created", e);
            e.printStackTrace();
            throw new RuntimeException("Connection pool is not created");
        }catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            throw new RuntimeException(e);
        }catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            throw new RuntimeException(e);
        }
        LOG.info("Connection pool is created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        IConnectionPoolManager poolManager = ServiceFactory.getInstance().getPoolManager();
        try {
            poolManager.destroy();
        } catch (ServiceConnectionPoolManagerException e) {
            e.printStackTrace();
        }
        LOG.info("Connection pool is destroyed");
    }
}

