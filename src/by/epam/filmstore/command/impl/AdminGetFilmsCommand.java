package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
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
 * <p>Command implements a request of user with role ADMIN to show
 * films. Is shown 6 records per page in order of addition date (new records on the top).
 * Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminGetFilmsCommand implements Command {

    private static final String ORDER = "date_add";
    private static final Logger LOG = LogManager.getLogger(AdminGetFilmsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            int page = 1;
            int recordsPerPage = 6;
            if (request.getParameter(ParameterAndAttributeName.PAGE) != null) {
                page = Integer.parseInt(request.getParameter(ParameterAndAttributeName.PAGE));
            }
            PagingListDTO<Film> result = filmService.getAll(ORDER, (page - 1) * recordsPerPage, recordsPerPage);

            int numOfRecords = result.getCount();
            List<Film> filmList = result.getObjectList();

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(ParameterAndAttributeName.FILM_LIST, filmList);
            request.setAttribute(ParameterAndAttributeName.TOTAL_PAGES, totalPages);
            request.setAttribute(ParameterAndAttributeName.CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_FILMS).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
