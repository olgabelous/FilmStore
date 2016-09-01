package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IDiscountService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 29.08.2016.
 */
public class AdminAddDiscountCommand implements Command {
    private static final String SUM_FROM = "sumFrom";
    private static final String VALUE = "value";
    private static final String DISCOUNTS_PAGE = "/FilmStore/UserServlet?command=admin-get-discounts";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            double sumFrom = Double.parseDouble(request.getParameter(SUM_FROM));
            double value = Double.parseDouble(request.getParameter(VALUE));
            IDiscountService service = ServiceFactory.getInstance().getDiscountService();
            service.save(sumFrom, value);
            response.sendRedirect(DISCOUNTS_PAGE);

        }catch(ServiceException | NumberFormatException e){
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
