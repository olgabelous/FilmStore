package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.ICountryService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 29.08.2016.
 */
public class AdminAddCountryCommand implements Command {

    private static final String COUNTRY_NAME = "countryName";
    private static final String COUNTRY_PAGE = "/FilmStore/UserServlet?command=admin-get-countries";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String countryName = request.getParameter(COUNTRY_NAME);

        ICountryService service = ServiceFactory.getInstance().getCountryService();

        try{
            service.save(countryName);
            response.sendRedirect(COUNTRY_PAGE);

        }catch(ServiceException e){
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
