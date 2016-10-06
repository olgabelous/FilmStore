package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
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
 * <p>Command implements a request of user with role ADMIN to show
 * comments with status "new". Is shown 6 records per page.
 * Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminGetCommentsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AdminGetCommentsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICommentService commentService = ServiceFactory.getInstance().getCommentService();
        HttpSession session = request.getSession(false);
        try {
            int page = 1;
            int recordsPerPage = 6;
            String pageNum = request.getParameter(ParameterAndAttributeName.PAGE);
            if (pageNum != null) {
                page = Integer.parseInt(pageNum);
            }
            PagingListDTO<Comment> result = commentService.getByStatus(CommentStatus.NEW, (page - 1) * recordsPerPage, recordsPerPage);
            List<Comment> commentList = result.getObjectList();
            int numOfRecords = result.getCount();

            if(session != null){
                session.setAttribute(ParameterAndAttributeName.COMMENT_NUM, commentList.size());
            }
            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(ParameterAndAttributeName.COMMENT_LIST, commentList);
            request.setAttribute(ParameterAndAttributeName.TOTAL_PAGES, totalPages);
            request.setAttribute(ParameterAndAttributeName.CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.ADMIN_COMMENTS).forward(request, response);

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
