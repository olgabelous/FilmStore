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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            int id = Integer.parseInt(request.getParameter(ID));

            Film film = filmService.get(id);

            request.setAttribute("film", film);

            request.getRequestDispatcher("/WEB-INF/jsp/singleFilm.jsp").forward(request, response);


        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
