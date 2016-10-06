package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
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
 * <p>Command implements a request of user with role ADMIN to delete
 * genre. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminDeleteGenreCommand  implements Command {

    private static final String GENRES_PAGE = "Controller?command=admin-get-genres";
    private static final String ERROR_MESSAGE_TEXT = "Genre was not found";
    private static final Logger LOG = LogManager.getLogger(AdminDeleteGenreCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter(ParameterAndAttributeName.ID));
            IGenreService genreService = ServiceFactory.getInstance().getGenreService();

            boolean isDeleted = genreService.delete(id);

            if(isDeleted) {
                LOG.info("Genre id="+id+" was deleted");
                response.sendRedirect(GENRES_PAGE);
            }
            else{
                LOG.warn(ERROR_MESSAGE_TEXT);
                request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                request.getRequestDispatcher(GENRES_PAGE).forward(request, response);
            }
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(GENRES_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
