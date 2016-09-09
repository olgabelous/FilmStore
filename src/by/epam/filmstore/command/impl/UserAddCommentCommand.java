package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.ICommentService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Olga Shahray on 04.09.2016.
 */
public class UserAddCommentCommand implements Command {
    private static final String USER = "user";
    private static final String FILM_ID = "filmId";
    private static final String COMMENT = "comment";
    private static final String MARK = "mark";
    private static final String FILM_PAGE = "Controller?command=get-film-by-id&id=";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(USER);

        ICommentService service = ServiceFactory.getInstance().getCommentService();

        try{
            int filmId = Integer.parseInt(request.getParameter(FILM_ID));
            String comment = request.getParameter(COMMENT);
            int mark = Integer.parseInt(request.getParameter(MARK));

            service.save(loggedUser, filmId, mark, comment);

            response.sendRedirect(FILM_PAGE+filmId);

        }catch(ServiceException e){
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
