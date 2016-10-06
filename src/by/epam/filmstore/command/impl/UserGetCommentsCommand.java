package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.ICommentService;
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
import java.util.List;

/**
 * <p>Command implements a request of user with role USER to show
 * all his comments to films.
 * Access right is checked in class UserFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.UserFilter
 * @author Olga Shahray
 */
public class UserGetCommentsCommand implements Command {

    private static final int ERROR_STATUS = 404;
    private static final Logger LOG = LogManager.getLogger(UserGetCommentsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
        try {
            ICommentService commentService = ServiceFactory.getInstance().getCommentService();

            List<Comment> commentList = commentService.getAllOfUser(loggedUser.getId());

            request.setAttribute(ParameterAndAttributeName.COMMENT_LIST, commentList);

            request.getRequestDispatcher(PageName.USER_COMMENTS_PAGE).forward(request, response);

        } catch (ServiceValidationException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);
        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
