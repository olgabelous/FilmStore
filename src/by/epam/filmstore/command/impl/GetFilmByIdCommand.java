package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
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

/**
 * Command implements a request to get film by id.
 *
 * @author Olga Shahray
 */
public class GetFilmByIdCommand implements Command {

    private static final int ERROR_STATUS = 404;
    private static final Logger LOG = LogManager.getLogger(GetFilmByIdCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IFilmService filmService = ServiceFactory.getInstance().getFilmService();

        try {
            int filmId = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));

            Film film = filmService.get(filmId);
            HttpSession session = request.getSession(false);
            if (session != null) {
                User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
                if (loggedUser != null && loggedUser.getRole().equals(Role.USER)) {
                    boolean isFilmInWishList = filmService.isFavoriteFilm(loggedUser.getId(), filmId);
                    request.setAttribute(ParameterAndAttributeName.IS_IN_WISH_LIST, isFilmInWishList);
                }
            }
            request.setAttribute(ParameterAndAttributeName.FILM, film);

            request.getRequestDispatcher(PageName.COMMON_FILM_PAGE).forward(request, response);

        }
        catch (ServiceValidationException | NumberFormatException e){
            LOG.error("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
