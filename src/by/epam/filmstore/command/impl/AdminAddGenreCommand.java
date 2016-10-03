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
public class AdminAddGenreCommand implements Command {

    private static final String ID = "id";
    private static final String GENRE_NAME = "genreName";
    private static final String GENRE_PAGE = "Controller?command=admin-get-genres";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final Logger LOG = LogManager.getLogger(AdminAddGenreCommand.class);
    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String genreName = request.getParameter(GENRE_NAME);
        String id = request.getParameter(ID);

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
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(GENRE_PAGE).forward(request, response);
        }
        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
