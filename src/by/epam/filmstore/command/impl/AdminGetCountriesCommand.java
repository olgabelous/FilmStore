package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.service.ICountryService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class AdminGetCountriesCommand implements Command {

    private static final String COUNTRY_LIST = "countryList";
    private static final String COUNTRIES_PAGE = "/WEB-INF/jsp/admin/countries.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ICountryService countryService = ServiceFactory.getInstance().getCountryService();
        HttpSession session = request.getSession(false);

        try {
            if (session != null){

                List<Country> countryList = countryService.getAll();

                request.setAttribute(COUNTRY_LIST, countryList);

                request.getRequestDispatcher(COUNTRIES_PAGE).forward(request, response);
            }
            else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }


        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
