package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IDiscountService;
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
public class AdminAddDiscountCommand implements Command {
    private static final String ID = "id";
    private static final String SUM_FROM = "sumFrom";
    private static final String VALUE = "value";
    private static final String DISCOUNTS_PAGE = "Controller?command=admin-get-discounts";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final Logger LOG = LogManager.getLogger(AdminAddDiscountCommand.class);

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String id = request.getParameter(ID);
            double sumFrom = Double.parseDouble(request.getParameter(SUM_FROM));
            double value = Double.parseDouble(request.getParameter(VALUE));

            IDiscountService service = ServiceFactory.getInstance().getDiscountService();
            if(id == null || id.isEmpty()) {
                service.save(sumFrom, value);
            }
            else{
                service.update(Integer.parseInt(id), sumFrom, value);
            }
            response.sendRedirect(DISCOUNTS_PAGE);

        }
        catch (NumberFormatException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, "Invalid data. Parameters must contain only numbers");
            request.getRequestDispatcher(DISCOUNTS_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(DISCOUNTS_PAGE).forward(request, response);
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
