package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.IGenreService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 10.08.2016.
 */
public class LoadMainPageCommand implements Command {

    private static final String YEAR = "year";
    private static final String RATING = "rating";
    private static final int LIMIT = 6;
    private static final String NEW_FILMS = "newfilms";
    private static final String BEST_FILMS = "bestfilms";
    private static final String MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final String GENRE_LIST = "genreList";
    //private static final String COUNTRY = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String year = request.getParameter(YEAR);

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();
        IGenreService genreService = ServiceFactory.getInstance().getGenreService();

        try {
            List<Film> newFilmList = filmService.getByYear(year,0, LIMIT);
            request.setAttribute(NEW_FILMS, newFilmList);

            List<Film> bestFilmList = filmService.getAll(RATING, LIMIT);
            request.setAttribute(BEST_FILMS, bestFilmList);

            List<Genre> genreList = genreService.getAll();
            request.setAttribute(GENRE_LIST, genreList);

            request.getRequestDispatcher(MAIN_PAGE).forward(request, response);


        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
