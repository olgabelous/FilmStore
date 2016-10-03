package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IGenreService;
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
public class AdminDeleteGenreCommand  implements Command {
    private static final String ID = "id";
    private static final String GENRES_PAGE = "Controller?command=admin-get-genres";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_TEXT = "Country was not found";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminDeleteGenreCommand.class);

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter(ID));
            IGenreService genreService = ServiceFactory.getInstance().getGenreService();

            boolean isDeleted = genreService.delete(id);

            if(isDeleted) {
                LOG.info("Genre id="+id+" was deleted");
                response.sendRedirect(GENRES_PAGE);
            }
            else{
                LOG.warn(ERROR_MESSAGE_TEXT);
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(GENRES_PAGE).forward(request, response);
            }
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(GENRES_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
