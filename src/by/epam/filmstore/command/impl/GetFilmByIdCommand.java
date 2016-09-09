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

/**
 * Created by Olga Shahray on 10.08.2016.
 */
public class GetFilmByIdCommand implements Command {

    private static final String ID = "id";
    private static final String FILM = "film";
    private static final String FILM_PAGE = "/WEB-INF/jsp/singleFilm.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            int id = Integer.parseInt(request.getParameter(ID));

            Film film = filmService.get(id);

            request.setAttribute(FILM, film);

            request.getRequestDispatcher(FILM_PAGE).forward(request, response);


        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
