package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Command implements a request of user with role ADMIN to update
 * user information.
 * Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminUpdateUserCommand implements Command {

    private static final String USERS_PAGE = "Controller?command=admin-get-users";
    private static final Logger LOG = LogManager.getLogger(AdminUpdateUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(ParameterAndAttributeName.NAME);
        String email = request.getParameter(ParameterAndAttributeName.EMAIL);
        String pass = request.getParameter(ParameterAndAttributeName.PASSWORD);
        String phone = request.getParameter(ParameterAndAttributeName.PHONE);
        String photo = request.getParameter(ParameterAndAttributeName.PHOTO);
        String dateReg = request.getParameter(ParameterAndAttributeName.DATE_REG);
        String role = request.getParameter(ParameterAndAttributeName.ROLE);

        IUserService service = ServiceFactory.getInstance().getUserService();
        try{
            int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));
            service.update(id, name, email, pass, phone, photo, dateReg, role);
            response.sendRedirect(USERS_PAGE);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(USERS_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
