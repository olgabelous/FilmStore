package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class SaveNewUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String phone = request.getParameter("phone");

        ServiceFactory factory = ServiceFactory.getInstance();
        IUserService service = factory.getUserService();

        try{
            service.saveUser(name, email, pass, phone);
        }catch(ServiceException e){
            // logging
            try {
                response.sendRedirect("/");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
