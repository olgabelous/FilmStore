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
 * Command implements a request to find films according to search query.
 *
 * @author Olga Shahray
 */
public class SearchFilmCommand implements Command {

    private static final String WARNING = "warning";
    private static final String WARNING_TEXT = "Sorry, we don't have such films. Please choose another params.";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(SearchFilmCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchLine = request.getParameter(ParameterAndAttributeName.Q);
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            List<Film> filmList = filmService.search(searchLine);

            if(filmList.size() == 0){
                request.setAttribute(WARNING, WARNING_TEXT);
            }
            else{
                request.setAttribute(ParameterAndAttributeName.FILM_LIST, filmList);
            }

            request.setAttribute(ParameterAndAttributeName.SEARCH_QUERY, searchLine);

            request.getRequestDispatcher(PageName.COMMON_FILMS_PAGE).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
