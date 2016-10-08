package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>Command implements a request to show films according to some filter parameters.
 * It shown 6 records per page.
 * </p>
 *
 * @author Olga Shahray
 */
public class GetFilteredFilmsCommand implements Command {

    private static final String WARNING = "warning";
    private static final String WARNING_TEXT = "Sorry, we don't have such films. Please choose another params.";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(GetFilteredFilmsCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, List<String>> filterParams = new HashMap<>();
        Map<String, String> genresFilterToSend = new HashMap<>();
        Map<String, String> countriesFilterToSend = new HashMap<>();
        List<String> yearFilterToSend = new ArrayList<>();

        String[] genres = request.getParameterValues(ParameterAndAttributeName.GENRE);
        String[] countries = request.getParameterValues(ParameterAndAttributeName.COUNTRY);
        String[] year = request.getParameterValues(ParameterAndAttributeName.YEAR);
        String rating = request.getParameter(ParameterAndAttributeName.RATING);
        String order = request.getParameter(ParameterAndAttributeName.ORDER);
        try {
            int page = 1;
            int recordsPerPage = 6;
            String pageNum = request.getParameter(ParameterAndAttributeName.PAGE);
            if (pageNum != null) {
                page = Integer.parseInt(pageNum);
            }
            ServletContext servletContext = request.getServletContext();
            List<Genre> genreList  = (List<Genre>) servletContext.getAttribute(ParameterAndAttributeName.GENRE_LIST);
            List<Country> countryList = (List<Country>) servletContext.getAttribute(ParameterAndAttributeName.COUNTRY_LIST);

            if (genres != null && genres.length != 0) {
                filterParams.put(ParameterAndAttributeName.GENRE, Arrays.asList(genres));
                for (Genre g : genreList) {
                    for (String s : genres) {
                        if (s.equals(String.valueOf(g.getId()))) {
                            genresFilterToSend.put(s, g.getGenreName());
                        }
                    }
                }
            }
            request.setAttribute(ParameterAndAttributeName.GENRE_FILTER, genresFilterToSend);
            if (countries != null && countries.length != 0) {
                filterParams.put(ParameterAndAttributeName.COUNTRY, Arrays.asList(countries));
                for (Country c : countryList) {
                    for (String s : countries) {
                        if (s.equals(String.valueOf(c.getId()))) {
                            countriesFilterToSend.put(s, c.getCountryName());
                        }
                    }
                }
            }
            request.setAttribute(ParameterAndAttributeName.COUNTRY_FILTER, countriesFilterToSend);

            if (year != null && year.length != 0) {
                if(year[0].equals("2016")){
                    yearFilterToSend = Arrays.asList(year);
                }
                else{
                    int yearFrom = Integer.parseInt(year[0]);
                    int yearTo = yearFrom + 10;
                    yearFilterToSend = Arrays.asList(new String[]{year[0], String.valueOf(yearTo)});
                }

                filterParams.put(ParameterAndAttributeName.YEAR, yearFilterToSend);
            }
            request.setAttribute(ParameterAndAttributeName.YEAR_FILTER, year);

            if (rating != null && !rating.isEmpty()) {
                filterParams.put(ParameterAndAttributeName.RATING, Collections.singletonList(rating));
            }

            IFilmService filmService = ServiceFactory.getInstance().getFilmService();

            PagingListDTO<Film> result = filmService.getFilteredFilms(filterParams, order, (page - 1) * recordsPerPage, recordsPerPage);

            int numOfRecords = result.getCount();
            List<Film> filteredFilmList = result.getObjectList();

            if (numOfRecords == 0) {
                request.setAttribute(WARNING, WARNING_TEXT);
            }

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
            String requestQueryString = request.getQueryString().split("&page")[0];

            request.setAttribute(ParameterAndAttributeName.QUERY, requestQueryString);
            request.setAttribute(ParameterAndAttributeName.FILM_LIST, filteredFilmList);
            request.setAttribute(ParameterAndAttributeName.TOTAL_PAGES, totalPages);
            request.setAttribute(ParameterAndAttributeName.CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.COMMON_FILMS_PAGE).forward(request, response);

        } catch (ServiceValidationException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);

        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
