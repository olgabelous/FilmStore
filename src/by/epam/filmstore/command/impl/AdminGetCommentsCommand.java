package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class AdminGetCommentsCommand implements Command {
    private static final String COMMENT_LIST = "commentList";
    private static final String COMMENTS_PAGE = "/WEB-INF/jsp/admin/comments.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final int LIMIT = 1000;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICommentService commentService = ServiceFactory.getInstance().getCommentService();

        try {
            List<Comment> commentList = commentService.getByStatus(CommentStatus.NEW);

            request.setAttribute(COMMENT_LIST, commentList);

            request.getRequestDispatcher(COMMENTS_PAGE).forward(request, response);

        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
