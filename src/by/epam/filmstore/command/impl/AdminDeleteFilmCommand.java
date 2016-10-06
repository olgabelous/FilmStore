package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.service.IFileStoreService;
import by.epam.filmstore.service.IFilmService;
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
 * <p>Command implements a request of user with role ADMIN to delete
 * film. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminDeleteFilmCommand  implements Command {

    private static final String FILMS_PAGE = "Controller?command=admin-get-films&page=";
    private static final String ERROR_MESSAGE_TEXT = "Film was not found";
    private static final Logger LOG = LogManager.getLogger(AdminDeleteFilmCommand.class);

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IFilmService filmService = ServiceFactory.getInstance().getFilmService();
        IFileStoreService fileStoreService = ServiceFactory.getInstance().getFileStoreService();
        int page = 1;

        try {
            String pageNum = request.getParameter(ParameterAndAttributeName.PAGE);
            if ( pageNum!= null && !pageNum.isEmpty()) {
                page = Integer.parseInt(pageNum);
            }
            String poster = request.getParameter(ParameterAndAttributeName.POSTER);

            int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));

            boolean isDeleted = filmService.delete(id);

            if(isDeleted) {
                if(poster != null && !poster.isEmpty()){
                    String posterStorePath = request.getServletContext().getInitParameter(ParameterAndAttributeName.POSTER_STORE_PATH);
                    fileStoreService.delete(request.getServletContext().getRealPath(posterStorePath + poster));
                }
                LOG.info("Film id="+id+" was deleted");
                response.sendRedirect(FILMS_PAGE + page);
            }
            else{
                LOG.warn(ERROR_MESSAGE_TEXT);
                request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(FILMS_PAGE + page).forward(request, response);
            }

        }catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(FILMS_PAGE + page).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
