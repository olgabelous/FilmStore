package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.IGenreService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class AdminGetGenresCommand implements Command {

    private static final String GENRE_LIST = "genreList";
    private static final String GENRES_PAGE = "/WEB-INF/jsp/admin/genres.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        IGenreService genreService = ServiceFactory.getInstance().getGenreService();
        HttpSession session = request.getSession(false);

        try {
            if (session != null){

                List<Genre> genreList = genreService.getAll();

                request.setAttribute(GENRE_LIST, genreList);

                request.getRequestDispatcher(GENRES_PAGE).forward(request, response);
            }
            else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }


        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
