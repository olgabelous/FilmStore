package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
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
 * @author Olga Shahray
 */
public class AdminSaveFilmCommand implements Command {

    private static final String ID = "id";
    private static final String PAGE = "page";
    private static final String TITLE = "title";
    private static final String YEAR = "year";
    private static final String COUNTRY_ID = "countryId";
    private static final String DURATION = "duration";
    private static final String AGE_RESTRICTION = "ageRestriction";
    private static final String PRICE = "price";
    private static final String POSTER = "poster";
    private static final String OLD_POSTER = "oldPoster";
    private static final String VIDEO = "video";
    private static final String DATE_ADD = "dateAdd";
    private static final String GENRES = "genres";
    private static final String FILM_MAKERS = "filmMakers";
    private static final String DESCRIPTION = "description";
    private static final String POSTER_STORE_PATH = "posterStorePath";
    private static final String FILMS_PAGE = "Controller?command=admin-get-films&page=";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(AdminSaveFilmCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;

        try{
            String pageNum = request.getParameter(PAGE);
            if ( pageNum!= null && !pageNum.isEmpty()) {
                page = Integer.parseInt(pageNum);
            }
            String id = request.getParameter(ID);

            String title = request.getParameter(TITLE);
            int year = Integer.parseInt(request.getParameter(YEAR));
            int countryId = Integer.parseInt(request.getParameter(COUNTRY_ID));
            int duration = Integer.parseInt(request.getParameter(DURATION));
            int ageRestriction = Integer.parseInt(request.getParameter(AGE_RESTRICTION));
            String[] genres = request.getParameterValues(GENRES);
            String[] filmMakers = request.getParameterValues(FILM_MAKERS);
            double price = Double.parseDouble(request.getParameter(PRICE));
            String video = request.getParameter(VIDEO);
            String description = request.getParameter(DESCRIPTION);
            String dateAdd = request.getParameter(DATE_ADD);
            String poster = request.getParameter(POSTER);
            String oldPoster = request.getParameter(OLD_POSTER);

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
                    String posterStorePath = request.getServletContext().getInitParameter(POSTER_STORE_PATH);
                    fileStoreService.delete(request.getServletContext().getRealPath(posterStorePath + oldPoster));
                }
            }
            response.sendRedirect(FILMS_PAGE + page);

        }
        catch (NumberFormatException e){
            LOG.warn("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, "Fields must not be empty");
            request.getRequestDispatcher(PageName.ADMIN_ADD_FILM_PAGE).forward(request, response);
        }
        catch (ServiceIncorrectParamLengthException e){
            LOG.error("Data is not valid", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
        catch (ServiceValidationException e){
            LOG.warn("Data is not valid", e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(PageName.ADMIN_ADD_FILM_PAGE).forward(request, response);
        }
        catch(ServiceException e){
            LOG.error("Exception is caught", e);
            request.setAttribute(EXCEPTION, e);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }

    }
}
