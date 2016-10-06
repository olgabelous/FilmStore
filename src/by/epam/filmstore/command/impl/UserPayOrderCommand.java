package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.PaymentData;
import by.epam.filmstore.domain.PaymentStatus;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.IPaymentService;
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

/**
 * <p>Command implements a request of user with role USER to pay order.
 * It changes all order status to "awaiting", calls method processPayment and according to
 * result of this method changes orders status to "paid" or "unpaid".
 * Access right is checked in class UserFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.UserFilter
 * @author Olga Shahray
 */
public class UserPayOrderCommand implements Command {

    private static final String CART_PAGE = "Controller?command=user-cart";
    private static final String ORDERS_PAGE = "Controller?command=user-get-orders";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserGetOrdersCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);

        String[] order = request.getParameterValues(ParameterAndAttributeName.ID);
        double sum = Double.parseDouble(request.getParameter(ParameterAndAttributeName.TOTAL_SUM));

        try {
            int[] orderIdArray = new int[order.length];
            IOrderService orderService = ServiceFactory.getInstance().getOrderService();
            for (int i = 0; i < order.length; i++) {
                orderIdArray[i] = Integer.parseInt(order[i]);
            }
            orderService.updateStatus(orderIdArray, OrderStatus.AWAITING);

            IPaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
            PaymentStatus paymentStatus = paymentService.processPayment(new PaymentData(sum));
            if (paymentStatus.isSuccessful()) {
                orderService.updateStatus(orderIdArray, OrderStatus.PAID);

                int orderNum = orderService.getUserOrdersByStatus(loggedUser.getId(), OrderStatus.UNPAID).size();
                session.setAttribute(ParameterAndAttributeName.ORDER_IN_CART_NUM, orderNum);
                request.setAttribute(ParameterAndAttributeName.PAYMENT_STATUS, paymentStatus);
                response.sendRedirect(ORDERS_PAGE);
            }
            else {
                orderService.updateStatus(orderIdArray, OrderStatus.UNPAID);

                int orderNum = orderService.getUserOrdersByStatus(loggedUser.getId(), OrderStatus.UNPAID).size();
                session.setAttribute(ParameterAndAttributeName.ORDER_IN_CART_NUM, orderNum);
                request.setAttribute(ParameterAndAttributeName.PAYMENT_STATUS, paymentStatus);
                response.sendRedirect(CART_PAGE);
            }

        } catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
