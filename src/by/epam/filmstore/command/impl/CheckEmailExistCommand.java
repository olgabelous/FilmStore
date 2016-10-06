package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Command checks if email exists in database. It gets from database number of records
 * with such email.</p>
 *
 * @author Olga Shahray
 */
public class CheckEmailExistCommand implements Command {

    private static final String CONTENT_TYPE = "text/plain";
    private static final Logger LOG = LogManager.getLogger(CheckEmailExistCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(ParameterAndAttributeName.EMAIL);
        IUserService service = ServiceFactory.getInstance().getUserService();
        response.setContentType(CONTENT_TYPE);
        try(PrintWriter writer =  response.getWriter()){
            int i = service.checkEmail(email);
            writer.write(String.valueOf(i));
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
