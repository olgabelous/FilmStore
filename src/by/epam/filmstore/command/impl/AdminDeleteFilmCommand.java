package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
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
 * @author Olga Shahray
 */
public class AdminDeleteFilmCommand  implements Command {
    private static final String ID = "id";
    private static final String PAGE = "page";
    private static final String POSTER = "poster";
    private static final String POSTER_STORE_PATH = "posterStorePath";
    private static final String FILMS_PAGE = "Controller?command=admin-get-films&page=";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_TEXT = "Film was not found";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

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
            String pageNum = request.getParameter(PAGE);
            if ( pageNum!= null && !pageNum.isEmpty()) {
                page = Integer.parseInt(pageNum);
            }
            String poster = request.getParameter(POSTER);

            int id = Integer.parseInt(request.getParameter(ID));

            boolean isDeleted = filmService.delete(id);

            if(isDeleted) {
                if(poster != null && !poster.isEmpty()){
                    String posterStorePath = request.getServletContext().getInitParameter(POSTER_STORE_PATH);
                    fileStoreService.delete(request.getServletContext().getRealPath(posterStorePath + poster));
                }
                LOG.info("Film id="+id+" was deleted");
                response.sendRedirect(FILMS_PAGE + page);
            }
            else{
                LOG.warn(ERROR_MESSAGE_TEXT);
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(FILMS_PAGE + page).forward(request, response);
            }

        }catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(FILMS_PAGE + page).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
