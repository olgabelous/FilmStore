package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 28.08.2016.
 */
public class AdminDeleteUserCommand implements Command {
    private static final String ID = "id";
    private static final String USERS_PAGE = "Controller?command=admin-get-users";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final int STATUS_OK = 200;
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_MESSAGE_TEXT = "Not found";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IUserService userService = ServiceFactory.getInstance().getUserService();

        try {
            int id = Integer.parseInt(request.getParameter(ID));

            boolean isDeleted = userService.delete(id);

            if(isDeleted) {
                response.setStatus(STATUS_OK);
                response.sendRedirect(USERS_PAGE);
            }
            else{
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }

        } catch (ServiceException | NumberFormatException e ) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
