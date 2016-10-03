package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
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
 * @author Olga Shahray
 */
public class UserSaveFavoriteFilmCommand implements Command {

    private static final String ID = "id";
    private static final String USER = "user";
    private static final String FILM_PAGE = "Controller?command=get-film-by-id&id=";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserSaveFavoriteFilmCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(USER);
        try {
            int filmId = Integer.parseInt(request.getParameter(ID));

            IFilmService filmService = ServiceFactory.getInstance().getFilmService();

            filmService.saveFavotiteFilm(loggedUser.getId(), filmId);

            response.sendRedirect(FILM_PAGE+filmId);

        } catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
