package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class AdminGetFilmsCommand implements Command {

    private static final String FILM_LIST = "filmList";
    private static final String FILMS_PAGE = "/WEB-INF/jsp/admin/films.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final int LIMIT = 1000;
    private static final String ORDER = "release_year";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            List<Film> filmList = filmService.getAll(ORDER, LIMIT);

            request.setAttribute(FILM_LIST, filmList);

            request.getRequestDispatcher(FILMS_PAGE).forward(request, response);

        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
