package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.service.IFilmMakerService;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>Command send user with role ADMIN to page of addition/updating film.
 * Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminShowAddFilmPageCommand implements Command {

    private static final String FILMS_PAGE = "Controller?command=admin-get-films";
    private static final Logger LOG = LogManager.getLogger(AdminSaveFilmCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IFilmMakerService filmMakerService = ServiceFactory.getInstance().getFilmMakerService();
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();
        int page = 1;

        try {
            String pageNum = request.getParameter(ParameterAndAttributeName.PAGE);
            if ( pageNum!= null && !pageNum.isEmpty()) {
                page = Integer.parseInt(pageNum);
            }

            Film film = null;
            String id = request.getParameter(ParameterAndAttributeName.ID);
            if(id != null && !id.isEmpty()) {
                film = filmService.get(Integer.parseInt(id));
            }
            List<FilmMaker> filmMakers = filmMakerService.getAll();

            ServletContext servletContext = request.getServletContext();

            servletContext.setAttribute(ParameterAndAttributeName.FILM_MAKER_LIST, filmMakers);
            request.setAttribute(ParameterAndAttributeName.FILM, film);
            request.setAttribute(ParameterAndAttributeName.PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_ADD_FILM_PAGE).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(FILMS_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
