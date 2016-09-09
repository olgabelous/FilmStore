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
 * Created by Olga Shahray on 23.07.2016.
 */
public class SaveNewUserCommand implements Command {
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    private static final String USER = "user";
    private static final String INDEX_PAGE = "/index.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name = request.getParameter(NAME);
        String email = request.getParameter(EMAIL);
        String pass = request.getParameter(PASSWORD);
        String phone = request.getParameter(PHONE);

        IUserService service = ServiceFactory.getInstance().getUserService();

        HttpSession session = request.getSession(true);

        try{
            User user = service.saveUser(name, email, pass, phone);
            session.setAttribute(USER, user);
            response.sendRedirect(INDEX_PAGE);

        }catch(ServiceException e){
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }

    }
}
