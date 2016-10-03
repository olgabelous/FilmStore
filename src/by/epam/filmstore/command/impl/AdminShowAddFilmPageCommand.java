package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.service.IFilmMakerService;
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
 * Created by Olga Shahray on 01.09.2016.
 */
public class AdminShowAddFilmPageCommand implements Command {
    private static final String ID = "id";
    private static final String PAGE = "page";
    private static final String FILM = "film";
    private static final String GENRES = "genres";
    private static final String FILM_MAKERS = "filmMakers";
    private static final String COUNTRIES = "countries";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_PAGE = "/errorPage.jsp";
    private static final String FILMS_PAGE = "Controller?command=admin-get-films";

    private static final Logger LOG = LogManager.getLogger(AdminSaveFilmCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IFilmMakerService filmMakerService = ServiceFactory.getInstance().getFilmMakerService();
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();
        int page = 1;

        try {
            String pageNum = request.getParameter(PAGE);
            if ( pageNum!= null && !pageNum.isEmpty()) {
                page = Integer.parseInt(pageNum);
            }

            Film film = null;
            String id = request.getParameter(ID);
            if(id != null && !id.isEmpty()) {
                film = filmService.get(Integer.parseInt(id));
            }
            List<FilmMaker> filmMakers = filmMakerService.getAll();

            request.setAttribute(FILM, film);
            request.setAttribute(FILM_MAKERS, filmMakers);
            request.setAttribute(PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_ADD_FILM_PAGE).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(FILMS_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
