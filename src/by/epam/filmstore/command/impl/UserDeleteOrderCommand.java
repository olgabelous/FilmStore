package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
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
public class UserDeleteOrderCommand implements Command {
    private static final String ID = "id";
    private static final String COUNTRY_PAGE = "Controller?command=user-cart";
    private static final String ORDER_IN_CART_NUM = "orderInCartNum";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(UserDeleteOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter(ID));

            IOrderService service = ServiceFactory.getInstance().getOrderService();
            // TODO: 23.09.2016 проверить принадлежит ли заказ этому пользователю
            boolean isDeleted = service.delete(id);

            HttpSession session = request.getSession(false);
            if(isDeleted ) {
                int orderNum = (Integer)session.getAttribute(ORDER_IN_CART_NUM);
                session.setAttribute(ORDER_IN_CART_NUM, --orderNum);
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
