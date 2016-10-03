package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Olga Shahray
 */
public class UserGetFavoriteFilmsCommand implements Command {
    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String USER = "user";
    private static final String FILM_LIST = "filmList";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserGetFavoriteFilmsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(USER);
        try {
            IFilmService filmService = ServiceFactory.getInstance().getFilmService();
            int page = 1;
            int recordsPerPage = 6;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            PagingListDTO<Film> result = filmService.getFavoriteFilms(loggedUser.getId(), (page - 1) * recordsPerPage, recordsPerPage);

            int numOfRecords = result.getCount();
            List<Film> filmList = result.getObjectList();

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(FILM_LIST, filmList);
            request.setAttribute(TOTAL_PAGES, totalPages);
            request.setAttribute(CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.USER_WISH_LIST_PAGE).forward(request, response);

        } catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}


