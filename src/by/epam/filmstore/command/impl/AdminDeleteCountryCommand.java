package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.service.ICountryService;
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
 * <p>Command implements a request of user with role ADMIN to delete
 * country. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminDeleteCountryCommand  implements Command {

    private static final String COUNTRY_PAGE = "Controller?command=admin-get-countries";
    private static final String ERROR_MESSAGE_TEXT = "Country was not found";
    private static final Logger LOG = LogManager.getLogger(AdminDeleteCountryCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();

        try {
            int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));

            boolean isDeleted = countryService.delete(id);

            if(isDeleted) {
                response.sendRedirect(COUNTRY_PAGE);
                LOG.info("Country id="+id+" was deleted");
            }
            else{
                LOG.warn(ERROR_MESSAGE_TEXT);
                request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(COUNTRY_PAGE).forward(request, response);
            }

        }catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(COUNTRY_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
