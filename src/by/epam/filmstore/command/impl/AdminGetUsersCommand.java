package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class AdminGetUsersCommand implements Command {

    private static final String USER_LIST = "userList";
    private static final String USER_PAGE = "/WEB-INF/jsp/admin/users.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final int LIMIT = 1000;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IUserService userService = ServiceFactory.getInstance().getUserService();

        try {
            List<User> userList = userService.getAll(LIMIT);

            request.setAttribute(USER_LIST, userList);

            request.getRequestDispatcher(USER_PAGE).forward(request, response);

        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
