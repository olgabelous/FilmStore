package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceAuthException;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginationCommand implements Command {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String INDEX_PAGE = "index.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_TEXT = "Wrong login or password";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        IUserService userService = ServiceFactory.getInstance().getUserService();

        HttpSession session = request.getSession(true);

        try {
            User user = userService.authorize(login, password);

            session.setAttribute(USER, user);
            response.sendRedirect(INDEX_PAGE);

        } catch (ServiceAuthException e) {

            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);

        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }

    }
}
