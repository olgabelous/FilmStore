package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
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

/**
 * <p>Command implements a request of user with role USER to watch required film.
 * It checks if film was paid and if logged user is owner of this film.
 * Access right is checked in class UserFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.UserFilter
 * @author Olga Shahray
 */
public class UserWatchFilmCommand implements Command{

    private static final int ERROR_404 = 404;
    private static final int ERROR_403 = 403;

    private static final Logger LOG = LogManager.getLogger(UserWatchFilmCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
        try{
            int orderId = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ORDER));
            int filmId = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));
            IOrderService orderService = ServiceFactory.getInstance().getOrderService();
            Order order = orderService.get(orderId);

            if(order.getStatus() == OrderStatus.PAID && order.getUser().getId() == loggedUser.getId() && order.getFilm().getId() == filmId){

                request.setAttribute(ParameterAndAttributeName.FILM, order.getFilm());
                request.getRequestDispatcher(PageName.USER_WATCH_FILM).forward(request, response);
            }
            else{
                LOG.warn("Access denied for user to watch film id=" + filmId);
                response.setStatus(ERROR_403);
            }

        }
        catch (ServiceValidationException | NumberFormatException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_404);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_404);
        }
    }
}
