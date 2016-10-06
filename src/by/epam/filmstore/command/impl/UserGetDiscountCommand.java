package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IDiscountService;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * <p>Command implements a request of user with role USER to show
 * his discount and total amount of paid orders.
 * Access right is checked in class UserFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.UserFilter
 * @author Olga Shahray
 */
public class UserGetDiscountCommand implements Command {

    private static final int ERROR_STATUS = 404;
    private static final Logger LOG = LogManager.getLogger(UserGetDiscountCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
        try {
            IDiscountService discountService = ServiceFactory.getInstance().getDiscountService();
            IOrderService orderService = ServiceFactory.getInstance().getOrderService();

            List<Discount> discountList = discountService.getAll();
            double discount = discountService.getDiscount(loggedUser.getId());
            double totalAmount = orderService.getTotalAmount(loggedUser.getId());

            request.setAttribute(ParameterAndAttributeName.DISCOUNT_LIST, discountList);
            request.setAttribute(ParameterAndAttributeName.DISCOUNT, discount);
            request.setAttribute(ParameterAndAttributeName.TOTAL_AMOUNT, totalAmount);

            request.getRequestDispatcher(PageName.USER_DISCOUNT_PAGE).forward(request, response);

        } catch (ServiceValidationException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }

    }
}
