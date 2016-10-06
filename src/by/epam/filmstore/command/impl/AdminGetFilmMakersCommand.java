package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IFilmMakerService;
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
 * film makers. Is shown 6 records per page.
 * Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminGetFilmMakersCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AdminGetFilmMakersCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmMakerService filmMakerService = ServiceFactory.getInstance().getFilmMakerService();

        try {
            int page = 1;
            int recordsPerPage = 6;
            String pageNum = request.getParameter(ParameterAndAttributeName.PAGE);
            if (pageNum != null) {
                page = Integer.parseInt(pageNum);
            }
            PagingListDTO<FilmMaker> result = filmMakerService.getAll((page - 1) * recordsPerPage, recordsPerPage);
            List<FilmMaker> filmMakerList = result.getObjectList();
            int numOfRecords = result.getCount();

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(ParameterAndAttributeName.FILM_MAKER_LIST, filmMakerList);
            request.setAttribute(ParameterAndAttributeName.TOTAL_PAGES, totalPages);
            request.setAttribute(ParameterAndAttributeName.CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_FILM_MAKERS).forward(request, response);

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
