package by.epam.filmstore.controller.listener;

import by.epam.filmstore.domain.Country;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.ICountryService;
import by.epam.filmstore.service.IGenreService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * Listener that gets genre list and country list and sets them in session as attributes.
 *
 * @author Olga Shahray
 */
public class FilmstoreHttpSessionListener implements HttpSessionListener {

    private static final String GENRE_LIST = "genreList";
    private static final String COUNTRY_LIST = "countryList";

    private static final Logger LOG = LogManager.getLogger(FilmstoreHttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        IGenreService genreService = ServiceFactory.getInstance().getGenreService();
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();
        try {
            List<Genre> genreList = genreService.getAll();
            session.setAttribute(GENRE_LIST, genreList);

            List<Country> countryList = countryService.getAll();
            session.setAttribute(COUNTRY_LIST, countryList);
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            //response.sendError(ERROR_STATUS);
        }catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            //response.sendError(ERROR_STATUS);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //do nothing
    }
}
