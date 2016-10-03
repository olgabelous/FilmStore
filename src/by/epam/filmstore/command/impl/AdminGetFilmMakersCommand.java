package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
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
 * @author Olga Shahray
 */
public class AdminGetFilmMakersCommand implements Command {
    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String FILM_MAKER_LIST = "filmMakerList";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminGetFilmMakersCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmMakerService filmMakerService = ServiceFactory.getInstance().getFilmMakerService();

        try {
            int page = 1;
            int recordsPerPage = 6;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            PagingListDTO<FilmMaker> result = filmMakerService.getAll((page - 1) * recordsPerPage, recordsPerPage);
            List<FilmMaker> filmMakerList = result.getObjectList();
            int numOfRecords = result.getCount();

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(FILM_MAKER_LIST, filmMakerList);
            request.setAttribute(TOTAL_PAGES, totalPages);
            request.setAttribute(CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_FILM_MAKERS).forward(request, response);

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
