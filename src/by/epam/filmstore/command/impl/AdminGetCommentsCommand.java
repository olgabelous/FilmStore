package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import by.epam.filmstore.domain.dto.PagingListDTO;
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
 * @author Olga Shahray
 */
public class AdminGetCommentsCommand implements Command {
    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String COMMENT_LIST = "commentList";
    private static final String COMMENT_NUM = "commentNum";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminGetCommentsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICommentService commentService = ServiceFactory.getInstance().getCommentService();
        HttpSession session = request.getSession(false);
        try {
            int page = 1;
            int recordsPerPage = 6;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            PagingListDTO<Comment> result = commentService.getByStatus(CommentStatus.NEW, (page - 1) * recordsPerPage, recordsPerPage);
            List<Comment> commentList = result.getObjectList();
            int numOfRecords = result.getCount();

            if(session != null){
                session.setAttribute(COMMENT_NUM, commentList.size());
            }
            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(COMMENT_LIST, commentList);
            request.setAttribute(TOTAL_PAGES, totalPages);
            request.setAttribute(CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_COMMENTS).forward(request, response);

        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
