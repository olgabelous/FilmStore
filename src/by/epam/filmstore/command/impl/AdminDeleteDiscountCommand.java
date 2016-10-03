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
public class AdminDeleteDiscountCommand  implements Command {
    private static final String ID = "id";
    private static final String DISCOUNTS_PAGE = "Controller?command=admin-get-discounts";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_TEXT = "Discount was not found";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminDeleteDiscountCommand.class);
    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDiscountService discountService = ServiceFactory.getInstance().getDiscountService();

        try {
            int id = Integer.parseInt(request.getParameter(ID));

            boolean isDeleted = discountService.delete(id);

            if(isDeleted) {
                response.sendRedirect(DISCOUNTS_PAGE);
                LOG.info("Discount id="+id+" was deleted");
            }
            else{
                LOG.warn(ERROR_MESSAGE_TEXT);
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(DISCOUNTS_PAGE).forward(request, response);
            }

        }catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(DISCOUNTS_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
