package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
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
 * <p>Command implements a request of user with role ADMIN to save new film maker or
 * update him if he exists. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminSaveFilmMakerCommand implements Command {

    private static final String FILM_MAKER_PAGE = "Controller?command=admin-get-film-makers";
    private static final Logger LOG = LogManager.getLogger(AdminSaveFilmMakerCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter(ParameterAndAttributeName.ID);
        String name = request.getParameter(ParameterAndAttributeName.NAME);
        String profession = request.getParameter(ParameterAndAttributeName.PROFESSION);

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
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(FILM_MAKER_PAGE).forward(request, response);
        }

        catch(ServiceException | NumberFormatException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
    }
}
