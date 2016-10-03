package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
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
 * @author Olga Shahray
 */
public class AdminGetDiscountsCommand implements Command {
    private static final String DISCOUNT_LIST = "discountList";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final Logger LOG = LogManager.getLogger(AdminGetDiscountsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDiscountService discountService = ServiceFactory.getInstance().getDiscountService();

        try {
            List<Discount> discountList = discountService.getAll();

            request.setAttribute(DISCOUNT_LIST, discountList);

            request.getRequestDispatcher(PageName.ADMIN_DISCOUNTS).forward(request, response);

        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
