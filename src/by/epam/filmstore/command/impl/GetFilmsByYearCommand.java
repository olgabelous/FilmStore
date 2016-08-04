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
 * Created by Olga Shahray on 03.08.2016.
 */
public class GetFilmsByYearCommand implements Command {

    private static final String YEAR = "year";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String year = request.getParameter(YEAR);

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            List<Film> filmList = filmService.getByYear(year);

            request.setAttribute("filmlist", filmList);

            request.getRequestDispatcher("/WEB-INF/jsp/films.jsp").forward(request, response);


        } catch (ServiceException e) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
