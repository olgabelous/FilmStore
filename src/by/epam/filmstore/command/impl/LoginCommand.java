package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceAuthException;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        IUserService userService = ServiceFactory.getInstance().getUserService();

		/*String query = QueryUtil.createHttpQueryString(request);
        request.getSession(true).setAttribute("prev_query", query);
		System.out.println(query);*/

        try {
            User user = userService.authorize(login, password);

            request.setAttribute("user", user);
            if (user.getRole() == Role.ADMIN) {
                request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
            }
            else if(user.getRole() == Role.USER){
                request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
            }
        } catch (ServiceAuthException e) {

            request.setAttribute("errorMessage", "Wrong login or password");

            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (ServiceException e) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }
}
