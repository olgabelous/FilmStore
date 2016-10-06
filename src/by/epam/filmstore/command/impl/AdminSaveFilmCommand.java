package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.service.IFileStoreService;
import by.epam.filmstore.service.IFilmService;
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
 * <p>Command implements a request of user with role ADMIN to save new film or
 * update it if it exists. Access right is checked in class AdminFilter.</p>
 *
 * @see by.epam.filmstore.controller.filter.AdminFilter
 * @author Olga Shahray
 */
public class AdminSaveFilmCommand implements Command {

    private static final String FILMS_PAGE = "Controller?command=admin-get-films&page=";
    private static final Logger LOG = LogManager.getLogger(AdminSaveFilmCommand.class);

    /**
     * <p>Method gets parameters from request and send its to service layer.
     * Field of film "poster" is a name of uploaded picture. If user updates poster of film, old poster
     * will be deleted.</p>
     *
     * @param request is wrapped request by class FileUploadWrapper
     *                @see by.epam.filmstore.controller.filter.FileUploadFilter
     *                @see by.epam.filmstore.controller.FileUploadWrapper
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;

        try{
            String pageNum = request.getParameter(ParameterAndAttributeName.PAGE);
            if ( pageNum!= null && !pageNum.isEmpty()) {
                page = Integer.parseInt(pageNum);
            }
            String id = request.getParameter(ParameterAndAttributeName.ID);

            String title = request.getParameter(ParameterAndAttributeName.TITLE);
            int year = Integer.parseInt(request.getParameter(ParameterAndAttributeName.YEAR));
            int countryId = Integer.parseInt(request.getParameter(ParameterAndAttributeName.COUNTRY_ID));
            int duration = Integer.parseInt(request.getParameter(ParameterAndAttributeName.DURATION));
            int ageRestriction = Integer.parseInt(request.getParameter(ParameterAndAttributeName.AGE_RESTRICTION));
            String[] genres = request.getParameterValues(ParameterAndAttributeName.GENRE_LIST);
            String[] filmMakers = request.getParameterValues(ParameterAndAttributeName.FILM_MAKER_LIST);
            double price = Double.parseDouble(request.getParameter(ParameterAndAttributeName.PRICE));
            String video = request.getParameter(ParameterAndAttributeName.VIDEO);
            String description = request.getParameter(ParameterAndAttributeName.DESCRIPTION);
            String dateAdd = request.getParameter(ParameterAndAttributeName.DATE_ADD);
            String poster = request.getParameter(ParameterAndAttributeName.POSTER);
            String oldPoster = request.getParameter(ParameterAndAttributeName.OLD_POSTER);

            if(poster == null || poster.isEmpty()){
                poster = oldPoster;
            }

            IFilmService service = ServiceFactory.getInstance().getFilmService();
            IFileStoreService fileStoreService = ServiceFactory.getInstance().getFileStoreService();
            if(id == null || id.isEmpty()) {
                service.save(genres, filmMakers, title, year, countryId, duration, ageRestriction, price, poster, video, description);
            }
            else{
                int filmId = Integer.parseInt(id);
                service.update(filmId, genres, filmMakers, title, year, countryId, duration, ageRestriction, price, poster, video, description, dateAdd);
                if(oldPoster != null && !oldPoster.isEmpty() && !oldPoster.equals(poster)){
                    String posterStorePath = request.getServletContext().getInitParameter(ParameterAndAttributeName.POSTER_STORE_PATH);
                    fileStoreService.delete(request.getServletContext().getRealPath(posterStorePath + oldPoster));
                }
            }
            response.sendRedirect(FILMS_PAGE + page);

        }
        catch (NumberFormatException e){
            LOG.warn("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, "Fields must not be empty");
            request.getRequestDispatcher(PageName.ADMIN_ADD_FILM_PAGE).forward(request, response);
        }
        catch (ServiceIncorrectParamLengthException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.warn("Data is not valid", e);
            request.setAttribute(ParameterAndAttributeName.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(PageName.ADMIN_ADD_FILM_PAGE).forward(request, response);
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(ParameterAndAttributeName.EXCEPTION, e);
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }

    }
}
