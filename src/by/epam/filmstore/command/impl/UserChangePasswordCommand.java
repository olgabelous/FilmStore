package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
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
 * <p>Command implements a request of user to update password.
 * Command checks access right.</p>
 *
 * @author Olga Shahray
 */
public class UserChangePasswordCommand implements Command {
    private static final String OLD_PASSWORD = "oldPass";
    private static final String NEW_PASSWORD = "newPass1";
    private static final String CONFIRM_PASSWORD = "newPass2";

    private static final String USER_PAGE = "Controller?command=user-personal-info";
    private static final int ERROR_STATUS = 404;
    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final String ACCESS_DENIED_ERROR = "accessDeniedError";
    private static final String MESSAGE = "Please log in";
    private static final String MESSAGE1 = "Old password is not correct";

    private static final Logger LOG = LogManager.getLogger(UserChangePasswordCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
            if (loggedUser != null) {

                String oldPass = request.getParameter(OLD_PASSWORD);
                String newPass = request.getParameter(NEW_PASSWORD);
                String confPass = request.getParameter(CONFIRM_PASSWORD);

                if (loggedUser.getPass().equals(oldPass)) {
                    IUserService service = ServiceFactory.getInstance().getUserService();
                    try {
                        service.updateUserPass(loggedUser, newPass, confPass);
                        response.sendRedirect(USER_PAGE);

                    } catch (ServiceValidationException e) {
                        LOG.warn("Data is not valid", e);
                        request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
                        request.getRequestDispatcher(USER_PAGE).forward(request, response);

                    } catch (ServiceException e) {
                        LOG.error("Exception is caught", e);
                        response.sendError(ERROR_STATUS);
                    }
                }
                else{
                    LOG.warn("Old user password is not correct");
                    request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, MESSAGE1);
                    request.getRequestDispatcher(USER_PAGE).forward(request, response);
                }
            } else {
                LOG.warn("Access denied to command user-change-password. Logged user is null");
                request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
                request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
            }
        } else {
            LOG.warn("Access denied to command user-change-password. Session is null");
            request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }
    }
}
