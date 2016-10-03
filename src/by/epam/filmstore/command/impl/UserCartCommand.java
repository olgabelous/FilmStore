package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.User;
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
 * @author Olga Shahray
 */
public class UserCartCommand implements Command {

    private static final String USER = "user";
    private static final String TOTAL_SUM = "totalSum";
    private static final String ORDER_LIST = "orderList";
    private static final String ORDER_IN_CART_NUM = "orderInCartNum";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserCartCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(USER);
        try {
            IOrderService service = ServiceFactory.getInstance().getOrderService();
            List<Order> orderList = service.getUserOrdersByStatus(loggedUser.getId(), OrderStatus.UNPAID);
            double sum = 0.0;
            for (Order order : orderList) {
                sum += order.getSum();
            }
            sum = Math.round(sum * 100.0) / 100.0;
            session.setAttribute(ORDER_IN_CART_NUM, orderList.size());
            request.setAttribute(ORDER_LIST, orderList);
            request.setAttribute(TOTAL_SUM, sum);
            request.getRequestDispatcher(PageName.USER_CART_PAGE).forward(request, response);

        } catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
