package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
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
 * @author Olga Shahray
 */
public class AdminUpdateUserCommand implements Command {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    private static final String PHOTO = "photo";
    private static final String DATE_REG = "date-reg";
    private static final String ROLE = "role";
    private static final String USERS_PAGE = "Controller?command=admin-get-users";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminUpdateUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(NAME);
        String email = request.getParameter(EMAIL);
        String pass = request.getParameter(PASSWORD);
        String phone = request.getParameter(PHONE);
        String photo = request.getParameter(PHOTO);
        String dateReg = request.getParameter(DATE_REG);
        String role = request.getParameter(ROLE);

        IUserService service = ServiceFactory.getInstance().getUserService();
        try{
            int id = Integer.parseInt(request.getParameter(ID));
            service.update(id, name, email, pass, phone, photo, dateReg, role);
            response.sendRedirect(USERS_PAGE);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(USERS_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
