package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Discount;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IDiscountService;
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
 * Created by Olga Shahray on 16.08.2016.
 */
public class GetDiscountCommand implements Command {
    private static final String USER = "user";
    private static final String DISCOUNT_LIST = "discountList";
    private static final String DISCOUNT = "discount";
    private static final String TOTAL_AMOUNT = "totalAmount";
    private static final String DISCOUNT_PAGE = "/WEB-INF/jsp/discount.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IDiscountService discountService = ServiceFactory.getInstance().getDiscountService();
        IOrderService orderService = ServiceFactory.getInstance().getOrderService();

        HttpSession session = request.getSession(false);

        try {
            if (session != null){
                User loggedUser = (User)session.getAttribute(USER);
                List<Discount> discountList = discountService.getAll();
                double discount = discountService.getDiscount(loggedUser.getId());
                double totalAmount = orderService.getTotalAmount(loggedUser.getId());

                request.setAttribute(DISCOUNT_LIST, discountList);
                request.setAttribute(DISCOUNT, discount);
                request.setAttribute(TOTAL_AMOUNT, totalAmount);

                request.getRequestDispatcher(DISCOUNT_PAGE).forward(request, response);
            }
            else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }


        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
