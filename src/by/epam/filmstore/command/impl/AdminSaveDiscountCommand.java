package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
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
 * <p>Command implements a request of user with role ADMIN to save new discount or
 * update it if it exists. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminSaveDiscountCommand implements Command {

    private static final String DISCOUNTS_PAGE = "Controller?command=admin-get-discounts";
    private static final Logger LOG = LogManager.getLogger(AdminSaveDiscountCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String id = request.getParameter(ParameterAndAttributeName.ID);
            double sumFrom = Double.parseDouble(request.getParameter(ParameterAndAttributeName.SUM_FROM));
            double value = Double.parseDouble(request.getParameter(ParameterAndAttributeName.VALUE));

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
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, "Invalid data. Parameters must contain only numbers");
            request.getRequestDispatcher(DISCOUNTS_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(DISCOUNTS_PAGE).forward(request, response);
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
