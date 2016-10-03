package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.service.ICountryService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Olga Shahray
 */
public class AdminGetCountriesCommand implements Command {

    private static final String COUNTRY_LIST = "countryList";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminGetCountriesCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ICountryService countryService = ServiceFactory.getInstance().getCountryService();

        try {
            List<Country> countryList = countryService.getAll();

            request.setAttribute(COUNTRY_LIST, countryList);

            request.getRequestDispatcher(PageName.ADMIN_COUNTRIES).forward(request, response);

        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
