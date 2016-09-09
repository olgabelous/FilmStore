package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 29.08.2016.
 */
public class AdminUpdateCommentCommand implements Command {
    private static final String FILM_ID = "filmId";
    private static final String USER_ID = "userId";
    private static final String STATUS = "status";
    private static final String COMMENTS_PAGE = "Controller?command=admin-get-comments";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ICommentService commentService = ServiceFactory.getInstance().getCommentService();

        try {
            int filmId = Integer.parseInt(request.getParameter(FILM_ID));
            int userId = Integer.parseInt(request.getParameter(USER_ID));
            String status = request.getParameter(STATUS);

            commentService.update(filmId, userId, CommentStatus.valueOf(status.toUpperCase()));

            response.sendRedirect(COMMENTS_PAGE);

        } catch (ServiceException | IllegalArgumentException e) {
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
