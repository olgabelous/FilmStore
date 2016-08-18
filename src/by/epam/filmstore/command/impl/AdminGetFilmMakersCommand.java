package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.service.IFilmMakerService;
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
public class AdminGetFilmMakersCommand implements Command {

    private static final String FILM_MAKER_LIST = "filmMakerList";
    private static final String FILM_MAKERS_PAGE = "/WEB-INF/jsp/admin/filmMakers.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final int LIMIT = 1000;
    private static final String ORDER = "name";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmMakerService filmMakerService = ServiceFactory.getInstance().getFilmMakerService();
        HttpSession session = request.getSession(false);

        try {
            if (session != null){

                List<FilmMaker> filmMakerList = filmMakerService.getAll(ORDER, LIMIT);

                request.setAttribute(FILM_MAKER_LIST, filmMakerList);

                request.getRequestDispatcher(FILM_MAKERS_PAGE).forward(request, response);
            }
            else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }


        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
