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
    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "totalPage";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String FILM_LIST = "filmlist";
    private static final String FILMS_PAGE = "/WEB-INF/jsp/films.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String year = request.getParameter(YEAR);

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        int page = 1;
        int recordsPerPage = 3;
        if(request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));

        try {
            List<Film> filmList = filmService.getByYear(year, (page-1)*recordsPerPage, recordsPerPage);
            int noOfRecords = 10;//dao.getNoOfRecords();
            int totalPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(FILM_LIST, filmList);
            request.setAttribute(TOTAL_PAGES, totalPages);
            request.setAttribute(CURRENT_PAGE, page);

            request.getRequestDispatcher(FILMS_PAGE).forward(request, response);


        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
