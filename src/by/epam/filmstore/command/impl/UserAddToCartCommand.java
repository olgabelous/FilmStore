package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
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

/**
 * @author Olga Shahray
 */
public class UserAddToCartCommand implements Command {
    private static final String FILM_ID = "filmId";
    private static final String USER = "user";
    private static final String PRICE = "price";
    private static final String CART_PAGE = "Controller?command=user-cart";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserAddToCartCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(USER);
        try {
            int filmId = Integer.parseInt(request.getParameter(FILM_ID));
            double price = Double.parseDouble(request.getParameter(PRICE));

            IOrderService service = ServiceFactory.getInstance().getOrderService();
            service.save(filmId, price, loggedUser);
            response.sendRedirect(CART_PAGE);

        } catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
