package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IFilmMakerService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 29.08.2016.
 */
public class AdminAddFilmMakerCommand implements Command {
    private static final String NAME = "name";
    private static final String PROFESSION = "profession";
    private static final String FILM_MAKER_PAGE = "/FilmStore/UserServlet?command=admin-get-film-makers";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(NAME);
        String profession = request.getParameter(PROFESSION);

        IFilmMakerService service = ServiceFactory.getInstance().getFilmMakerService();

        try{
            service.save(name, profession);
            response.sendRedirect(FILM_MAKER_PAGE);

        }catch(ServiceException e){
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
