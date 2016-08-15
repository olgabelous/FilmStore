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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IUserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(false);

        try {
            int id = Integer.parseInt(request.getParameter(ID));
            if (session != null){
                User loggedUser = (User)session.getAttribute("user");
                if(loggedUser.getId() == id){
                    User user = userService.get(id);

                    request.setAttribute("user", user);

                    request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("errorMessage", "Access denied");
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
