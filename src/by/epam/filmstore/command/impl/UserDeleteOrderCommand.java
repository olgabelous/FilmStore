package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.ParameterAndAttributeName;
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
 * <p>Command implements a request of user with role USER to delete film from cart,
 * i.e. to delete unpaid order. Access right is checked in class UserFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.UserFilter
 * @author Olga Shahray
 */
public class UserDeleteOrderCommand implements Command {

    private static final String COUNTRY_PAGE = "Controller?command=user-cart";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserDeleteOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));

            IOrderService service = ServiceFactory.getInstance().getOrderService();
            // TODO: 23.09.2016 проверить принадлежит ли заказ этому пользователю
            boolean isDeleted = service.delete(id);

            HttpSession session = request.getSession(false);
            if(isDeleted ) {
                int orderNum = (Integer)session.getAttribute(ParameterAndAttributeName.ORDER_IN_CART_NUM);
                session.setAttribute(ParameterAndAttributeName.ORDER_IN_CART_NUM, --orderNum);
                response.sendRedirect(COUNTRY_PAGE);
            }
            else{
                LOG.warn("Order id=" + id +" was not deleted");
                response.sendError(ERROR_STATUS);
            }
        }
        catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
