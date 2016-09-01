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
 * Created by Olga Shahray on 28.08.2016.
 */
public class AdminDeleteCountryCommand  implements Command {
    private static final String ID = "id";
    private static final String COUNTRY_PAGE = "/FilmStore/UserServlet?command=admin-get-countries";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final int STATUS_OK = 200;
    private static final String ERROR_MESSAGE = "errorMassage";
    private static final String ERROR_MESSAGE_TEXT = "Not found";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();

        try {
            int id = Integer.parseInt(request.getParameter(ID));

            boolean isDeleted = countryService.delete(id);

            if(isDeleted) {
                response.setStatus(STATUS_OK);
                response.sendRedirect(COUNTRY_PAGE);
            }
            else{
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }



        } catch (ServiceException | NumberFormatException e ) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
