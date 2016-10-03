package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.service.IFilmMakerService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceIncorrectParamLengthException;
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
public class AdminAddFilmMakerCommand implements Command {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PROFESSION = "profession";
    private static final String FILM_MAKER_PAGE = "Controller?command=admin-get-film-makers";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final Logger LOG = LogManager.getLogger(AdminAddFilmMakerCommand.class);

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter(ID);
        String name = request.getParameter(NAME);
        String profession = request.getParameter(PROFESSION);

        IFilmMakerService service = ServiceFactory.getInstance().getFilmMakerService();

        try{
            if(id == null || id.isEmpty()) {
                service.save(name, profession);
            }
            else{
                service.update(Integer.parseInt(id), name, profession);
            }
            response.sendRedirect(FILM_MAKER_PAGE);

        }catch (ServiceIncorrectParamLengthException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(FILM_MAKER_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
