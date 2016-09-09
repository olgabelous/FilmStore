package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Olga Shahray on 14.08.2016.
 */
public class GetUserByIdCommand implements Command {
    private static final String ID = "id";
    private static final String USER = "user";
    private static final String USER_PAGE = "/WEB-INF/jsp/profile.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final String ERROR_MESSAGE = "Access denied";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IUserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(false);

        try {
            int id = Integer.parseInt(request.getParameter(ID));
            if (session != null){
                User loggedUser = (User)session.getAttribute(USER);
                if(loggedUser.getId() == id){
                    User user = userService.get(id);

                    request.setAttribute(USER, user);

                    request.getRequestDispatcher(USER_PAGE).forward(request, response);
                }
                else{
                    request.setAttribute(ERROR_ATTRIBUTE, ERROR_MESSAGE);
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
