package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olga Shahray on 15.08.2016.
 */
public class UserGetCommentsCommand implements Command {
    private static final String USER = "user";
    private static final String COMMENT_LIST = "commentList";
    private static final String COMMENTS_PAGE = "/WEB-INF/jsp/user/comments.jsp";
    private static final String ERROR_PAGE = "/error.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ICommentService commentService = ServiceFactory.getInstance().getCommentService();
        HttpSession session = request.getSession(false);

        try {
            if (session != null){
                User loggedUser = (User)session.getAttribute(USER);
                List<Comment> commentList = commentService.getAllOfUser(loggedUser.getId());

                request.setAttribute(COMMENT_LIST, commentList);

                request.getRequestDispatcher(COMMENTS_PAGE).forward(request, response);
            }
            else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }


        } catch (ServiceException e) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
