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
 * Created by Olga Shahray on 10.08.2016.
 */
public class LoadMainPageCommand implements Command {

    private static final String YEAR = "year";
    private static final String RATING = "rating";
    private static final int LIMIT = 6;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String year = request.getParameter(YEAR);

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            List<Film> newFilmList = filmService.getByYear(year,LIMIT);

            request.setAttribute("newfilms", newFilmList);

            List<Film> bestFilmList = filmService.getAll(RATING, LIMIT);

            request.setAttribute("bestfilms", bestFilmList);

            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);


        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
