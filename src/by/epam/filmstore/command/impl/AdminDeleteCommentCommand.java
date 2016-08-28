package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 28.08.2016.
 */
public class AdminDeleteCommentCommand  implements Command {
    private static final String USER_ID = "userId";
    private static final String FILM_ID = "filmId";
    private static final String COMMENTS_PAGE = "/WEB-INF/jsp/admin/comments.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final int STATUS_OK = 200;
    private static final String ERROR_MESSAGE = "errorMassage";
    private static final String ERROR_MESSAGE_TEXT = "Not found";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICommentService commentService = ServiceFactory.getInstance().getCommentService();

        try {
            int userId = Integer.parseInt(request.getParameter(USER_ID));
            int filmId = Integer.parseInt(request.getParameter(FILM_ID));

            boolean isDeleted = commentService.delete(userId, filmId);

            if(isDeleted) {
                response.setStatus(STATUS_OK);
            }
            else{
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            }

            request.getRequestDispatcher(COMMENTS_PAGE).forward(request, response);

        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
