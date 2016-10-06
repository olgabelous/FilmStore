package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.service.IDiscountService;
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
 * <p>Command implements a request of user with role ADMIN to show
 * all discounts. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminGetDiscountsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AdminGetDiscountsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDiscountService discountService = ServiceFactory.getInstance().getDiscountService();

        try {
            List<Discount> discountList = discountService.getAll();

            request.setAttribute(ParameterAndAttributeName.DISCOUNT_LIST, discountList);

            request.getRequestDispatcher(PageName.ADMIN_DISCOUNTS).forward(request, response);

        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
