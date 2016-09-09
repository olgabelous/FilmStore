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
 * Created by Olga Shahray on 15.08.2016.
 */
public class UserGetOrdersCommand implements Command {

    private static final String USER = "user";
    private static final String ORDER_LIST = "orderList";
    private static final String ORDERS_PAGE = "/WEB-INF/jsp/user/orders.jsp";
    private static final String ERROR_PAGE = "/error.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IOrderService userService = ServiceFactory.getInstance().getOrderService();
        HttpSession session = request.getSession(false);

        try {
            if (session != null){
                User loggedUser = (User)session.getAttribute(USER);
                List<Order> orderList = userService.getAllOfUser(loggedUser.getId());

                request.setAttribute(ORDER_LIST, orderList);

                request.getRequestDispatcher(ORDERS_PAGE).forward(request, response);
            }
            else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }


        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
