package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.OrderStatus;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.IOrderService;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceAuthException;
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
 * <p>Command authorizes user in system. It gets login and password from request and sends it to service layer.
 * Authorized user is sent to home page.</p>
 *
 * @author Olga Shahray
 */
public class LoginationCommand implements Command {

    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final int ERROR_STATUS = 404;
    private static final String AUTH_ERROR = "authError";

    private static final Logger LOG = LogManager.getLogger(LoginationCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(ParameterAndAttributeName.LOGIN);
        String password = request.getParameter(ParameterAndAttributeName.PASSWORD);

        IUserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);

        try {
            User user = userService.authorize(login, password);

            if(user.getRole() == Role.ADMIN){
                ICommentService commentService =  ServiceFactory.getInstance().getCommentService();
                int newCommentNum = commentService.getCount(CommentStatus.NEW);
                session.setAttribute(ParameterAndAttributeName.COMMENT_NUM, newCommentNum);
            }
            else{
                IOrderService orderService = ServiceFactory.getInstance().getOrderService();
                int orderInCartNum = orderService.getUserOrdersByStatus(user.getId(), OrderStatus.UNPAID).size();
                session.setAttribute(ParameterAndAttributeName.ORDER_IN_CART_NUM, orderInCartNum);
            }
            session.setAttribute(ParameterAndAttributeName.USER, user);
            response.sendRedirect(INDEX_PAGE);

        } catch (ServiceAuthException e) {
            LOG.warn("Authorization failed", e);
            request.setAttribute(AUTH_ERROR, e.getMessage());
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            response.sendError(ERROR_STATUS);

        }catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }

    }
}
