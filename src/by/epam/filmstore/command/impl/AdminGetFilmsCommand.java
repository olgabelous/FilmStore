package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Olga Shahray
 */
public class AdminGetFilmsCommand implements Command {

    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String FILM_LIST = "filmList";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ORDER = "date_add";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminGetFilmsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            int page = 1;
            int recordsPerPage = 6;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            PagingListDTO<Film> result = filmService.getAll(ORDER, (page - 1) * recordsPerPage, recordsPerPage);

            int numOfRecords = result.getCount();
            List<Film> filmList = result.getObjectList();

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(FILM_LIST, filmList);
            request.setAttribute(TOTAL_PAGES, totalPages);
            request.setAttribute(CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_FILMS).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
