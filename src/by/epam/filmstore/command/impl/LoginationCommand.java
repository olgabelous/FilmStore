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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        IUserService userService = ServiceFactory.getInstance().getUserService();

        HttpSession session = request.getSession(true);
		/*String query = QueryUtil.createHttpQueryString(request);
        request.getSession(true).setAttribute("prev_query", query);
		System.out.println(query);*/

        try {
            User user = userService.authorize(login, password);

            //request.setAttribute("user", user);
            session.setAttribute("user", user);
            response.sendRedirect("/index.jsp");

        } catch (ServiceAuthException e) {

            request.setAttribute("errorMessage", "Wrong login or password");

            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (ServiceException e) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }
}
