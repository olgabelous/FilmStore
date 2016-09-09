package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.PaymentData;
import by.epam.filmstore.domain.PaymentStatus;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.IPaymentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 04.09.2016.
 */
public class UserPayOrderCommand implements Command {

    private static final String ORDER_ID = "id";
    private static final String ORDER_LIST = "order-list";
    private static final String TOTAL_SUM = "total-sum";
    private static final String PAYMENT_STATUS = "paymentStatus";
    private static final String CART_PAGE = "Controller?command=user-cart";
    private static final String ORDERS_PAGE = "Controller?command=user-get-orders";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //List<Order> orderList = request.getParameter(ORDER_LIST);
            int orderId = Integer.parseInt(request.getParameter(TOTAL_SUM));
            double sum = Double.parseDouble(request.getParameter(TOTAL_SUM));

            IOrderService orderService = ServiceFactory.getInstance().getOrderService();
            IPaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
            //orderService.updateStatus(orderId, OrderStatus.AWAITING);
            PaymentStatus paymentStatus = paymentService.processPayment(new PaymentData(sum));
            if(paymentStatus.isSuccessful()){
                orderService.updateStatus(orderId, OrderStatus.PAID);
                request.setAttribute(PAYMENT_STATUS, paymentStatus);
                response.sendRedirect(ORDERS_PAGE);
            }
            else{
                orderService.updateStatus(orderId, OrderStatus.UNPAID);
                request.setAttribute(PAYMENT_STATUS, paymentStatus);
                response.sendRedirect(CART_PAGE);
            }



        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
