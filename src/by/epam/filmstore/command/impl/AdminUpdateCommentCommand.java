package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Command implements a request of user with role ADMIN to update
 * comment with required status. Comment status may be "applied" or "rejected".
 * Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminUpdateCommentCommand implements Command {

    private static final String COMMENTS_PAGE = "Controller?command=admin-get-comments";
    private static final Logger LOG = LogManager.getLogger(AdminUpdateCommentCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ICommentService commentService = ServiceFactory.getInstance().getCommentService();

        try {
            int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));
            String status = request.getParameter(ParameterAndAttributeName.STATUS);

            commentService.update(id, CommentStatus.valueOf(status.toUpperCase()));

            response.sendRedirect(COMMENTS_PAGE);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
