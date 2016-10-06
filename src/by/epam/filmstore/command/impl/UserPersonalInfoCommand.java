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
 * <p>Command implements a request of user to show personal
 * information of user. Command checks access right.</p>
 *
 * @author Olga Shahray
 */
public class UserPersonalInfoCommand implements Command {

    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final String ACCESS_DENIED_ERROR = "accessDeniedError";
    private static final String MESSAGE = "Please log in";
    private static final Logger LOG = LogManager.getLogger(UserGetOrdersCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
            if (loggedUser != null) {

                request.getRequestDispatcher(PageName.USER_PERSONAL_INFO_PAGE).forward(request, response);

            } else {
                LOG.warn("Access denied to command user-personal-info. Logged user is null");
                request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
                request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
            }
        } else {
            LOG.warn("Access denied to command user-personal-info. Session is null");
            request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }
    }
}
