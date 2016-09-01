package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Olga Shahray on 29.08.2016.
 */
public class AdminAddFilmCommand implements Command {

    private static final String TITLE = "title";
    private static final String YEAR = "year";
    private static final String COUNTRY_ID = "countryId";
    private static final String DURATION = "duration";
    private static final String AGE_RESTRICTION = "ageRestriction";
    private static final String PRICE = "price";
    private static final String LINK = "link";
    private static final String GENRES = "genres";
    private static final String DIRECTOR = "director";
    private static final String ACTORS = "actors";
    private static final String DESCRIPTION = "description";

    private static final String FILMS_PAGE = "/FilmStore/UserServlet?command=admin-get-films";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String EXCEPTION = "exception";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String title = request.getParameter(TITLE);
            int year = Integer.parseInt(request.getParameter(YEAR));
            int countryId = Integer.parseInt(request.getParameter(COUNTRY_ID));
            int duration = Integer.parseInt(request.getParameter(DURATION));
            int ageRestriction = Integer.parseInt(request.getParameter(AGE_RESTRICTION));
            String[] genres = request.getParameterValues(GENRES);
            String director = request.getParameter(DIRECTOR);
            String[] actors = request.getParameterValues(ACTORS);
            double price = Double.parseDouble(request.getParameter(PRICE));
            String link = request.getParameter(LINK);
            String description = request.getParameter(DESCRIPTION);
            //String[] filmMakers = new String[actors.length+1];
            String[] filmMakers = Arrays.copyOf(actors, actors.length+1);
            filmMakers[filmMakers.length-1] = director;

            IFilmService service = ServiceFactory.getInstance().getFilmService();

            service.save(genres, filmMakers, title, year, countryId, duration, ageRestriction, price, link, description);
            response.sendRedirect(FILMS_PAGE);

        }catch(ServiceException | NumberFormatException e){
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
