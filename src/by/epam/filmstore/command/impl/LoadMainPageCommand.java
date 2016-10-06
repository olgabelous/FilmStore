package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command gets lists of films and sends user to main page.
 *
 * @author Olga Shahray
 */
public class LoadMainPageCommand implements Command {

    private static final int YEAR = 2016;
    private static final int LIMIT = 12;
    private static final String NEW_FILMS = "newfilms";
    private static final String BEST_FILMS = "bestfilms";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(LoadMainPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            List<Film> newFilmList = filmService.getByYear(YEAR, 0, LIMIT);
            request.setAttribute(NEW_FILMS, newFilmList);

            List<Film> bestFilmList = filmService.getAll(ParameterAndAttributeName.RATING, 0, LIMIT).getObjectList();
            request.setAttribute(BEST_FILMS, bestFilmList);

            request.getRequestDispatcher(PageName.COMMON_MAIN_PAGE).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        }catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
