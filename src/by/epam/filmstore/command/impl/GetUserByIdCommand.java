package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>Command sends user to user profile page. It checks access right of
 * requested user.
 * </p>
 *
 * @author Olga Shahray
 */
public class GetUserByIdCommand implements Command {

    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final String ACCESS_DENIED_ERROR = "accessDeniedError";
    private static final String MESSAGE = "Please log in";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(GetUserByIdCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
            if (loggedUser != null) {
                try {
                    int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));
                    if (loggedUser.getId() == id) {
                        request.getRequestDispatcher(PageName.USER_PROFILE_PAGE).forward(request, response);
                    }
                    else{
                        LOG.warn("Logged user id and requested id is not identical");
                        response.sendError(ERROR_STATUS);
                    }
                }  catch (NumberFormatException e) {
                    LOG.warn("User id is not a number", e);
                    response.sendError(ERROR_STATUS);
                }
            } else {
                LOG.warn("Access denied. Logged user is null");
                request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
                request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
            }
        } else {
            LOG.warn("Access denied. Session is null");
            request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }
    }
}
