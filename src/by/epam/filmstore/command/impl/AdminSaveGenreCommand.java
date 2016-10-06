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
 * <p>Command implements a request of user with role ADMIN to save new genre or
 * update it if it exists. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminSaveGenreCommand implements Command {

    private static final String GENRE_PAGE = "Controller?command=admin-get-genres";
    private static final Logger LOG = LogManager.getLogger(AdminSaveGenreCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String genreName = request.getParameter(ParameterAndAttributeName.GENRE_NAME);
        String id = request.getParameter(ParameterAndAttributeName.ID);

        IGenreService service = ServiceFactory.getInstance().getGenreService();

        try{
            if(id == null || id.isEmpty()) {
                service.save(genreName);
            }
            else{
                service.update(Integer.parseInt(id), genreName);
            }
            response.sendRedirect(GENRE_PAGE);

        }catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(GENRE_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
