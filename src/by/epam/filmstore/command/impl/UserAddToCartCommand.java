package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Order;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 02.09.2016.
 */
public class UserAddToCartCommand implements Command {
    private static final String FILM_ID = "filmId";
    private static final String USER = "user";
    private static final String PRICE = "price";
    private static final String CART_PAGE = "/WEB-INF/jsp/user/cart.jsp";
    private static final String ORDER_LIST = "orderList";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final String TOTAL_SUM = "totalSum";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int filmId = Integer.parseInt(request.getParameter(FILM_ID));
            double price = Double.parseDouble(request.getParameter(PRICE));

            HttpSession session = request.getSession(false);
            User loggedUser = (User) session.getAttribute(USER);

            IOrderService service = ServiceFactory.getInstance().getOrderService();
            service.save(filmId, price, loggedUser);
            List<Order> orderList = service.getOrdersInCart(loggedUser.getId());
            double sum = 0.0;
            for(Order order : orderList){
                sum += order.getSum();
            }
            request.setAttribute(ORDER_LIST, orderList);
            request.setAttribute(TOTAL_SUM, sum);
            request.getRequestDispatcher(CART_PAGE).forward(request, response);

        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }

    }
}
